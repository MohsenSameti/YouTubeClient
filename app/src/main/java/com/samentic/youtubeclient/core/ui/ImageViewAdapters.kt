package com.samentic.youtubeclient.core.ui

import android.animation.ValueAnimator
import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.samentic.youtubeclient.R

fun loadImage(
    imageView: ImageView,
    url: String?,
    @DrawableRes errorRes: Int? = null,
    isCircular: Boolean = false
) {
    if (url == null) return
    imageView.load(url) {
        val drawable = ShimmerDrawable().also {
            it.setShimmer(
                Shimmer.ColorHighlightBuilder()
                    .setBaseColor(imageView.colorCompat(R.color.grey_300))
                    .setBaseAlpha(0.8f)
                    .setDuration(1000L)
                    .setRepeatDelay(550L)
                    .setRepeatMode(ValueAnimator.REVERSE)
                    .setDropoff(1f)
                    .setShape(Shimmer.Shape.RADIAL)
                    .setHighlightColor(imageView.colorCompat(R.color.blue_grey_50))
                    .build()
            )
        }
        placeholder(drawable)
        listener(
            onStart = { drawable.startShimmer() },
            onCancel = { drawable.stopShimmer() },
            onError = { _, _ -> drawable.stopShimmer() },
            onSuccess = { _, _ -> drawable.stopShimmer() }
        )
        errorRes?.let { error(it) }
        diskCachePolicy(CachePolicy.ENABLED)
        memoryCachePolicy(CachePolicy.ENABLED)

        if(isCircular){
            transformations(CircleCropTransformation())
        }
    }
}