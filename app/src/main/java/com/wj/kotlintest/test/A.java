package com.wj.kotlintest.test;

/**
 * @author 王杰
 */
public class A {

    private A() {

    }

    static class Helper {
        static final A  a= new A();
    }

    public static A getInstance() {
        return Helper.a;
    }
}
