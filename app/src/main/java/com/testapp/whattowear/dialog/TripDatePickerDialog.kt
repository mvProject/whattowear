package com.testapp.whattowear.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TripDatePickerDialog constructor(
    private val calendar: Calendar = Calendar.getInstance(),
    private val initialHours: Int = 12,
    private val initialMinutes: Int = 0,
    private val year: Int = calendar.get(Calendar.YEAR),
    private val month: Int = calendar.get(Calendar.MONTH),
    private val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    interface DatePickerDialogListener {
        fun getTripDateTypeSelectedValue(type: String, date: Long)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val startDateValueSelected = arguments?.getLong(END_DATE_DIALOG)
        if (startDateValueSelected != null) {
            calendar.timeInMillis = startDateValueSelected.toLong()
        }
        val dpd = DatePickerDialog(context!!, this, year, month, day)
        dpd.datePicker.minDate = calendar.timeInMillis
        return dpd
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        calendar.set(year, month, day, initialHours, initialMinutes)
        val listener = parentFragment as DatePickerDialogListener?
        val selectedDateInSeconds = calendar.timeInMillis
        when (tag) {
            START_DATE_DIALOG -> {
                listener?.getTripDateTypeSelectedValue(START_DATE_DIALOG, selectedDateInSeconds)
            }
            END_DATE_DIALOG -> {
                listener?.getTripDateTypeSelectedValue(END_DATE_DIALOG, selectedDateInSeconds)
            }
        }
    }

    companion object {
        const val START_DATE_DIALOG = "StartDateDialog"
        const val END_DATE_DIALOG = "EndDateDialog"
    }
}