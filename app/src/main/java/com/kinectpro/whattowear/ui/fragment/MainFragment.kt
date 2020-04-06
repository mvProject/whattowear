package com.kinectpro.whattowear.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.kinectpro.whattowear.BuildConfig
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.enums.ErrorCodes
import com.kinectpro.whattowear.data.model.enums.ResourceStatus
import com.kinectpro.whattowear.databinding.MainFragmentBinding
import com.kinectpro.whattowear.ui.WeatherConditionsAdapter
import com.kinectpro.whattowear.ui.viewmodel.MainViewModel
import com.kinectpro.whattowear.utils.DATE_RANGE_MAX_LENGTH_ALLOWED
import com.kinectpro.whattowear.utils.convertToReadableRange
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var mainFragmentBinding: MainFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (!Places.isInitialized()) {
            Places.initialize(context!!, BuildConfig.GOOGLE_PLACE_API_KEY)
        }
        mainFragmentBinding = MainFragmentBinding.inflate(inflater, container, false)
        return mainFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainFragmentBinding.mainViewModel = viewModel
        mainFragmentBinding.lifecycleOwner = this

        /*
         Observes destination and date range and obtain new forecast on changing
         */
        viewModel.selectedTrip.observe(viewLifecycleOwner, Observer {})

        /*
         Observes status of selecting place
         Show status message if error appears
         */
        viewModel.selectedPlaceStatus.observe(viewLifecycleOwner, Observer<String> {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        /*
         Observes selected trip status
         Display weather forecast on success or or proper message on error
         */
        viewModel.selectedTripCondition.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceStatus.LOADING -> {
                    Glide.with(this).load(R.drawable.waiting).into(waitingImage)
                }
                ResourceStatus.SUCCESS -> {
                    txtNightWeatherSummary.text =
                        it.data?.nightTemp?.convertToReadableRange(context!!)
                    txtDayWeatherSummary.text = it.data?.dayTemp?.convertToReadableRange(context!!)
                    wearList.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = WeatherConditionsAdapter(it.data?.conditionDates!!)
                    }
                }
                ResourceStatus.ERROR -> {
                    it.errorCode?.let { error ->
                        Toast.makeText(context, error.getProperErrorMessage(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })

        setupPlaceSelectListener()

        /*
         Show calendar to select start date on TripStartDateSelect button click
         */
        mainFragmentBinding.btnTripStartDateSelect.setOnClickListener {
            val calendar = Calendar.getInstance()
            val currentDate = calendar.timeInMillis
            viewModel.tripRangeStartDateValue.value?.let {
                // if start date was already selected, it will be init value for calendar
                if (it > 0) {
                    calendar.timeInMillis = it
                }
            }
            val tripStartDateSelectionDialog = DatePickerDialog(
                context!!,
                viewModel.tripStartDateSelectionDialogListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).also {
                // Current date as default for minDate
                it.datePicker.minDate = currentDate
                // maxDate for selecting date is 30 days from current date
                it.datePicker.maxDate =
                    calendar.timeInMillis + TimeUnit.DAYS.toMillis(DATE_RANGE_MAX_LENGTH_ALLOWED)
            }
            tripStartDateSelectionDialog.show()
        }

        /*
         Show calendar to select end date on TripEndDateSelect button click
         */
        mainFragmentBinding.btnTripEndDateSelect.setOnClickListener {
            val calendar = Calendar.getInstance()
            viewModel.tripRangeEndDateValue.value?.let {
                // if end date was already selected, it will be init value for calendar
                if (it > 0) {
                    calendar.timeInMillis = it
                }
            }
            val tripEndDateSelectionDialog = DatePickerDialog(
                context!!,
                viewModel.tripEndDateSelectionDialogListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).also {
                viewModel.tripRangeStartDateValue.value?.let { date ->
                    // if trip start date is already set,it will be set as minDate value for date select suggestion
                    if (date > 0) {
                        it.datePicker.minDate = date
                        // maxDate for selecting date is 30 days from minDate value
                        it.datePicker.maxDate =
                            date + TimeUnit.DAYS.toMillis(DATE_RANGE_MAX_LENGTH_ALLOWED)
                    } else {
                        // else set current date as minDate
                        it.datePicker.minDate = calendar.timeInMillis
                    }
                }
            }
            tripEndDateSelectionDialog.show()
        }
    }

    private fun setupPlaceSelectListener() {

        val autoComplete =
            childFragmentManager.findFragmentById(R.id.autocompleteFragment) as AutocompleteSupportFragment

        autoComplete.apply {
            retainInstance = true
            setPlaceFields(
                listOf(
                    Place.Field.ID,
                    Place.Field.NAME,
                    Place.Field.LAT_LNG,
                    Place.Field.UTC_OFFSET
                )
            )
            setTypeFilter(TypeFilter.CITIES)
            setOnPlaceSelectedListener(viewModel.getTripDestinationPlaceSelected())
            // Set place if it was previously saved
            viewModel.selectedDestinationPlace.value?.let {
                autoComplete.setText(it.name)
            }
        }

        clear_button_view.setOnClickListener {
            autoComplete.setText("")
            viewModel.clearTripSelection()

            mainFragmentBinding.apply {
                wearList.visibility = View.INVISIBLE
                cardDatesSummary.visibility = View.INVISIBLE
                txtGoodTripMessage.visibility = View.INVISIBLE
            }
        }
    }

    private fun Int.getProperErrorMessage(): String {
        return when (this) {
            ErrorCodes.SocketTimeOut.code -> getString(R.string.message_response_error_timeout)
            ErrorCodes.UnknownHostException.code -> getString(R.string.message_response_error_unknown_host)
            ErrorCodes.LanguageRequestException.code -> getString(R.string.message_response_error_invalid_lang)
            ErrorCodes.TargetRequestAccessException.code -> getString(R.string.message_response_error_access_denied)
            ErrorCodes.TargetRequestSourceException.code -> getString(R.string.message_response_error_request_target)
            ErrorCodes.TargetDailyUsageLimitException.code -> getString(R.string.message_response_error_daily_limit_exceeded)
            ErrorCodes.EmptyDestinationException.code -> getString(R.string.message_error_trip_destination)
            ErrorCodes.EmptyDatesException.code -> getString(R.string.message_error_trip_date_not_select)
            ErrorCodes.TooLongDateRangeIntervalException.code -> getString(R.string.message_error_trip_to_long_range)
            ErrorCodes.NoInternetConnectionException.code -> getString(R.string.message_response_error_no_internet)
            ErrorCodes.EmptyStartDateException.code -> getString(R.string.message_error_trip_start_date_empty)
            ErrorCodes.StartDateIsGreaterException.code -> getString(R.string.message_error_trip_start_date_is_greater_than_end_date)
            else -> getString(R.string.message_response_error_unspecified)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveLastSelectedPlaceToLocalStorage()
    }
}


