package com.aspsine.swipetoloadlayout.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.R;
import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * 刷新控件
 */
public class RefreshView extends FrameLayout implements SwipeRefreshTrigger, SwipeTrigger {

    private ImageView iv;
    private TextView tv;

    public RefreshView(Context context) {
        this(context, null);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = View.inflate(context, R.layout.layout_refresh, this);

        iv = (ImageView) view.findViewById(R.id.iv);
        tv = (TextView) view.findViewById(R.id.tv);


    }

    @Override
    public void onRefresh() {
        iv.setImageResource(R.drawable.loading);
        AnimationDrawable animation = (AnimationDrawable) iv.getDrawable();
        animation.start();
        tv.setText(R.string.loading);
    }

    @Override
    public void onPrepare() {
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (isComplete) {
            return;
        }

        float moveY = 100.0f * y;
        float height = 1.0f * getHeight();

        float degree = moveY / height;

        if (degree <= 100) {
            tv.setText(R.string.pull_to_refresh);
            if (degree < 40) {
                iv.setImageResource(R.drawable.pull_end_image_frame_01);
            } else if (degree < 55) {
                iv.setImageResource(R.drawable.pull_end_image_frame_02);
            } else if (degree < 70) {
                iv.setImageResource(R.drawable.pull_end_image_frame_03);
            } else if (degree < 85) {
                iv.setImageResource(R.drawable.pull_end_image_frame_04);
            } else {
                iv.setImageResource(R.drawable.pull_end_image_frame_05);
            }

        } else {
            tv.setText(R.string.release_to_refresh);
        }
    }

    @Override
    public void onRelease() {
    }

    @Override
    public void onComplete() {
        tv.setText(R.string.complete);

        if (iv.getDrawable() instanceof AnimationDrawable) {
            AnimationDrawable animation = (AnimationDrawable) iv.getDrawable();
            animation.stop();
            iv.clearAnimation();
        }

        iv.setImageResource(R.drawable.refreshing_image_frame_01);
    }

    @Override
    public void onReset() {
    }
}
