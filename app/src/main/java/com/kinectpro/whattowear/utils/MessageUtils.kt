package com.kinectpro.whattowear.utils

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kinectpro.whattowear.R

/**
 * Show material dialog with positive and neutral button
 * @param context application context
 * @param positiveListener on positive button click listener
 * @param neutralListener on neutral button click listener
 */
fun dialogShow(
    context: Context,
    positiveListener: DialogInterface.OnClickListener? = null,
    neutralListener: DialogInterface.OnClickListener? = null
) {
    val dialogBuilder = MaterialAlertDialogBuilder(
        context,
        R.style.ThemeOverlay_App_MaterialAlertDialog
    ).apply {
        setCancelable(true)
        setView(R.layout.dialog_layout)
        setPositiveButton(context.getString(R.string.dialog_button_yes), positiveListener)
        setNeutralButton(context.getString(R.string.dialog_button_no), neutralListener)
    }
    dialogBuilder.show()
}