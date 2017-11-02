package com.wj.kotlintest.base

import com.wj.kotlintest.constant.Constants
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Presenter基类
 *
 * @param V MVP View类型 继承[BaseMVPView]
 * @param M MVP Module 继承[BaseMVPModule]
 */
open class BaseMVPPresenter<V : BaseMVPView, M : BaseMVPModule> {

    /** MVP View 对象  */
    protected var mView: V? = null

    /** MVP Module 对象  */
    @Inject
    protected lateinit var mModule: M

    /** RxJava2 生命周期管理  */
    private val disposables = CompositeDisposable()

    /**
     * 界面绑定，关联 MVP View
     *
     * @param view MVP View
     */
    fun attach(view: V) {
        mView = view
    }

    /**
     * 解除绑定，去除 MVP View 引用
     */
    fun detach() {
        mView = null
    }

    /**
     * 检查请求返回数据，并在登录状态异常时弹出提示
     *
     * @param data 返回数据
     * @param T  返回数据类型
     *
     * @return 是否成功
     */
    protected fun <T : BaseEntity> checkResponse(data: T): Boolean {
        return data.code == Constants.ResponseCode.SUCCESS
    }

    /**
     * 将网络请求添加到 RxJava2 生命周期
     */
    protected fun addDisposable(dis: Disposable) {
        disposables.add(dis)
    }

    /**
     * 消费所有事件
     */
    fun dispose() {
        if (!disposables.isDisposed && disposables.size() > 0) {
            disposables.dispose()
        }
    }

}
