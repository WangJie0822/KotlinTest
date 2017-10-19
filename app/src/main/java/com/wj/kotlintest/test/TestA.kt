package com.wj.kotlintest.test

import javax.inject.Inject

/**
 *
 *
 * @author 王杰
 */
class TestA @Inject constructor(){

    override fun toString(): String {

        val str = "fewfw"

        str.a()



        return "Class TestA " + super.toString()
    }
}