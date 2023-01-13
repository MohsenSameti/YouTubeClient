package com.samentic.youtubeclient.core.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.colorCompat(@ColorRes res: Int): Int {
    return ContextCompat.getColor(this, res)
}

val ViewGroup.inflater: LayoutInflater
    get() = LayoutInflater.from(this.context)

fun Context.dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
