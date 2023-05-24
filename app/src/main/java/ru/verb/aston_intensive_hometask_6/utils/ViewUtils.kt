package ru.verb.aston_intensive_hometask_6.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.verb.aston_intensive_hometask_6.R

fun ImageView.loadImage(link: String) {
    Glide.with(this)
        .load(link)
        .circleCrop()
        .placeholder(R.drawable.ic_baseline_person_2_24)
        .error(R.drawable.ic_baseline_person_2_24)
        .into(this)
}