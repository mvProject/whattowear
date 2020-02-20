package com.testapp.whattowear.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.testapp.whattowear.ui.viewmodel.MainViewModel
import java.util.*

class TripDatePickerDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var mainViewModel: MainViewModel
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
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        c.set(year,month,day,12,0)
        when (tag){
            START_DATE_DIALOG -> {
                //mainViewModel.selectedTripStartDate.value = (c.timeInMillis / 1000)
            }
            END_DATE_DIALOG -> {
                //mainViewModel.selectedTripEndDate.value = (c.timeInMillis / 1000)
            }
        }
        Log.d("Wear","date selected ${c.timeInMillis / 1000}")
    }
    // TODO find solution
}