package com.example.pokemontask.Utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
@BindingAdapter("Imageloader")
fun loadWithGlide(image :ImageView ,url :String){
    Glide.with(image).load(url).into(image)
}