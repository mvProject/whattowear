package com.testapp.whattowear.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TripDatePickerDialog constructor(
    private val calendar: Calendar = Calendar.getInstance(),
    private val selectedYear: Int = calendar.get(Calendar.YEAR),
    private val selectedMonth: Int = calendar.get(Calendar.MONTH),
    private val selectedDay: Int = calendar.get(Calendar.DAY_OF_MONTH),
    private val listener: DatePickerDialogListener
) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    interface DatePickerDialogListener {
        fun getTripDateTypeSelectedValue(type: String, date: Long)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val startDateValueSelected = arguments?.getLong(END_DATE_DIALOG)
        if (startDateValueSelected != null)
            calendar.timeInMillis = startDateValueSelected
        val dpd = DatePickerDialog(context!!, this, selectedYear, selectedMonth, selectedDay)
        dpd.datePicker.minDate = calendar.timeInMillis
        return dpd
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        calendar.set(year, month, day, INIT_DAY_HOUR, INIT_DAY_MINUTE)
        val selectedDateInSeconds = calendar.timeInMillis
        when (tag) {
            START_DATE_DIALOG -> {
                listener.getTripDateTypeSelectedValue(START_DATE_DIALOG, selectedDateInSeconds)
            }
            END_DATE_DIALOG -> {
                listener.getTripDateTypeSelectedValue(END_DATE_DIALOG, selectedDateInSeconds)
            }
        }
    }

    companion object {
        const val START_DATE_DIALOG = "StartDateDialog"
        const val END_DATE_DIALOG = "EndDateDialog"
        // for time of weather forecast data
        const val INIT_DAY_HOUR = 12
        const val INIT_DAY_MINUTE = 0
    }
}