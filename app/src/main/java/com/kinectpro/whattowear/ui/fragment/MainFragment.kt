package com.kinectpro.whattowear.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.kinectpro.whattowear.data.model.location.PlaceTrip
import com.kinectpro.whattowear.databinding.MainFragmentBinding
import com.kinectpro.whattowear.ui.WeatherConditionsAdapter
import com.kinectpro.whattowear.ui.viewmodel.MainViewModel
import com.kinectpro.whattowear.utils.DATE_START_MAX_LENGTH_ALLOWED
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

        viewModel.selectedDestinationPlace.observe(viewLifecycleOwner, Observer<PlaceTrip> {
            it?.let {
                viewModel.obtainSelectedDestinationWeatherRequest(true)
            }
        })

        viewModel.selectedPlaceStatus.observe(viewLifecycleOwner, Observer<String> {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

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

        mainFragmentBinding.btnSearchWear.setOnClickListener {
            viewModel.obtainSelectedDestinationWeatherRequest(false)
        }

        setupPlaceSelectListener()

        mainFragmentBinding.btnTripStartDateSelect.setOnClickListener {
            val calendar = Calendar.getInstance()
            val tripStartDateSelectionDialog = DatePickerDialog(
                context!!,
                viewModel.tripStartDateSelectionDialogListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).also {
                if (viewModel.tripStartDateLive.value!! == 0L) {
                    it.datePicker.minDate = calendar.timeInMillis
                } else {
                    it.datePicker.minDate = viewModel.tripStartDateLive.value!!
                }
                it.datePicker.maxDate =
                    calendar.timeInMillis + TimeUnit.DAYS.toMillis(DATE_START_MAX_LENGTH_ALLOWED)
            }
            tripStartDateSelectionDialog.show()
        }

        mainFragmentBinding.btnTripEndDateSelect.setOnClickListener {
            val calendar = Calendar.getInstance()
            val tripEndDateSelectionDialog = DatePickerDialog(
                context!!,
                viewModel.tripEndDateSelectionDialogListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).also {
                it.datePicker.minDate = viewModel.tripStartDateLive.value!!
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
        }

        clear_button_view.setOnClickListener {
            autoComplete.setText("")
            viewModel.selectedDestinationPlace.value = null
            viewModel.tripStartDateLive.value = 0L
            viewModel.tripEndDateLive.value = 0L

            mainFragmentBinding.wearList.visibility = View.INVISIBLE
            mainFragmentBinding.cardDatesSummary.visibility = View.INVISIBLE
            mainFragmentBinding.txtGoodTripMessage.visibility = View.INVISIBLE
        }
    }

    private fun Int.getProperErrorMessage(): String {
        return when (this) {
            ErrorCodes.SocketTimeOut.code -> getString(R.string.message_response_error_timeout)
            ErrorCodes.UnknownHostException.code -> getString(R.string.message_response_error_unknown_host)
            ErrorCodes.LanguageRequestException.code -> getString(R.string.message_response_error_invalid_lang)
            ErrorCodes.TargetRequestAccessException.code -> getString(R.string.message_response_error_access_denied)
            ErrorCodes.TargetRequestSourceException.code -> getString(R.string.message_response_error_request_target)
            ErrorCodes.EmptyDestinationException.code -> getString(R.string.message_error_trip_destination)
            ErrorCodes.EmptyDatesException.code -> getString(R.string.message_error_trip_date_not_select)
            ErrorCodes.InvalidDatesRangeException.code -> getString(R.string.message_error_trip_date_range)
            ErrorCodes.TooLongDateRangeIntervalException.code -> getString(R.string.message_error_trip_to_long_range)
            ErrorCodes.NoInternetConnectionException.code -> getString(R.string.message_response_error_no_internet)
            else -> getString(R.string.message_response_error_unspecified)
        }
    }
}


