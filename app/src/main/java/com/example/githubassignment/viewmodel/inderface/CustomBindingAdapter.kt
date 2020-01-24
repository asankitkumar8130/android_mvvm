package com.example.githubassignment.viewmodel.inderface

import android.view.View
import androidx.databinding.BindingAdapter

class CustomBindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter("visibleGone")
        fun showHide(view: View, show: Boolean) {
            view.visibility = if (show) View.VISIBLE else View.GONE
        }
    }
}