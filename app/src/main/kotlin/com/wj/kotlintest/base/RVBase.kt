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
 * @param E  数据类型
 * @param VH ViewHolder 类型，继承 [BaseRvViewHolder]
 * @param H  事件处理类型 Handler
 * @param DB  DataBinding 类型，与 VH 一致 继承 [ViewDataBinding]
 */
abstract class BaseRvAdapter<E, VH : BaseRvViewHolder<*, *>, H, in DB : ViewDataBinding> : RecyclerView.Adapter<VH>() {

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
    private val headers: ArrayList<View> = ArrayList()
    /** 脚布局集合 */
    private val footers: ArrayList<View> = ArrayList()

    /** 头布局下标 */
    private var mHeaderPos: Int = 0
    /** 脚布局下标 */
    private var mFooterPos: Int = 0

    /**
     * 根据 View 下标获取当前 View 布局类型
     */
    override fun getItemViewType(position: Int) = when {
        isHeader(position) -> VIEW_TYPE_HEADER // 头布局
        isFooter(position) -> VIEW_TYPE_FOOTER // 脚布局
        else -> VIEW_TYPE_NORMAL               // 普通布局
    }

    /**
     * 创建 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH? {
        return if (viewType == VIEW_TYPE_NORMAL) { // 普通布局
            // 加载布局，初始化 DataBinding
            val binding = DataBindingUtil.inflate<DB>(
                    LayoutInflater.from(parent.context),
                    layoutResID(), parent, false
            )
            // 绑定事件处理
            handler?.let { binding.setVariable(BR.handler, handler) }
            // 创建 ViewHolder
            createViewHolder(binding)
        } else return if (viewType == VIEW_TYPE_HEADER) { // 头布局
            createViewHolder(headers[mHeaderPos++])
        } else { // 脚布局
            createViewHolder(footers[mFooterPos++])
        }
    }

    /**
     * 绑定 ViewHolder
     */
    override fun onBindViewHolder(holder: VH, position: Int) {
        if (isHeader(position) || isFooter(position)) { // 头布局、脚布局，不做操作
            return
        }
        // 普通布局，绑定数据
        convert(holder, getItem(position - headers.size))
        holder.mBinding.executePendingBindings()
    }

    /**
     * 获取条目总数
     */
    override fun getItemCount() = headers.size + data.size + footers.size

    /**
     * 根据下标判断是否是头布局
     */
    protected fun isHeader(position: Int) = position < headers.size

    /**
     * 根据下标判断是否是脚布局
     */
    protected fun isFooter(position: Int) = position > itemCount - footers.size - 1

    /**
     * 根据下标获取当前布局对应的数据对象
     */
    protected fun getItem(position: Int) = data[position]

    /**
     * 添加头布局
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
     * 添加脚布局
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
     * @return ViewHolder
     */
    protected abstract fun createViewHolder(binding: DB): VH

    /**
     * 绑定数据
     *
     * @param holder ViewHolder
     * @param entity   数据对象
     */
    protected abstract fun convert(holder: VH, entity: E)

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
 */
open class BaseRvViewHolder<DB : ViewDataBinding, E> : RecyclerView.ViewHolder {

    lateinit var mBinding: DB

    constructor(view: View) : super(view)

    constructor(binding: DB) : super(binding.root) {
        mBinding = binding
    }

    open fun bindData(entity: E) {
        mBinding.setVariable(BR.item, entity)
        mBinding.executePendingBindings()
    }
}