package com.testapp.whattowear.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.testapp.whattowear.ui.viewmodel.MainViewModel
import java.util.*

class TripDatePickerDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var mainViewModel: MainViewModel

    private val c: Calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(context!!, this, year, month, day)
        dpd.datePicker.minDate = c.timeInMillis
        return dpd
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        mainViewModel = ViewModelProvider(parentFragment as ViewModelStoreOwner).get(MainViewModel::class.java)
        c.set(year,month,day,12,0,0)

        val selectedDateInSeconds = c.timeInMillis
        when (tag){
            START_DATE_DIALOG -> {
                mainViewModel.tripStartDate = selectedDateInSeconds
            }
            END_DATE_DIALOG -> {
                mainViewModel.tripEndDate = selectedDateInSeconds
            }
        }
    }

    companion object{
        const val START_DATE_DIALOG = "StartDateDialog"
        const val END_DATE_DIALOG = "EndDateDialog"
    }
}