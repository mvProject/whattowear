package com.kinectpro.whattowear.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.kinectpro.whattowear.R
import kotlinx.android.synthetic.main.dialog_layout.view.*

/**
 * Show material dialog with positive and neutral button
 */
class TripSavingDialog : DialogFragment() {

    interface DefaultListDialogListener {
        fun onFinishDialog(defaultListChecked: Boolean)
    }

    var onCheckListener: DefaultListDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val view = activity!!.layoutInflater.inflate(R.layout.dialog_layout, null)
            val builder = AlertDialog.Builder(it)
            builder.setCancelable(true)
                .setView(view)
                .setPositiveButton(context?.getString(R.string.dialog_button_yes)) { _, _ ->
                    val defaultCheck = view.checkBox.isChecked
                    onCheckListener?.onFinishDialog(defaultCheck)
                }
                .setNeutralButton(context?.getString(R.string.dialog_button_no), null)
            builder.create()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onCheckListener = activity as DefaultListDialogListener?
    }
}

