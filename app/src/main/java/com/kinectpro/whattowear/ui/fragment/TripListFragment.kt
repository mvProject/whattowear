package com.kinectpro.whattowear.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kinectpro.whattowear.databinding.TripListFragmentBinding

import com.kinectpro.whattowear.ui.viewmodel.TripListViewModel

class TripListFragment : Fragment() {

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
                Log.d("Wear", it.size.toString())
            }
        })
    }

}
