package com.example.arcookingclass.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.arcookingclass.R

@BindingAdapter("android:src")
fun setImageViewResource(imageView : ImageView, resource : Int){
    imageView.setImageResource(resource)
}

@BindingAdapter("android:text")
fun setTextView(textView : TextView, isLearned: Boolean){
    if(isLearned){
        textView.text = "수강 완료"
    }
    else{
        textView.text = ""
    }
}
