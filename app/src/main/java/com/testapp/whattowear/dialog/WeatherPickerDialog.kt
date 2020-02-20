package com.testapp.whattowear.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class WeatherPickerDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val START_DATE_DIALOG = "StartDateDialog"
    private val END_DATE_DIALOG = "EndDateDialog"

    private val c: Calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(context!!, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        when (tag){
            START_DATE_DIALOG -> {
                Log.d("Wear","start date selected")
            }
            END_DATE_DIALOG -> {
                Log.d("Wear","end date selected")
            }
        }
        c.set(year,month,day,12,0)
        Log.d("Wear","$tag date selected ${c.timeInMillis}")
    }
}