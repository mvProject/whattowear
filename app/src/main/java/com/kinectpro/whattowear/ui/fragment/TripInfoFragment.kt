package com.kinectpro.whattowear.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinectpro.whattowear.databinding.TripInfoFragmentBinding
import com.kinectpro.whattowear.ui.adapter.TripCheckListAdapter
import com.kinectpro.whattowear.ui.viewmodel.TripInfoViewModel
import com.kinectpro.whattowear.ui.viewmodel.TripInfoViewModelFactory
import kotlinx.android.synthetic.main.trip_info_fragment.*

class TripInfoFragment : Fragment() {

    private lateinit var tripInfoViewModel: TripInfoViewModel
    private lateinit var tripInfoViewModelFactory: TripInfoViewModelFactory
    private lateinit var tripInfoFragmentBinding: TripInfoFragmentBinding

    private val args: TripInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tripInfoFragmentBinding = TripInfoFragmentBinding.inflate(inflater, container, false)
        return tripInfoFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val selectedTripId = args.tripId
        if (selectedTripId.isNotEmpty()) {
            activity?.let {
                tripInfoViewModelFactory = TripInfoViewModelFactory(it.application, selectedTripId)
                tripInfoViewModel = ViewModelProvider(
                    this,
                    tripInfoViewModelFactory
                ).get(TripInfoViewModel::class.java)

                tripInfoViewModel.tripDetail.observe(viewLifecycleOwner, Observer { item ->
                    tripInfoFragmentBinding.tripItemInfo = item.trip
                    tripCheckList.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(context)
                        adapter = TripCheckListAdapter(item.wears)
                    }
                })
            }
        }
    }

    override fun onPause() {
        super.onPause()
        tripInfoViewModel.updateWears((tripCheckList.adapter as TripCheckListAdapter).getTripsSelection())
    }
}
