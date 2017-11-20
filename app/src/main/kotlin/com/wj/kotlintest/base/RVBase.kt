package com.wj.kotlintest.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wj.kotlintest.BR

/**
 * RecyclerView 适配器基类
 *
 * @param VH ViewHolder 类型，继承 [BaseRvViewHolder]
 * @param DB  DataBinding 类型，与 VH 一致 继承 [ViewDataBinding]
 * @param H  事件处理类型 Handler
 * @param E  数据类型
 */
abstract class BaseRvAdapter<VH : BaseRvViewHolder<DB, E>, DB : ViewDataBinding, H, E> : RecyclerView.Adapter<VH>() {

    companion object {
        /** 布局类型-头布局  */
        protected val VIEW_TYPE_HEADER = 0x00038
        /** 布局类型-正常  */
        protected val VIEW_TYPE_NORMAL = 0x00046
        /** 布局类型-脚布局  */
        protected val VIEW_TYPE_FOOTER = 0x00508
    }

    /** 数据集合  */
    lateinit var data: ArrayList<E>

    /** 事件处理  */
    var handler: H? = null

    /** 头布局集合 */
    private val headers = arrayListOf<View>()
    /** 脚布局集合 */
    private val footers = arrayListOf<View>()

    /** 头布局下标 */
    private var mHeaderPos: Int = 0
    /** 脚布局下标 */
    private var mFooterPos: Int = 0

    /**
     * 根据 View 下标获取当前 View 布局类型
     *
     * @param position View 下标
     * @return View 类型 [VIEW_TYPE_HEADER]、[VIEW_TYPE_NORMAL]、[VIEW_TYPE_FOOTER]
     */
    override fun getItemViewType(position: Int) = when {
        isHeader(position) -> VIEW_TYPE_HEADER // 头布局
        isFooter(position) -> VIEW_TYPE_FOOTER // 脚布局
        else -> VIEW_TYPE_NORMAL               // 普通布局
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH? {
        return if (viewType == VIEW_TYPE_NORMAL) {
            // 普通布局
            // 加载布局，初始化 DataBinding
            val binding = DataBindingUtil.inflate<DB>(
                    LayoutInflater.from(parent.context),
                    layoutResID(), parent, false
            )
            // 绑定事件处理
            handler?.let { binding.setVariable(BR.handler, handler) }
            // 创建 ViewHolder
            createViewHolder(binding)
        } else return if (viewType == VIEW_TYPE_HEADER) {
            // 头布局
            createViewHolder(headers[mHeaderPos++])
        } else {
            // 脚布局
            createViewHolder(footers[mFooterPos++])
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (isHeader(position) || isFooter(position)) {
            // 头布局、脚布局，返回不做操作
            return
        }
        // 普通布局，绑定数据
        convert(holder, getItem(position - headers.size))
        // DataBinding 更新布局
        holder.mBinding.executePendingBindings()
    }

    override fun getItemCount() = headers.size + data.size + footers.size

    /**
     * 根据下标判断是否是头布局
     *
     * @param position View 下标
     * @return 是否是头布局
     */
    protected fun isHeader(position: Int) = position < headers.size

    /**
     * 根据下标判断是否是脚布局
     *
     * @param position View 下标
     * @return 是否是脚布局
     */
    protected fun isFooter(position: Int) = position > itemCount - footers.size - 1

    /**
     * 根据下标获取当前布局对应的数据对象
     *
     * @param position View 下标
     * @return View 对应的数据对象
     */
    protected fun getItem(position: Int) = data[position]

    /**
     * 添加头布局，**注意：必须重写 createViewHolder(view) 方法**
     *
     * @param headerView 头布局
     */
    fun addHeader(headerView: View) {
        if (null == createViewHolder(headerView)) {
            throw RuntimeException("Please override createViewHolder(view) first!")
        }
        headers.add(headerView)
    }

    /**
     * 添加脚布局，**注意：必须重写 createViewHolder(view) 方法**
     *
     * @param footerView 脚布局
     */
    fun addFooter(footerView: View) {
        if (null == createViewHolder(footerView)) {
            throw RuntimeException("Please override createViewHolder(view) first!")
        }
        footers.add(footerView)
    }

    /**
     * 获取布局id
     *
     * @return 布局id
     */
    protected abstract fun layoutResID(): Int

    /**
     * 创建ViewHolder
     *
     * @param binding DataBinding对象
     *
     * @return ViewHolder 对象
     */
    protected abstract fun createViewHolder(binding: DB): VH

    /**
     * 绑定数据
     *
     * @param holder ViewHolder
     * @param entity   数据对象
     */
    protected fun convert(holder: VH, entity: E) {
        holder.bindData(entity)
    }

    /**
     * 创建ViewHolder 使用头布局时必须重写
     *
     * @param view View对象
     *
     * @return ViewHolder
     */
    protected abstract fun createViewHolder(view: View): VH?
}

/**
 * ViewHolder基类
 *
 * @param DB DataBinding 类，继承 [ViewDataBinding]，与 Adapter 一致
 * @param E 数据实体类，与 Adapter 一致
 */
open class BaseRvViewHolder<DB : ViewDataBinding, E> : RecyclerView.ViewHolder {

    /** DataBinding 对象 */
    lateinit var mBinding: DB

    /**
     * 构造方法，头布局、脚布局使用
     *
     * @param view 布局 View 对象
     */
    constructor(view: View) : super(view)

    /**
     * 构造方法，普通布局使用
     *
     * @param binding DataBinding 对象
     */
    constructor(binding: DB) : super(binding.root) {
        mBinding = binding
    }

    /**
     * 绑定数据
     *
     * @param entity 数据实体对象
     */
    open fun bindData(entity: E) {
        mBinding.setVariable(BR.item, entity)
        mBinding.executePendingBindings()
    }
}