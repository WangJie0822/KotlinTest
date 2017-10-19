package com.wj.kotlintest.base

import com.wj.kotlintest.net.NetApi
import javax.inject.Inject

/**
 * MVP Module基类
 */
open class BaseMVPModule @Inject constructor() {

    @Inject
    lateinit var netClient: NetApi
}
