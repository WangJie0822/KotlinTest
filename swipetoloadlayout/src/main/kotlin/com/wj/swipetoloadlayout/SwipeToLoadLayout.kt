package com.wj.swipetoloadlayout

import android.content.Context
import android.support.v4.view.MotionEventCompat
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.Scroller

/**
 *
 *
 * @author 王杰
 */
class SwipeToLoadLayout : ViewGroup {

    companion object {
        val TAG = SwipeToLoadLayout::class.java.simpleName
        val DEFAULT_SWIPING_TO_REFRESH_TO_DEFAULT_SCROLLING_DURATION = 200
        val DEFAULT_RELEASE_TO_REFRESHING_SCROLLING_DURATION = 200
        val DEFAULT_REFRESH_COMPLETE_DELAY_DURATION = 300
        val DEFAULT_REFRESH_COMPLETE_TO_DEFAULT_SCROLLING_DURATION = 500
        val DEFAULT_DEFAULT_TO_REFRESHING_SCROLLING_DURATION = 500
        val DEFAULT_SWIPING_TO_LOAD_MORE_TO_DEFAULT_SCROLLING_DURATION = 200
        val DEFAULT_RELEASE_TO_LOADING_MORE_SCROLLING_DURATION = 200
        val DEFAULT_LOAD_MORE_COMPLETE_DELAY_DURATION = 300
        val DEFAULT_LOAD_MORE_COMPLETE_TO_DEFAULT_SCROLLING_DURATION = 300
        val DEFAULT_DEFAULT_TO_LOADING_MORE_SCROLLING_DURATION = 300
        val DEFAULT_DRAG_RATIO = 0.5f
        val INVALID_POINTER = -1
        val INVALID_COORDINATE = -1
    }

    /** 标记-是否打印日志 */
    var mDebug = false

    /** 滚动处理 */
    val mAutoScroller: AutoScroller

    /** 刷新监听 */
    var mRefreshListener: OnRefreshListener? = null
    /** 上拉加载监听*/
    var mLoadMoreListener: OnLoadMoreListener? = null

    /** 下拉刷新控件 */
    var mHeaderView: View? = null
    /** 目标控件 */
    var mTargetView: View? = null
    /** 上拉加载控件 */
    var mFooterView: View? = null

    /** 标记-能否下拉刷新 */
    private var mRefreshEnabled = true
    /** 标记-能否上拉加载 */
    private var mLoadMoreEnabled = true

    /** 下拉刷新控件高度 */
    var mHeaderHeight = 0
    /** 上拉加载控件高度 */
    var mFooterHeight = 0

    /** 触摸事件的极限值 */
    private val mTouchSlop: Int

    /** 触发刷新偏移量 */
    var mRefreshTriggerOffset = 0f
    /** 触发加载偏移量 */
    var mLoadMoreTriggerOffset = 0f

    /** 标记-是否有下拉刷新控件 */
    var mHasHeaderView = false
    /** 标记-是否有上拉加载控件 */
    var mHasFooterView = false

    /** 标记-是否自动刷新 */
    var mAutoLoading: Boolean = false

    /** 下拉刷新控件偏移量 */
    var mHeaderOffset = 0
    /** 目标控件偏移量 */
    var mTargetOffset = 0
    /** 上拉加载控件偏移量 */
    var mFooterOffset = 0

    /** 默认类型[STYLE.CLASSIC]*/
    var mStyle = STYLE.CLASSIC

    /** SwipeToLoadLayout 当前状态 */
    var mStatus = STATUS.STATUS_DEFAULT
        set(value) {
            field = value
            if (mDebug) {
                STATUS.printStatus(field)
            }
        }

    /** 触摸事件开始 x 坐标 */
    private var mInitDownX = 0f

    /** 触摸事件开始 y 坐标 */
    private var mInitDownY = 0f

    /** 触摸事件结束 x 坐标 */
    private var mLastX = 0f

    /** 触摸事件结束 y 坐标 */
    private var mLastY = 0f

    /** 触摸事件 id */
    private var mActivePointerId = 0

    private var mDragRatio = DEFAULT_DRAG_RATIO

    /**
     * **ATTRIBUTE:**
     * the max value of top offset
     */
    private var mRefreshFinalDragOffset: Float = 0.toFloat()

    /**
     * **ATTRIBUTE:**
     * the max value of bottom offset
     */
    private var mLoadMoreFinalDragOffset: Float = 0.toFloat()

    /** 状态 滑动刷新 -> 默认 持续时间 */
    private var mSwipingToRefreshToDefaultScrollingDuration = DEFAULT_SWIPING_TO_REFRESH_TO_DEFAULT_SCROLLING_DURATION

    /** 状态 释放刷新 -> 刷新 持续时间 */
    private var mReleaseToRefreshToRefreshingScrollingDuration = DEFAULT_RELEASE_TO_REFRESHING_SCROLLING_DURATION

    /** 刷新完成延时 */
    private var mRefreshCompleteDelayDuration = DEFAULT_REFRESH_COMPLETE_DELAY_DURATION

    /** 状态 刷新完成 -> 默认 持续时间 */
    private var mRefreshCompleteToDefaultScrollingDuration = DEFAULT_REFRESH_COMPLETE_TO_DEFAULT_SCROLLING_DURATION

    /** 状态 自动刷新开启 默认 -> 刷新 持续时间 */
    private var mDefaultToRefreshingScrollingDuration = DEFAULT_DEFAULT_TO_REFRESHING_SCROLLING_DURATION

    /** 状态 释放加载更多 -> 加载更多 持续时间 */
    private var mReleaseToLoadMoreToLoadingMoreScrollingDuration = DEFAULT_RELEASE_TO_LOADING_MORE_SCROLLING_DURATION

    /** 加载更多完成延时 */
    private var mLoadMoreCompleteDelayDuration = DEFAULT_LOAD_MORE_COMPLETE_DELAY_DURATION

    /** 状态 加载更多完成 -> 默认 持续时间 */
    private var mLoadMoreCompleteToDefaultScrollingDuration = DEFAULT_LOAD_MORE_COMPLETE_TO_DEFAULT_SCROLLING_DURATION

    /** 状态 滑动加载更多 -> 默认 持续时间 */
    private var mSwipingToLoadMoreToDefaultScrollingDuration = DEFAULT_SWIPING_TO_LOAD_MORE_TO_DEFAULT_SCROLLING_DURATION

    /** 状态 自动加载更多开启 默认 -> 加载更多 持续时间 */
    private var mDefaultToLoadingMoreScrollingDuration = DEFAULT_DEFAULT_TO_LOADING_MORE_SCROLLING_DURATION

    /** 刷新回调 */
    val mRefreshCallback: RefreshCallback = object : RefreshCallback() {

        override fun onPrepare() {
            if (mHeaderView != null && mHeaderView is SwipeTrigger && STATUS.isStatusDefault(mStatus)) {
                mHeaderView?.visibility = View.VISIBLE
                (mHeaderView as SwipeTrigger).onPrepare()
            }
        }

        override fun onMove(y: Int, isComplete: Boolean, automatic: Boolean) {
            if (mHeaderView != null && mHeaderView is SwipeTrigger && STATUS.isRefreshStatus(mStatus)) {
                if (mHeaderView?.visibility != View.VISIBLE) {
                    mHeaderView?.visibility = View.VISIBLE
                }
                (mHeaderView as SwipeTrigger).onMove(y, isComplete, automatic)
            }
        }

        override fun onRelease() {
            if (mHeaderView != null && mHeaderView is SwipeTrigger && STATUS.isReleaseToRefresh(mStatus)) {
                (mHeaderView as SwipeTrigger).onRelease()
            }
        }

        override fun onRefresh() {
            if (mHeaderView != null && STATUS.isRefreshing(mStatus)) {
                if (mHeaderView is SwipeRefreshTrigger) {
                    (mHeaderView as SwipeRefreshTrigger).onRefresh()
                }
                mRefreshListener?.onRefresh()
            }
        }

        override fun onComplete() {
            if (mHeaderView != null && mHeaderView is SwipeTrigger) {
                (mHeaderView as SwipeTrigger).onComplete()
            }
        }

        override fun onReset() {
            if (mHeaderView != null && mHeaderView is SwipeTrigger && STATUS.isStatusDefault(mStatus)) {
                (mHeaderView as SwipeTrigger).onReset()
                mHeaderView?.setVisibility(View.GONE)
            }
        }
    }

    /** 上拉加载回调 */
    val mLoadMoreCallback: LoadMoreCallback = object : LoadMoreCallback() {

        override fun onPrepare() {
            if (mFooterView != null && mFooterView is SwipeTrigger && STATUS.isStatusDefault(mStatus)) {
                mFooterView?.setVisibility(View.VISIBLE)
                (mFooterView as SwipeTrigger).onPrepare()
            }
        }

        override fun onMove(y: Int, isComplete: Boolean, automatic: Boolean) {
            if (mFooterView != null && mFooterView is SwipeTrigger && STATUS.isLoadMoreStatus(mStatus)) {
                if (mFooterView?.visibility != View.VISIBLE) {
                    mFooterView?.visibility = View.VISIBLE
                }
                (mFooterView as SwipeTrigger).onMove(y, isComplete, automatic)
            }
        }

        override fun onRelease() {
            if (mFooterView != null && mFooterView is SwipeTrigger && STATUS.isReleaseToLoadMore(mStatus)) {
                (mFooterView as SwipeTrigger).onRelease()
            }
        }

        override fun onLoadMore() {
            if (mFooterView != null && STATUS.isLoadingMore(mStatus)) {
                if (mFooterView is SwipeLoadMoreTrigger) {
                    (mFooterView as SwipeLoadMoreTrigger).onLoadMore()
                }
                mLoadMoreListener?.onLoadMore()
            }
        }

        override fun onComplete() {
            if (mFooterView != null && mFooterView is SwipeTrigger) {
                (mFooterView as SwipeTrigger).onComplete()
            }
        }

        override fun onReset() {
            if (mFooterView != null && mFooterView is SwipeTrigger && STATUS.isStatusDefault(mStatus)) {
                (mFooterView as SwipeTrigger).onReset()
                mFooterView?.visibility = View.GONE
            }
        }
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
        mAutoScroller = AutoScroller()
    }

    /**
     * XML 加载完毕后回调
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        when (childCount) {
            0 -> // 没有子控件，返回不做操作
                return
            in 1..3 -> {
                // 1-3个子控件，根据 id 获取对应的控件
                mHeaderView = findViewById(R.id.swipe_refresh_header)
                mTargetView = findViewById(R.id.swipe_target)
                mFooterView = findViewById(R.id.swipe_load_more_footer)
            }
            else ->
                // 超过3个子控件，不支持，抛出异常
                throw IllegalStateException("Children num must equal or less than 3")
        }
        if (mTargetView == null) {
            // 没找到目标控件，返回不做操作
            return
        }
        // 默认隐藏下拉刷新、上拉加载控件
        if (mHeaderView is SwipeTrigger) {
            mHeaderView?.visibility = View.GONE
        }
        if (mFooterView is SwipeTrigger) {
            mFooterView?.visibility = View.GONE
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // 测量下拉刷新控件
        mHeaderView?.let {
            measureChildWithMargins(it, widthMeasureSpec, 0, heightMeasureSpec, 0)
            val lp = it.layoutParams as MarginLayoutParams
            mHeaderHeight = it.measuredHeight + lp.topMargin + lp.bottomMargin
            if (mRefreshTriggerOffset < mHeaderHeight) {
                mRefreshTriggerOffset = mHeaderHeight.toFloat()
            }
        }

        // 测量目标控件
        mTargetView?.let {
            measureChildWithMargins(it, widthMeasureSpec, 0, heightMeasureSpec, 0)
        }

        // 测量上拉加载控件
        mFooterView?.let {
            measureChildWithMargins(it, widthMeasureSpec, 0, heightMeasureSpec, 0)
            val lp = it.layoutParams as MarginLayoutParams
            mFooterHeight = it.measuredHeight + lp.topMargin + lp.bottomMargin
            if (mLoadMoreTriggerOffset < mFooterHeight) {
                mLoadMoreTriggerOffset = mFooterHeight.toFloat()
            }
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        layoutChildren()

        mHasHeaderView = mHeaderView != null
        mHasFooterView = mFooterView != null
    }

    /**
     * SwipeToLoadLayout 的布局参数类型
     */
    class LayoutParams : ViewGroup.MarginLayoutParams {
        constructor(c: Context, attrs: AttributeSet) : super(c, attrs)
        constructor(width: Int, height: Int) : super(width, height)
        constructor(source: ViewGroup.MarginLayoutParams) : super(source)
        constructor(source: ViewGroup.LayoutParams) : super(source)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return SwipeToLoadLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
        return SwipeToLoadLayout.LayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
        return SwipeToLoadLayout.LayoutParams(context, attrs)
    }

    /**
     * 事件分发
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val action = MotionEventCompat.getActionMasked(ev)
        when (action) {
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP ->
                // 触摸事件-抬起手指处理
                onActivePointerUp()
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 事件拦截
     */
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val action = MotionEventCompat.getActionMasked(event)
        when (action) {
            MotionEvent.ACTION_DOWN -> {

                mActivePointerId = MotionEventCompat.getPointerId(event, 0)
                mLastY = getMotionEventY(event, mActivePointerId)
                mInitDownY = mLastY
                mLastX = getMotionEventX(event, mActivePointerId)
                mInitDownX = mLastX

                if (STATUS.isSwipingToRefresh(mStatus) || STATUS.isSwipingToLoadMore(mStatus) ||
                        STATUS.isReleaseToRefresh(mStatus) || STATUS.isReleaseToLoadMore(mStatus)) {
                    // 不是加载中或默认状态，终止之前的滚动事件，处理新的滚动事件
                    mAutoScroller.abortIfRunning()
                    if (mDebug) {
                        Log.i(TAG, "Another finger down, abort auto scrolling, let the new finger handle")
                    }
                    // 消费事件
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (mActivePointerId == INVALID_POINTER) {
                    return false
                }
                val y = getMotionEventY(event, mActivePointerId)
                val x = getMotionEventX(event, mActivePointerId)
                val yInitDiff = y - mInitDownY
                val xInitDiff = x - mInitDownX
                mLastY = y
                mLastX = x
                val moved = Math.abs(yInitDiff) > Math.abs(xInitDiff) && Math.abs(yInitDiff) > mTouchSlop
                val triggerCondition = (yInitDiff > 0 && moved && checkCanRefresh()) // 刷新的触发条件
                         || (yInitDiff < 0 && moved && checkCanLoadMore()) // 加载更多的触发条件

                if (triggerCondition) {
                    // 触发了刷新或加载更多，拦截事件，交给 SwipeToloadLayout 的 onTouchEvent() 处理
                    return true
                }
            }
            MotionEvent.ACTION_POINTER_UP -> {
                onSecondaryPointerUp(event)
                mLastY = getMotionEventY(event, mActivePointerId)
                mInitDownY = mLastY
                mLastX = getMotionEventX(event, mActivePointerId)
                mInitDownX = mLastX
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> mActivePointerId = INVALID_POINTER
        }
        return super.onInterceptTouchEvent(event)
    }

    /**
     * 事件处理
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = MotionEventCompat.getActionMasked(event)

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mActivePointerId = MotionEventCompat.getPointerId(event, 0)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                // take over the ACTION_MOVE event from SwipeToLoadLayout#onInterceptTouchEvent()
                // if condition is true
                val y = getMotionEventY(event, mActivePointerId)
                val x = getMotionEventX(event, mActivePointerId)

                val yDiff = y - mLastY
                val xDiff = x - mLastX
                mLastY = y
                mLastX = x

                if (Math.abs(xDiff) > Math.abs(yDiff) && Math.abs(xDiff) > mTouchSlop) {
                    return false
                }

                if (STATUS.isStatusDefault(mStatus)) {
                    if (yDiff > 0 && checkCanRefresh()) {
                        mRefreshCallback.onPrepare()
                        mStatus = STATUS.STATUS_SWIPING_TO_REFRESH
                    } else if (yDiff < 0 && checkCanLoadMore()) {
                        mLoadMoreCallback.onPrepare()
                        mStatus = STATUS.STATUS_SWIPING_TO_LOAD_MORE
                    }
                } else if (STATUS.isRefreshStatus(mStatus)) {
                    if (mTargetOffset <= 0) {
                        mStatus = STATUS.STATUS_DEFAULT
                        fixCurrentStatusLayout()
                        return false
                    }
                } else if (STATUS.isLoadMoreStatus(mStatus)) {
                    if (mTargetOffset >= 0) {
                        mStatus = STATUS.STATUS_DEFAULT
                        fixCurrentStatusLayout()
                        return false
                    }
                }

                if (STATUS.isRefreshStatus(mStatus)) {
                    if (STATUS.isSwipingToRefresh(mStatus) || STATUS.isReleaseToRefresh(mStatus)) {
                        if (mTargetOffset >= mRefreshTriggerOffset) {
                            mStatus = STATUS.STATUS_RELEASE_TO_REFRESH
                        } else {
                            mStatus = STATUS.STATUS_SWIPING_TO_REFRESH
                        }
                        fingerScroll(yDiff)
                    }
                } else if (STATUS.isLoadMoreStatus(mStatus)) {
                    if (STATUS.isSwipingToLoadMore(mStatus) || STATUS.isReleaseToLoadMore(mStatus)) {
                        if (-mTargetOffset >= mLoadMoreTriggerOffset) {
                            mStatus = STATUS.STATUS_RELEASE_TO_LOAD_MORE
                        } else {
                            mStatus = STATUS.STATUS_SWIPING_TO_LOAD_MORE
                        }
                        fingerScroll(yDiff)
                    }
                }
                return true
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                val pointerIndex = MotionEventCompat.getActionIndex(event)
                val pointerId = MotionEventCompat.getPointerId(event, pointerIndex)
                if (pointerId != INVALID_POINTER) {
                    mActivePointerId = pointerId
                }
                mLastY = getMotionEventY(event, mActivePointerId)
                mInitDownY = mLastY
                mLastX = getMotionEventX(event, mActivePointerId)
                mInitDownX = mLastX
            }
            MotionEvent.ACTION_POINTER_UP -> {
                onSecondaryPointerUp(event)
                mLastY = getMotionEventY(event, mActivePointerId)
                mInitDownY = mLastY
                mLastX = getMotionEventX(event, mActivePointerId)
                mInitDownX = mLastX
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (mActivePointerId == INVALID_POINTER) {
                    return false
                }
                mActivePointerId = INVALID_POINTER
            }
            else -> {
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 检查是否能刷新
     */
    private fun checkCanRefresh() = mRefreshEnabled && !canChildScrollUp() && mHasHeaderView && mRefreshTriggerOffset > 0

    /**
     * 检查是否能加载更多
     */
    private fun checkCanLoadMore() = mLoadMoreEnabled && !canChildScrollDown() && mHasFooterView && mLoadMoreTriggerOffset > 0

    /**
     * 目标控件能否向上滚动
     */
    protected fun canChildScrollUp() = ViewCompat.canScrollVertically(mTargetView, -1)

    /**
     * 目标控件能否向下滚动
     */
    protected fun canChildScrollDown() = ViewCompat.canScrollVertically(mTargetView, 1)

    /**
     * 新的触摸事件，抬起手指
     */
    private fun onSecondaryPointerUp(ev: MotionEvent) {
        val pointerIndex = MotionEventCompat.getActionIndex(ev)
        val pointerId = MotionEventCompat.getPointerId(ev, pointerIndex)
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            val newPointerIndex = if (pointerIndex == 0) 1 else 0
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex)
        }
    }

    private fun getMotionEventX(event: MotionEvent, activePointId: Int): Float {
        val index = MotionEventCompat.findPointerIndex(event, activePointId)
        return if (index < 0)
            INVALID_COORDINATE.toFloat()
        else
            MotionEventCompat.getX(event, index)
    }

    private fun getMotionEventY(event: MotionEvent, activePointerId: Int): Float {
        val index = MotionEventCompat.findPointerIndex(event, activePointerId)
        return if (index < 0)
            INVALID_COORDINATE.toFloat()
        else
            MotionEventCompat.getY(event, index)
    }

    /**
     * 处理抬起手指事件
     */
    fun onActivePointerUp() {
        when {
            STATUS.isSwipingToRefresh(mStatus) -> scrollSwipingToRefreshToDefault()
            STATUS.isSwipingToLoadMore(mStatus) -> scrollSwipingToLoadMoreToDefault()
            STATUS.isReleaseToRefresh(mStatus) -> {
                mRefreshCallback.onRelease()
                scrollReleaseToRefreshToRefreshing()

            }
            STATUS.isReleaseToLoadMore(mStatus) -> {
                mLoadMoreCallback.onRelease()
                scrollReleaseToLoadMoreToLoadingMore()
            }
        }
    }

    /**
     * 获取子控件大小、位置
     */
    fun layoutChildren() {

        if (mTargetView == null) {
            // 没有目标控件，返回不做操作
            return
        }

        // 下拉刷新控件
        mHeaderView?.let {
            val lp = it.layoutParams as MarginLayoutParams
            val headerLeft = paddingLeft + lp.leftMargin
            val headerTop = when (mStyle) {
                STYLE.CLASSIC ->
                    paddingTop + lp.topMargin - mHeaderHeight + mHeaderOffset
                STYLE.ABOVE ->
                    paddingTop + lp.topMargin - mHeaderHeight + mHeaderOffset
                STYLE.BLEW ->
                    paddingTop + lp.topMargin
                STYLE.SCALE ->
                    paddingTop + lp.topMargin - mHeaderHeight / 2 + mHeaderOffset / 2
                else -> // classic
                    paddingTop + lp.topMargin - mHeaderHeight + mHeaderOffset
            }
            val headerRight = headerLeft + it.measuredWidth
            val headerBottom = headerTop + it.measuredHeight
            it.layout(headerLeft, headerTop, headerRight, headerBottom)
        }

        // 目标控件
        mTargetView?.let {
            val lp = it.layoutParams as MarginLayoutParams
            val targetLeft = paddingLeft + lp.leftMargin
            val targetTop = when (mStyle) {
                STYLE.CLASSIC ->
                    paddingTop + lp.topMargin + mTargetOffset
                STYLE.ABOVE ->
                    paddingTop + lp.topMargin
                STYLE.BLEW ->
                    paddingTop + lp.topMargin + mTargetOffset
                STYLE.SCALE ->
                    paddingTop + lp.topMargin + mTargetOffset
                else -> // classic
                    paddingTop + lp.topMargin + mTargetOffset
            }
            val targetRight = targetLeft + it.measuredWidth
            val targetBottom = targetTop + it.measuredHeight
            it.layout(targetLeft, targetTop, targetRight, targetBottom)
        }

        // 上拉加载控件
        mFooterView?.let {
            val lp = it.layoutParams as MarginLayoutParams
            val footerLeft = paddingLeft + lp.leftMargin
            val footerBottom = when (mStyle) {
                STYLE.CLASSIC ->
                    measuredHeight - paddingBottom - lp.bottomMargin + mFooterHeight + mFooterOffset
                STYLE.ABOVE ->
                    measuredHeight - paddingBottom - lp.bottomMargin + mFooterHeight + mFooterOffset
                STYLE.BLEW ->
                    measuredHeight - paddingBottom - lp.bottomMargin
                STYLE.SCALE ->
                    measuredHeight - paddingBottom - lp.bottomMargin + mFooterHeight / 2 + mFooterOffset / 2
                else -> // classic
                    measuredHeight - paddingBottom - lp.bottomMargin + mFooterHeight + mFooterOffset
            }
            val footerTop = footerBottom - it.measuredHeight
            val footerRight = footerLeft + it.measuredWidth
            it.layout(footerLeft, footerTop, footerRight, footerBottom)
        }

        // 根据类型，设置控件在父布局中的位置
        if (mStyle == STYLE.CLASSIC || mStyle == STYLE.ABOVE) {
            mHeaderView?.bringToFront()
            mFooterView?.bringToFront()
        } else if (mStyle == STYLE.BLEW || mStyle == STYLE.SCALE) {
            mTargetView?.bringToFront()
        }
    }

    /**
     * scrolling by physical touch with your fingers
     *
     * @param yDiff
     */
    private fun fingerScroll(yDiff: Float) {
        val ratio = mDragRatio
        var yScrolled = yDiff * ratio

        // make sure (targetOffset>0 -> targetOffset=0 -> default status)
        // or (targetOffset<0 -> targetOffset=0 -> default status)
        // forbidden fling (targetOffset>0 -> targetOffset=0 ->targetOffset<0 -> default status)
        // or (targetOffset<0 -> targetOffset=0 ->targetOffset>0 -> default status)
        // I am so smart :)

        val tmpTargetOffset = yScrolled + mTargetOffset
        if (tmpTargetOffset > 0 && mTargetOffset < 0 || tmpTargetOffset < 0 && mTargetOffset > 0) {
            yScrolled = (-mTargetOffset).toFloat()
        }


        if (mRefreshFinalDragOffset >= mRefreshTriggerOffset && tmpTargetOffset > mRefreshFinalDragOffset) {
            yScrolled = mRefreshFinalDragOffset - mTargetOffset
        } else if (mLoadMoreFinalDragOffset >= mLoadMoreTriggerOffset && -tmpTargetOffset > mLoadMoreFinalDragOffset) {
            yScrolled = -mLoadMoreFinalDragOffset - mTargetOffset
        }

        if (STATUS.isRefreshStatus(mStatus)) {
            mRefreshCallback.onMove(mTargetOffset, false, false)
        } else if (STATUS.isLoadMoreStatus(mStatus)) {
            mLoadMoreCallback.onMove(mTargetOffset, false, false)
        }
        updateScroll(yScrolled)
    }

    private fun autoScroll(yScrolled: Float) {

        if (STATUS.isSwipingToRefresh(mStatus)) {
            mRefreshCallback.onMove(mTargetOffset, false, true)
        } else if (STATUS.isReleaseToRefresh(mStatus)) {
            mRefreshCallback.onMove(mTargetOffset, false, true)
        } else if (STATUS.isRefreshing(mStatus)) {
            mRefreshCallback.onMove(mTargetOffset, true, true)
        } else if (STATUS.isSwipingToLoadMore(mStatus)) {
            mLoadMoreCallback.onMove(mTargetOffset, false, true)
        } else if (STATUS.isReleaseToLoadMore(mStatus)) {
            mLoadMoreCallback.onMove(mTargetOffset, false, true)
        } else if (STATUS.isLoadingMore(mStatus)) {
            mLoadMoreCallback.onMove(mTargetOffset, true, true)
        }
        updateScroll(yScrolled)
    }

    /**
     * Process the scrolling(auto or physical) and append the diff values to mTargetOffset
     * I think it's the most busy and core method. :) a ha ha ha ha...
     *
     * @param yScrolled
     */
    private fun updateScroll(yScrolled: Float) {
        if (yScrolled == 0f) {
            return
        }
        mTargetOffset += yScrolled.toInt()

        if (STATUS.isRefreshStatus(mStatus)) {
            mHeaderOffset = mTargetOffset
            mFooterOffset = 0
        } else if (STATUS.isLoadMoreStatus(mStatus)) {
            mFooterOffset = mTargetOffset
            mHeaderOffset = 0
        }

        if (mDebug) {
            Log.i(TAG, "mTargetOffset = " + mTargetOffset)
        }
        layoutChildren()
        invalidate()
    }

    /**
     * 滚动处理
     */
    inner class AutoScroller : Runnable {

        private val mScroller: Scroller = Scroller(context)

        var mmLastY: Int = 0

        var mRunning = false

        var mAbort = false

        override fun run() {
            val finish = !mScroller.computeScrollOffset() || mScroller.isFinished
            val currY = mScroller.currY
            val yDiff = currY - mmLastY
            if (finish) {
                finish()
            } else {
                mmLastY = currY
                this@SwipeToLoadLayout.autoScroll(yDiff.toFloat())
                post(this)
            }
        }

        /**
         * remove the post callbacks and reset default values
         */
        fun finish() {
            mmLastY = 0
            mRunning = false
            removeCallbacks(this)
            // if abort by user, don't call
            if (!mAbort) {
                autoScrollFinished()
            }
        }

        /**
         * abort scroll if it is scrolling
         */
        fun abortIfRunning() {
            if (mRunning) {
                if (!mScroller.isFinished) {
                    mAbort = true
                    mScroller.forceFinished(true)
                }
                finish()
                mAbort = false
            }
        }

        /**
         * The param yScrolled here isn't final pos of y.
         * It's just like the yScrolled param in the
         * [.updateScroll]
         *
         * @param yScrolled
         * @param duration
         */
        fun autoScroll(yScrolled: Int, duration: Int) {
            removeCallbacks(this)
            mmLastY = 0
            if (!mScroller.isFinished) {
                mScroller.forceFinished(true)
            }
            mScroller.startScroll(0, 0, 0, yScrolled, duration)
            post(this)
            mRunning = true
        }
    }

    private fun scrollDefaultToRefreshing() {
        mAutoScroller.autoScroll((mRefreshTriggerOffset + 0.5f).toInt(), mDefaultToRefreshingScrollingDuration)
    }

    private fun scrollDefaultToLoadingMore() {
        mAutoScroller.autoScroll(-(mLoadMoreTriggerOffset + 0.5f).toInt(), mDefaultToLoadingMoreScrollingDuration)
    }

    private fun scrollSwipingToRefreshToDefault() {
        mAutoScroller.autoScroll(-mHeaderOffset, mSwipingToRefreshToDefaultScrollingDuration)
    }

    private fun scrollSwipingToLoadMoreToDefault() {
        mAutoScroller.autoScroll(-mFooterOffset, mSwipingToLoadMoreToDefaultScrollingDuration)
    }

    private fun scrollReleaseToRefreshToRefreshing() {
        mAutoScroller.autoScroll(mHeaderHeight - mHeaderOffset, mReleaseToRefreshToRefreshingScrollingDuration)
    }

    private fun scrollReleaseToLoadMoreToLoadingMore() {
        mAutoScroller.autoScroll(-mFooterOffset - mFooterHeight, mReleaseToLoadMoreToLoadingMoreScrollingDuration)
    }

    private fun scrollRefreshingToDefault() {
        mAutoScroller.autoScroll(-mHeaderOffset, mRefreshCompleteToDefaultScrollingDuration)
    }

    private fun scrollLoadingMoreToDefault() {
        mAutoScroller.autoScroll(-mFooterOffset, mLoadMoreCompleteToDefaultScrollingDuration)
    }

    private fun fixCurrentStatusLayout() {
        if (STATUS.isRefreshing(mStatus)) {
            mTargetOffset = (mRefreshTriggerOffset + 0.5f).toInt()
            mHeaderOffset = mTargetOffset
            mFooterOffset = 0
            layoutChildren()
            invalidate()
        } else if (STATUS.isStatusDefault(mStatus)) {
            mTargetOffset = 0
            mHeaderOffset = 0
            mFooterOffset = 0
            layoutChildren()
            invalidate()
        } else if (STATUS.isLoadingMore(mStatus)) {
            mTargetOffset = -(mLoadMoreTriggerOffset + 0.5f).toInt()
            mHeaderOffset = 0
            mFooterOffset = mTargetOffset
            layoutChildren()
            invalidate()
        }
    }

    /**
     * invoke when [AutoScroller.finish] is automatic
     */
    private fun autoScrollFinished() {
        val mLastStatus = mStatus

        when (mStatus) {
            STATUS.STATUS_RELEASE_TO_REFRESH -> {
                mStatus = STATUS.STATUS_REFRESHING
                fixCurrentStatusLayout()
                mRefreshCallback.onRefresh()
            }
            STATUS.STATUS_REFRESHING -> {
                mStatus = STATUS.STATUS_DEFAULT
                fixCurrentStatusLayout()
                mRefreshCallback.onReset()
            }
            STATUS.STATUS_SWIPING_TO_REFRESH -> {
                if (mAutoLoading) {
                    mAutoLoading = false
                    mStatus = STATUS.STATUS_REFRESHING
                    fixCurrentStatusLayout()
                    mRefreshCallback.onRefresh()
                } else {
                    mStatus = STATUS.STATUS_DEFAULT
                    fixCurrentStatusLayout()
                    mRefreshCallback.onReset()
                }
            }
            STATUS.STATUS_SWIPING_TO_LOAD_MORE -> {
                if (mAutoLoading) {
                    mAutoLoading = false
                    mStatus = STATUS.STATUS_LOADING_MORE
                    fixCurrentStatusLayout()
                    mLoadMoreCallback.onLoadMore()
                } else {
                    mStatus = STATUS.STATUS_DEFAULT
                    fixCurrentStatusLayout()
                    mLoadMoreCallback.onReset()
                }
            }
            STATUS.STATUS_LOADING_MORE -> {
                mStatus = STATUS.STATUS_DEFAULT
                fixCurrentStatusLayout()
                mLoadMoreCallback.onReset()
            }
            STATUS.STATUS_RELEASE_TO_LOAD_MORE -> {
                mStatus = STATUS.STATUS_LOADING_MORE
                fixCurrentStatusLayout()
                mLoadMoreCallback.onLoadMore()
            }
            STATUS.STATUS_DEFAULT -> {
            }
            else -> throw IllegalStateException("illegal state: " + STATUS.getStatus(mStatus))
        }

        if (mDebug) {
            Log.i(TAG, STATUS.getStatus(mLastStatus) + " -> " + STATUS.getStatus(mStatus))
        }
    }

    /**
     * auto refresh or cancel
     *
     * @param refreshing
     */
    fun setRefreshing(refreshing: Boolean) {
        if (!mRefreshEnabled || mHeaderView == null) {
            return
        }
        this.mAutoLoading = refreshing
        if (refreshing) {
            if (STATUS.isStatusDefault(mStatus)) {
                mStatus = STATUS.STATUS_SWIPING_TO_REFRESH
                scrollDefaultToRefreshing()
            }
        } else {
            if (STATUS.isRefreshing(mStatus)) {
                mRefreshCallback.onComplete()
                postDelayed({ scrollRefreshingToDefault() }, mRefreshCompleteDelayDuration.toLong())
            }
        }
    }

    /**
     * auto loading more or cancel
     *
     * @param loadingMore
     */
    fun setLoadingMore(loadingMore: Boolean) {
        if (!mLoadMoreEnabled || mFooterView == null) {
            return
        }
        this.mAutoLoading = loadingMore
        if (loadingMore) {
            if (STATUS.isStatusDefault(mStatus)) {
                mStatus = STATUS.STATUS_SWIPING_TO_LOAD_MORE
                scrollDefaultToLoadingMore()
            }
        } else {
            if (STATUS.isLoadingMore(mStatus)) {
                mLoadMoreCallback.onComplete()
                postDelayed({ scrollLoadingMoreToDefault() }, mLoadMoreCompleteDelayDuration.toLong())
            }
        }
    }

    fun onComplete() {
        if (STATUS.isRefreshing(mStatus)) {
            setRefreshing(false)
        }

        if (STATUS.isLoadingMore(mStatus)) {
            setLoadingMore(false)
        }
    }

}

/**
 * 布局类型
 */
object STYLE {
    val CLASSIC = 0
    val ABOVE = 1
    val BLEW = 2
    val SCALE = 3
}

object STATUS {

    val STATUS_REFRESH_RETURNING = -4
    val STATUS_REFRESHING = -3
    val STATUS_RELEASE_TO_REFRESH = -2
    val STATUS_SWIPING_TO_REFRESH = -1
    val STATUS_DEFAULT = 0
    val STATUS_SWIPING_TO_LOAD_MORE = 1
    val STATUS_RELEASE_TO_LOAD_MORE = 2
    val STATUS_LOADING_MORE = 3
    val STATUS_LOAD_MORE_RETURNING = 4

    fun isRefreshing(status: Int) = status == STATUS.STATUS_REFRESHING

    fun isLoadingMore(status: Int) = status == STATUS.STATUS_LOADING_MORE

    fun isReleaseToRefresh(status: Int) = status == STATUS.STATUS_RELEASE_TO_REFRESH

    fun isReleaseToLoadMore(status: Int) = status == STATUS.STATUS_RELEASE_TO_LOAD_MORE

    fun isSwipingToRefresh(status: Int) = status == STATUS.STATUS_SWIPING_TO_REFRESH

    fun isSwipingToLoadMore(status: Int) = status == STATUS.STATUS_SWIPING_TO_LOAD_MORE

    fun isRefreshStatus(status: Int) = status < STATUS.STATUS_DEFAULT

    fun isLoadMoreStatus(status: Int) = status > STATUS.STATUS_DEFAULT

    fun isStatusDefault(status: Int) = status == STATUS.STATUS_DEFAULT

    fun getStatus(status: Int) = when (status) {
        STATUS_REFRESH_RETURNING -> "status_refresh_returning"
        STATUS_REFRESHING -> "status_refreshing"
        STATUS_RELEASE_TO_REFRESH -> "status_release_to_refresh"
        STATUS_SWIPING_TO_REFRESH -> "status_swiping_to_refresh"
        STATUS_DEFAULT -> "status_default"
        STATUS_SWIPING_TO_LOAD_MORE -> "status_swiping_to_load_more"
        STATUS_RELEASE_TO_LOAD_MORE -> "status_release_to_load_more"
        STATUS_LOADING_MORE -> "status_loading_more"
        STATUS_LOAD_MORE_RETURNING -> "status_load_more_returning"
        else -> "status_illegal!"
    }

    fun printStatus(status: Int) {
        Log.i("SWIPE_TO_LOAD_STATUS", "printStatus:" + getStatus(status))
    }
}

interface SwipeTrigger {
    fun onPrepare()
    fun onMove(y: Int, isComplete: Boolean, automatic: Boolean)
    fun onRelease()
    fun onComplete()
    fun onReset()
}

interface SwipeRefreshTrigger {
    fun onRefresh()
}

interface SwipeLoadMoreTrigger {
    fun onLoadMore()
}

/**
 * refresh event callback
 */
abstract class RefreshCallback : SwipeTrigger, SwipeRefreshTrigger

/**
 * load more event callback
 */
abstract class LoadMoreCallback : SwipeTrigger, SwipeLoadMoreTrigger

interface OnRefreshListener {
    fun onRefresh()
}

interface OnLoadMoreListener {
    fun onLoadMore()
}


