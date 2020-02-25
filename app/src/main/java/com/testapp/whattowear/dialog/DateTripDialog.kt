package com.testapp.whattowear.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle

class DateTripDialog constructor(
    context: Context,
    private val startDateValueSelected: Long?,
    year: Int,
    month: Int,
    day: Int,
    listener: OnDateSetListener
) : DatePickerDialog(context, listener, year, month, day){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startDateValueSelected?.let{
            this.datePicker.minDate = startDateValueSelected
        }
    }
}
