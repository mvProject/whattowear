package com.testapp.whattowear.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TripDatePickerDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val calendar: Calendar = Calendar.getInstance()
    private val initialHours = 12
    private val initialMinutes = 0

    interface DatePickerDialogListener {
        fun onDateSelectedDialog(type : String,date: Long)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val startDateValueSelected = arguments?.getLong(END_DATE_DIALOG)
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        if (startDateValueSelected != null){
            calendar.timeInMillis = startDateValueSelected
        }
        val dpd = DatePickerDialog(context!!, this, year, month, day)
        dpd.datePicker.minDate = calendar.timeInMillis
        return dpd
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        calendar.set(year,month,day,initialHours,initialMinutes)
        val listener = parentFragment as DatePickerDialogListener?
        val selectedDateInSeconds = calendar.timeInMillis
        when (tag){
            START_DATE_DIALOG -> {
                listener?.onDateSelectedDialog(START_DATE_DIALOG,selectedDateInSeconds)
            }
            END_DATE_DIALOG -> {
                listener?.onDateSelectedDialog(END_DATE_DIALOG,selectedDateInSeconds)
            }
        }
    }

    companion object{
        const val START_DATE_DIALOG = "StartDateDialog"
        const val END_DATE_DIALOG = "EndDateDialog"
    }
}