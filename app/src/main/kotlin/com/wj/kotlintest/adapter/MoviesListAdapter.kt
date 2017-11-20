package com.wj.kotlintest.adapter

import android.graphics.Bitmap
import android.support.v7.graphics.Palette
import android.view.View
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.wj.kotlintest.R
import com.wj.kotlintest.base.BaseRvAdapter
import com.wj.kotlintest.base.BaseRvViewHolder
import com.wj.kotlintest.databinding.ItemMoviesListBinding
import com.wj.kotlintest.entity.MoviesEntity
import com.wj.kotlintest.glide.GlideApp
import com.wj.kotlintest.handler.MoviesItemHandler
import com.wj.kotlintest.net.UrlDefinition
import javax.inject.Inject

/**
 * 电影列表适配器类
 */
class MoviesListAdapter @Inject constructor()
    : BaseRvAdapter<MoviesListAdapter.ViewHolder,
        ItemMoviesListBinding,
        MoviesItemHandler,
        MoviesEntity>() {

    override fun layoutResID() = R.layout.item_movies_list

    override fun createViewHolder(view: View) = null

    override fun createViewHolder(binding: ItemMoviesListBinding) = ViewHolder(binding)

    class ViewHolder(binding: ItemMoviesListBinding) : BaseRvViewHolder<ItemMoviesListBinding, MoviesEntity>(binding) {
        override fun bindData(entity: MoviesEntity) {
            super.bindData(entity)
            // 获取 Context 对象
            val ctx = mBinding.iv.context
            GlideApp.with(ctx)
                    .asBitmap()
                    .placeholder(R.mipmap.img_default)
                    .load(UrlDefinition.POSTER_PATH + entity.backdrop_path)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into<BitmapImageViewTarget>(object : BitmapImageViewTarget(mBinding.iv) {
                        override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
                            super.onResourceReady(resource, transition)
                            // 图片加载完成后，从图片中取色，并设置为电影名背景色
                            Palette.from(resource)
                                    .generate({
                                        @Suppress("DEPRECATION")
                                        mBinding.v.setBackgroundColor(
                                                it.getVibrantColor(
                                                        ctx.resources
                                                                .getColor(R.color.colorPrimaryDark)
                                                )
                                        )
                                    })
                        }
                    })
        }
    }
}
