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
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.testapp.whattowear.data.PlaceTrip
import com.testapp.whattowear.R
import com.testapp.whattowear.ui.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private val apiKey = "AIzaSyAbiAj6LivbiUwyGh6YoU4sWiEd7Sfvrpk"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (!Places.isInitialized()) {
            Places.initialize(context!!, apiKey)
        }
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.selectedPlace.observe(viewLifecycleOwner,Observer<PlaceTrip>{
            // TODO weather achieve
        })

        getPlaceWhereToGo()
    }

    private fun getPlaceWhereToGo(){

        val autoComplete = childFragmentManager.findFragmentById(R.id.autocompleteFragment) as AutocompleteSupportFragment

        autoComplete.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        autoComplete.setOnPlaceSelectedListener(object : PlaceSelectionListener{
            override fun onError(status: Status) {
                Toast.makeText(context,status.statusMessage,Toast.LENGTH_SHORT).show()
            }

            override fun onPlaceSelected(place: Place) {
                viewModel.selectedPlace.value = PlaceTrip(
                    place.id!!,
                    place.name!!,
                    place.latLng.toString()
                )
            }
        })
    }

}


