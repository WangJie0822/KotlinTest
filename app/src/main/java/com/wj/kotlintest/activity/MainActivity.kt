package com.wj.kotlintest.activity

import android.os.Bundle
import android.util.Log
import com.wj.kotlintest.base.BaseActivity
import com.wj.kotlintest.databinding.ActivityMainBinding
import com.wj.kotlintest.test.TestA
import com.wj.kotlintest.test.a
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initTitleBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Inject
    lateinit var testA: TestA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        mBinding.tv.text = testA.toString()

        Log.e("--------->", "fawef".a())


    }
}
