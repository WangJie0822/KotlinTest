package com.wj.kotlintest.base

/**
 * 网络请求结束回调接口
 *
 * @param E 请求成功返回数据类型
 * @author 王杰
 */
interface OnNetFinishedListener<in E : BaseEntity> {

    /**
     * 网络请求成功
     *
     * @param entity 请求返回数据
     */
    fun onSuccess(entity: E)

    /**
     * 请求失败
     *
     * @param fail 失败信息
     */
    fun onFail(fail: Throwable)
}