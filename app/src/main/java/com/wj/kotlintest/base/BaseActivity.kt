package com.wj.kotlintest.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import com.wj.kotlintest.utils.AppManager
import dagger.android.AndroidInjection

/**
 *
 *
 * @author 王杰
 */
abstract class BaseActivity<B : ViewDataBinding>
    : AppCompatActivity(){

    protected lateinit var mContext: AppCompatActivity

    protected lateinit var mBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        // Dagger2 注入
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        // 保存当前 Context 对象
        mContext = this

        // 添加到 AppManager 应用管理
        AppManager.INSTANCE.addActivity(this)

    }

    override fun onDestroy() {

        // 从应用管理移除当前 Activity 对象
        AppManager.INSTANCE.removeActivity(this)

        super.onDestroy()
    }

    override fun setContentView(layoutResID: Int) {

        // 加载布局，初始化 DataBinding
        mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                layoutResID, null, false
        )

        super.setContentView(mBinding.root)
    }

}