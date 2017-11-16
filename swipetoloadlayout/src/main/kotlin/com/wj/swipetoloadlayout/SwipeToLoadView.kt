package com.wj.swipetoloadlayout

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

/**
 * 下拉刷新布局
 */
class RefreshView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), SwipeRefreshTrigger, SwipeTrigger {

    private val iv: ImageView
    private val tv: TextView


    init {
        val view = View.inflate(context, R.layout.layout_refresh, this)
        iv = view.findViewById(R.id.iv)
        tv = view.findViewById(R.id.tv)
    }

    override fun onRefresh() {
        iv.setImageResource(R.drawable.loading)
        val animation = iv.drawable as AnimationDrawable
        animation.start()
        tv.setText(R.string.loading)
    }

    override fun onPrepare() {}

    override fun onMove(y: Int, isComplete: Boolean, automatic: Boolean) {
        if (isComplete) {
            return
        }

        val moveY = 100.0f * y
        val height = 1.0f * height

        val degree = moveY / height

        if (degree <= 100) {
            tv.setText(R.string.pull_to_refresh)
            when {
                degree < 40 -> iv.setImageResource(R.drawable.pull_end_image_frame_01)
                degree < 55 -> iv.setImageResource(R.drawable.pull_end_image_frame_02)
                degree < 70 -> iv.setImageResource(R.drawable.pull_end_image_frame_03)
                degree < 85 -> iv.setImageResource(R.drawable.pull_end_image_frame_04)
                else -> iv.setImageResource(R.drawable.pull_end_image_frame_05)
            }
        } else {
            tv.setText(R.string.release_to_refresh)
        }
    }

    override fun onRelease() {}

    override fun onComplete() {
        tv.setText(R.string.complete)

        if (iv.drawable is AnimationDrawable) {
            val animation = iv.drawable as AnimationDrawable
            animation.stop()
            iv.clearAnimation()
        }

        iv.setImageResource(R.drawable.refreshing_image_frame_01)
    }

    override fun onReset() {}

}

/**
 * 上拉加载更多布局
 */
class LoadMoreView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int)
    : FrameLayout(context, attrs, defStyleAttr),
        SwipeLoadMoreTrigger, SwipeTrigger {

    private val iv: ImageView
    private val tv: TextView

    init {
        val view = View.inflate(context, R.layout.layout_refresh, this)
        iv = view.findViewById(R.id.iv)
        tv = view.findViewById(R.id.tv)
    }

    override fun onLoadMore() {
        iv.setImageResource(R.drawable.loading)
        val animation = iv.drawable as AnimationDrawable
        animation.start()
        tv.setText(R.string.loading)
    }

    override fun onPrepare() {}

    override fun onMove(y: Int, isComplete: Boolean, automatic: Boolean) {
        if (isComplete) {
            return
        }

        val moveY = -100.0f * y
        val height = 1.0f * height

        val degree = moveY / height

        if (degree <= 100) {
            tv.setText(R.string.pull_to_load_more)
            when {
                degree < 40 -> iv.setImageResource(R.drawable.pull_end_image_frame_01)
                degree < 55 -> iv.setImageResource(R.drawable.pull_end_image_frame_02)
                degree < 70 -> iv.setImageResource(R.drawable.pull_end_image_frame_03)
                degree < 85 -> iv.setImageResource(R.drawable.pull_end_image_frame_04)
                else -> iv.setImageResource(R.drawable.pull_end_image_frame_05)
            }
        } else {
            tv.setText(R.string.release_to_load_more)
        }
    }

    override fun onRelease() {}

    override fun onComplete() {
        tv.setText(R.string.complete)

        if (iv.drawable is AnimationDrawable) {
            val animation = iv.drawable as AnimationDrawable
            animation.stop()
            iv.clearAnimation()
        }

        iv.setImageResource(R.drawable.refreshing_image_frame_01)
    }

    override fun onReset() {}
}