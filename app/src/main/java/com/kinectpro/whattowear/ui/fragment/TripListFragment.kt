package com.kinectpro.whattowear.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.trip.TripItem
import com.kinectpro.whattowear.databinding.TripListFragmentBinding
import com.kinectpro.whattowear.ui.adapter.TripsAdapter
import com.kinectpro.whattowear.ui.viewmodel.TripListViewModel
import kotlinx.android.synthetic.main.trip_list_fragment.*

class TripListFragment : Fragment(), TripsAdapter.OnItemSelectedListener {

    private lateinit var tripListViewModel: TripListViewModel
    private lateinit var tripListFragmentBinding: TripListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tripListFragmentBinding = TripListFragmentBinding.inflate(inflater, container, false)
        return tripListFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tripListViewModel = ViewModelProvider(this).get(TripListViewModel::class.java)

        tripListViewModel.allTrips.observe(viewLifecycleOwner, Observer {
            it?.let {
                trip_list_view.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter =
                        TripsAdapter(
                            it, this@TripListFragment
                        )
                }
            }
        })
    }

    // prepare for details screen - achieve single trip info with check list)
    override fun onItemClick(trip: TripItem) {
        tripListViewModel.loadSingleTrip(trip).observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(
                    context,
                    it.trip.destinationPlace + " " + it.checkList.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onMenuAction(trip: TripItem, item: MenuItem?) {
        when (item!!.itemId) {
            R.id.trip_item_edit -> {
                tripListViewModel.editSelectedTrip(trip)
                //   val action = TripListFragmentDirections.actionTripListFragmentToTripInfo("id")
                //   view?.findNavController()?.navigate(action)
            }
            R.id.trip_item_delete -> {
                tripListViewModel.deleteSelectedTripFromDb(trip)
            }
        }
    }
}
