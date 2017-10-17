package com.wj.kotlintest.base

/**
 * 数据实体类基类
 */
open class BaseBean {

    /** 返回信息  */
    open var msg: String? = null
    /** 返回码  */
    open var code: Int = 0
    /** 层级  */
    open var total: Int = 0
}
