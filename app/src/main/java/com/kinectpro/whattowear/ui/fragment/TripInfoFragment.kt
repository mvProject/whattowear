package com.kinectpro.whattowear.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.kinectpro.whattowear.databinding.TripInfoFragmentBinding
import com.kinectpro.whattowear.ui.viewmodel.TripInfoViewModel

class TripInfoFragment : Fragment() {

    private lateinit var tripInfoViewModel: TripInfoViewModel
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

        tripInfoViewModel = ViewModelProvider(this).get(TripInfoViewModel::class.java)

        val selectedTrip = args.tripId

        //  tripInfoViewModel.loadSingleTrip(selectedTrip).observe(viewLifecycleOwner, Observer {
        //      Log.d("Wear",it.toString())
        //  })
        /*
            tripInfoFragmentBinding.tripItemInfo = selectedTrip
            selectedTrip.checkList?.let {
                tripCheckList.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = TripCheckListAdapter(it)
                }
            }

         */
    }
}
