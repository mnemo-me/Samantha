package com.mnemo.samantha.ui

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File

@BindingAdapter("loadImage")
fun ImageView.loadImage(imageFile: File){

    Glide.with(this)
        .load(imageFile)
        .apply(RequestOptions.skipMemoryCacheOf(true))
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
        .into(this)
}

@BindingAdapter("loadImageDrawable")
fun ImageView.loadImageDrawable(drawable: Drawable){

    Glide.with(this)
        .load(drawable)
        .apply(RequestOptions.skipMemoryCacheOf(true))
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
        .into(this)
}

@BindingAdapter("setTime")
fun TextView.setTime(timeInMinutes: Int){
    val hour = timeInMinutes.div(60)
    val hourString = if (hour < 10) "0$hour" else hour.toString()

    val minutes = timeInMinutes.rem(60)
    val minutesString = if (minutes == 0) "00" else minutes.toString()

    val timeString = "${hourString}:${minutesString}"
    text = timeString
}




