package com.wj.kotlintest.test

import android.util.Log

/**
 *
 *
 * @author 王杰
 */
open class TestConstructor2 constructor(a:Int) {

    init {
        Log.d("------------->", "init")
    }

    constructor(a: String) : this(1) {
        Log.d("------------->", "init  1")
    }

    constructor(a: String, b: String) : this(2) {
        Log.d("------------->", "init  2")
    }




}