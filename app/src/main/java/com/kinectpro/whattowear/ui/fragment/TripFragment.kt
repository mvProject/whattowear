package com.kinectpro.whattowear.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
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
import com.kinectpro.whattowear.databinding.DialogLayoutBinding
import com.kinectpro.whattowear.databinding.TripFragmentBinding
import com.kinectpro.whattowear.ui.adapter.WeatherConditionsAdapter
import com.kinectpro.whattowear.ui.viewmodel.TripViewModel
import com.kinectpro.whattowear.utils.DATE_RANGE_MAX_LENGTH_ALLOWED
import com.kinectpro.whattowear.utils.convertToReadableRange
import kotlinx.android.synthetic.main.trip_fragment.*
import java.util.*
import java.util.concurrent.TimeUnit

class TripFragment : Fragment() {
    private lateinit var tripViewModel: TripViewModel
    private lateinit var tripFragmentBinding: TripFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), BuildConfig.GOOGLE_PLACE_API_KEY)
        }
        tripFragmentBinding = TripFragmentBinding.inflate(inflater, container, false)
        return tripFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tripViewModel = ViewModelProvider(this).get(TripViewModel::class.java)

        tripFragmentBinding.mainViewModel = tripViewModel
        tripFragmentBinding.lifecycleOwner = this

        /*
         Observes destination and date range and obtain new forecast on changing
         */
        tripViewModel.selectedTrip.observe(viewLifecycleOwner, Observer {})

        /*
         Observes status of selecting place
         Show status message if error appears
         */
        tripViewModel.selectedPlaceStatus.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        /*
         Observes selected trip status
         Display weather forecast on success or or proper message on error
         */
        tripViewModel.selectedTripCondition.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceStatus.LOADING -> {
                    Glide.with(this).load(R.drawable.waiting).into(waitingImage)
                }
                ResourceStatus.SUCCESS -> {
                    txtNightWeatherSummary.text =
                        it.data?.nightTemp?.convertToReadableRange(requireContext())
                    txtDayWeatherSummary.text =
                        it.data?.dayTemp?.convertToReadableRange(requireContext())

                    wearList.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = WeatherConditionsAdapter(
                            it.data?.conditionDates!!
                        )
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
        tripFragmentBinding.btnTripStartDateSelect.setOnClickListener {
            val calendar = Calendar.getInstance()
            val currentDate = calendar.timeInMillis
            tripViewModel.tripRangeStartDateValue.value?.let {
                // if start date was already selected, it will be init value for calendar
                if (it > 0) {
                    calendar.timeInMillis = it
                }
            }
            val tripStartDateSelectionDialog = DatePickerDialog(
                requireContext(),
                tripViewModel.tripStartDateSelectionDialogListener,
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
        tripFragmentBinding.btnTripEndDateSelect.setOnClickListener {
            val calendar = Calendar.getInstance()
            tripViewModel.tripRangeEndDateValue.value?.let {
                // if end date was already selected, it will be init value for calendar
                if (it > 0) {
                    calendar.timeInMillis = it
                }
            }
            val tripEndDateSelectionDialog = DatePickerDialog(
                requireContext(),
                tripViewModel.tripEndDateSelectionDialogListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).also {
                tripViewModel.tripRangeStartDateValue.value?.let { date ->
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
                    Place.Field.LAT_LNG
                )
            )
            setTypeFilter(TypeFilter.CITIES)
            setOnPlaceSelectedListener(tripViewModel.getTripDestinationPlaceSelected())
            // Set place if it was previously saved
            tripViewModel.selectedDestinationPlace.value?.let {
                autoComplete.setText(it.name)
            }
        }

        clear_button_view.setOnClickListener {
            autoComplete.setText("")
            tripViewModel.clearTripSelection()

            tripFragmentBinding.apply {
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
            ErrorCodes.NoForecastException.code -> getString(R.string.message_response_error_no_forecast)
            else -> getString(R.string.message_response_error_unspecified)
        }
    }

    override fun onStop() {
        super.onStop()
        tripViewModel.saveLastSelectedPlaceToCache()
    }


    fun showDialog() {
        val dialog: AlertDialog
        val binding: DialogLayoutBinding = DialogLayoutBinding.inflate(LayoutInflater.from(context))
        AlertDialog.Builder(requireContext()).run {
            setView(binding.root)
            dialog = this.show()
        }

        binding.apply {
            dialogMessage.setOnClickListener {
                checkBox.isChecked = !checkBox.isChecked
            }
            btnDialogCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnDialogOk.setOnClickListener {
                when (tripViewModel.saveTripToDatabase(checkBox.isChecked)) {
                    true -> {
                        view?.findNavController()
                            ?.navigate(R.id.action_TripFragment_to_TripListFragment)
                    }
                    false -> {
                        Toast.makeText(
                            context,
                            getString(R.string.error_empty_trip_selected),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
                dialog.dismiss()
            }
        }
    }
}


