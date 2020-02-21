package com.testapp.whattowear.ui.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.testapp.whattowear.BuildConfig
import com.testapp.whattowear.R
import com.testapp.whattowear.data.PlaceTrip
import com.testapp.whattowear.data.WeatherData
import com.testapp.whattowear.databinding.MainFragmentBinding
import com.testapp.whattowear.ui.viewmodel.MainViewModel

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
        mainFragmentBinding = MainFragmentBinding.inflate(inflater,container,false)
        return mainFragmentBinding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        mainFragmentBinding.mainViewModel = viewModel
        mainFragmentBinding.lifecycleOwner = this

        viewModel.singleWeatherList.observe(viewLifecycleOwner,Observer<MutableList<WeatherData>>{
            Log.d("Wear", "single weather - $it")
        })

        viewModel.selectedPlaceStatus.observe(viewLifecycleOwner,Observer<String>{
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        mainFragmentBinding.btnSearchWear.setOnClickListener {

            val dataRange = mutableListOf<Long>()
            dataRange.add(1582114347)
            dataRange.add(1582269744)
            dataRange.add(1582356144)

            viewModel.getSelectedPlaceWeatherRange(viewModel.selectedPlace.value,dataRange)
        }

        setupPlaceSelectListener()
    }
    private fun setupPlaceSelectListener(){

        val autoComplete = childFragmentManager.findFragmentById(R.id.autocompleteFragment) as AutocompleteSupportFragment

        autoComplete.apply {
            retainInstance = true
            setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
            setOnPlaceSelectedListener(viewModel.getDestinationPlace())
        }
    }
}


