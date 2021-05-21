package com.mnemo.samantha.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageLoad")
fun bindImageView(imageView: ImageView, imagePath: String){

    Glide.with(imageView.context)
        .load(imagePath)
        .into(imageView)
}



