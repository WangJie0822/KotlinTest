package com.wj.kotlintest.adapter

import android.graphics.Bitmap
import android.support.v7.graphics.Palette
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.wj.kotlintest.R
import com.wj.kotlintest.base.BaseRvAdapter
import com.wj.kotlintest.base.BaseRvViewHolder
import com.wj.kotlintest.bean.MoviesBean
import com.wj.kotlintest.databinding.ItemMoviesListBinding
import com.wj.kotlintest.glide.GlideApp
import com.wj.kotlintest.handler.MoviesItemHandler
import com.wj.kotlintest.net.UrlDefinition
import javax.inject.Inject

/**
 *
 *
 * @author 王杰
 */
class MoviesListAdapter @Inject constructor() : BaseRvAdapter<
        MoviesBean,
        MoviesListAdapter.ViewHolder,
        MoviesItemHandler,
        ItemMoviesListBinding>() {

    override fun layoutResID(): Int {
        return R.layout.item_movies_list
    }

    override fun createViewHolder(binding: ItemMoviesListBinding): ViewHolder {
        return ViewHolder(binding)
    }

    class ViewHolder(binding: ItemMoviesListBinding) : BaseRvViewHolder<ItemMoviesListBinding, MoviesBean>(binding) {
        override fun bindData(entity: MoviesBean) {
            super.bindData(entity)
            val ctx = mBinding.iv.context
            GlideApp.with(ctx)
                    .asBitmap()
                    .load(UrlDefinition.POSTER_PATH + entity.backdrop_path)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into<BitmapImageViewTarget>(object : BitmapImageViewTarget(mBinding.iv) {
                        override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
                            super.onResourceReady(resource, transition)
                            Palette.from(resource)
                                    .generate({
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