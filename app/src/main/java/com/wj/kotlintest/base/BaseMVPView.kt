package com.wj.kotlintest.base

/**
 * MVP View基类
 */
interface BaseMVPView {

    /**
     * 网络请求结束
     */
    fun onNetFinished()

    /**
     * 网络故障
     */
    fun onNetError()

    /**
     * 无数据
     */
    fun onNoData()

    /**
     * 加载中
     */
    fun onLoading()
}
