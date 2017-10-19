package com.wj.kotlintest.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import com.wj.kotlintest.BR

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