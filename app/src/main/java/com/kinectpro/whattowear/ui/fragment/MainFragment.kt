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
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.kinectpro.whattowear.BuildConfig
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.location.PlaceTrip
import com.kinectpro.whattowear.data.wrapper.Status
import com.kinectpro.whattowear.databinding.MainFragmentBinding
import com.kinectpro.whattowear.ui.WeatherConditionsAdapter
import com.kinectpro.whattowear.ui.viewmodel.MainViewModel
import com.kinectpro.whattowear.utils.GetDummy
import com.kinectpro.whattowear.utils.convertToReadableRange
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
            // TODO weather achieve
        })

        viewModel.selectedPlaceStatus.observe(viewLifecycleOwner, Observer<String> {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        mainFragmentBinding.btnSearchWear.setOnClickListener {
            viewModel.getSelectedPlaceWeatherData()?.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Status.LOADING -> {
                        mainFragmentBinding.progressIndicator.visibility = View.VISIBLE
                        mainFragmentBinding.waitingImage.visibility = View.VISIBLE
                        mainFragmentBinding.txtNightWeatherSummary.visibility = View.INVISIBLE
                        mainFragmentBinding.txtDayWeatherSummary.visibility = View.INVISIBLE
                        Glide.with(this).load(R.drawable.waiting).into(waitingImage)
                    }
                    Status.SUCCESS -> {
                        mainFragmentBinding.progressIndicator.visibility = View.INVISIBLE
                        mainFragmentBinding.waitingImage.visibility = View.INVISIBLE
                        mainFragmentBinding.txtNightWeatherSummary.visibility = View.VISIBLE
                        mainFragmentBinding.txtDayWeatherSummary.visibility = View.VISIBLE

                        Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()

                        wearList.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter = WeatherConditionsAdapter(GetDummy().conditionDates)
                        }
                        txtNightWeatherSummary.text = GetDummy().nightTemp.convertToReadableRange()
                        txtDayWeatherSummary.text = GetDummy().dayTemp.convertToReadableRange()
                    }
                    Status.ERROR -> {
                    }
                }
            })
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
            childFragmentManager.findFragmentById(com.kinectpro.whattowear.R.id.autocompleteFragment) as AutocompleteSupportFragment

        autoComplete.apply {
            retainInstance = true
            setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
            setOnPlaceSelectedListener(viewModel.getTripDestinationPlaceSelected())
        }
    }
}


