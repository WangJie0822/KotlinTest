package com.wj.kotlintest.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wj.kotlintest.BR
import java.lang.reflect.ParameterizedType

/**
 * RecyclerView 适配器基类
 *
 * @param VH ViewHolder 类型，继承 [BaseRvViewHolder]
 * @param DB  DataBinding 类型，与 VH 一致 继承 [ViewDataBinding]
 * @param H  事件处理类型 Handler
 * @param E  数据类型
 */
abstract class BaseRvAdapter<out VH : BaseRvViewHolder<DB, E>, DB : ViewDataBinding, H, E> : RecyclerView.Adapter<BaseRvViewHolder<DB, E>>() {

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
    val headers = arrayListOf<View>()
    /** 脚布局集合 */
    val footers = arrayListOf<View>()

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvViewHolder<DB, E> {
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

    override fun onBindViewHolder(holder: BaseRvViewHolder<DB, E>, position: Int) {
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

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            // 如果是 Grid 布局
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) =
                        if (isHeader(position) || isFooter(position))
                            manager.spanCount
                        else
                            // 如果是头布局或者脚布局，独占一行
                            1
            }
        }
    }

    override fun onViewAttachedToWindow(holder: BaseRvViewHolder<DB, E>) {
        val lp = holder.itemView.layoutParams
        lp?.let {
            if (it is StaggeredGridLayoutManager.LayoutParams) {
                // 是瀑布流式布局
                if (isFooter(holder.layoutPosition) || isHeader(holder.layoutPosition)) {
                    // 是头布局或脚布局，独占一行
                    it.isFullSpan = true
                }
            }
        }
    }

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
     * 获取布局id
     *
     * @return 布局id
     */
    protected abstract fun layoutResID(): Int

    /**
     * 绑定数据
     *
     * @param holder ViewHolder
     * @param entity   数据对象
     */
    protected fun convert(holder: BaseRvViewHolder<DB, E>, entity: E) {
        holder.bindData(entity)
    }

    /**
     * 创建ViewHolder
     *
     * @param binding DataBinding对象
     *
     * @return ViewHolder 对象
     */
    protected open fun createViewHolder(binding: DB): VH {
        val holderConstructor = getVHClass().getConstructor(getDBClass())
        return holderConstructor.newInstance(binding)
    }

    /**
     * 创建ViewHolder 使用头布局时必须重写
     *
     * @param view View对象
     *
     * @return ViewHolder
     */
    protected open fun createViewHolder(view: View): BaseRvViewHolder<DB, E> {
        @Suppress("UNCHECKED_CAST")
        val clazz = getVHClass().superclass as Class<BaseRvViewHolder<DB, E>>
        val constructor = clazz.getConstructor(View::class.java)
        return constructor.newInstance(view)
    }

    /**
     * 获取 ViewHolder 的类
     *
     * @return ViewHolder 实际类型
     */
    @Suppress("UNCHECKED_CAST")
    private fun getVHClass() = getActualTypeList()[0] as Class<VH>

    /**
     * 获取 DataBinding 的类
     *
     * @return DataBinding 的实际类型
     */
    @Suppress("UNCHECKED_CAST")
    private fun getDBClass() = getActualTypeList()[1] as Class<DB>

    /**
     * 获取泛型实际类型列表
     */
    private fun getActualTypeList() = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
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
    constructor(binding: DB) : this(binding.root) {
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