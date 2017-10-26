package com.wj.kotlintest.test

import android.util.Log

/**
 *
 *
 * @author 王杰
 */
class TestConstructor constructor(a:Int) : TestConstructor2(a) {

    init {
        Log.d("------------->","init")
    }

    constructor(a: String) : this(1) {
        Log.d("------------->","init  1")
    }

    constructor(a: String, b: String) : this(2) {
        Log.d("------------->","init  2")
    }




}