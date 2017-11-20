package com.wj.kotlintest.activity

import android.os.Bundle
import android.os.Handler
import com.wj.kotlintest.R
import com.wj.kotlintest.base.BaseActivity
import com.wj.kotlintest.base.BlankPresenter
import com.wj.kotlintest.constants.SPLASH_TIME_DELAY
import com.wj.kotlintest.databinding.ActivitySplashBinding

/**
 * 闪屏界面
 */
class SplashActivity : BaseActivity<BlankPresenter, ActivitySplashBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 延时后跳转主界面，销毁当前界面
        Handler().postDelayed({
            MainActivity.actionStart(mContext)
            finish()
        }, SPLASH_TIME_DELAY)
    }
}
