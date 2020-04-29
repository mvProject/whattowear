package com.kinectpro.whattowear.utils

import android.graphics.drawable.Drawable
import com.google.android.material.textview.MaterialTextView
import com.kinectpro.whattowear.R

fun setTextViewTitleAndIcon(
    view: MaterialTextView,
    condition: Boolean
) {
    var icon: Drawable? = null
    when (condition) {
        true -> {
            icon = view.context.resources.getDrawable(R.drawable.ic_arrow_up, null)
            view.text = view.context.getString(R.string.hide_title)
        }
        false -> {
            icon = view.context.resources.getDrawable(R.drawable.ic_arrow_down, null)
            view.text = view.context.getString(R.string.show_title)
        }
    }
    view.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
}