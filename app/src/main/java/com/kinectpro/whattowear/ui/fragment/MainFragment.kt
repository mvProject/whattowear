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
import com.kinectpro.whattowear.data.model.location.PlaceTrip
import com.kinectpro.whattowear.data.wrapper.Status
import com.kinectpro.whattowear.databinding.MainFragmentBinding
import com.kinectpro.whattowear.ui.WeatherConditionsAdapter
import com.kinectpro.whattowear.ui.viewmodel.MainViewModel
import com.kinectpro.whattowear.utils.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.*

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
              /*
                when (viewModel.obtainMatchAllConditionsForWeatherRequest()) {
                    DATE_ERROR_MAX_LENGTH_EXCEEDED -> {
                        Toast.makeText(
                            context,
                            getString(R.string.message_error_trip_to_long_range),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    null -> viewModel.convertWeatherListToWeatherCondition(viewModel.getSelectedPlaceWeatherData())
                }

               */
            }
        })

        viewModel.selectedPlaceStatus.observe(viewLifecycleOwner, Observer<String> {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.selectedTripCondition.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    Glide.with(this).load(R.drawable.waiting).into(waitingImage)
                }
                Status.SUCCESS -> {
                    txtNightWeatherSummary.text = it.data?.nightTemp?.convertToReadableRange()
                    txtDayWeatherSummary.text = it.data?.dayTemp?.convertToReadableRange()
                    wearList.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = WeatherConditionsAdapter(it.data?.conditionDates!!)
                    }
                }
                Status.ERROR -> Toast.makeText(context,it.error?.message,Toast.LENGTH_SHORT).show()
            }
        })

        mainFragmentBinding.btnSearchWear.setOnClickListener {
            if (viewModel.selectedDestinationPlace.value == null) {
                Toast.makeText(
                    context,
                    getString(R.string.message_error_trip_destination),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            viewModel.obtainSelectedDestinationWeatherRequest(viewModel.getSelectedPlaceWeatherData())
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
                it.datePicker.minDate = calendar.timeInMillis
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
}


