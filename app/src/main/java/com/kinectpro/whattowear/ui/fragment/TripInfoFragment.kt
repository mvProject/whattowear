package com.kinectpro.whattowear.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.databinding.TripInfoFragmentBinding
import com.kinectpro.whattowear.ui.adapter.DefaultCheckListAdapter
import com.kinectpro.whattowear.ui.adapter.ItemAdapter
import com.kinectpro.whattowear.ui.adapter.PersonalCheckListAdapter
import com.kinectpro.whattowear.ui.viewmodel.TripInfoViewModel
import com.kinectpro.whattowear.ui.viewmodel.TripInfoViewModelFactory
import com.kinectpro.whattowear.utils.*
import kotlinx.android.synthetic.main.trip_info_fragment.*

class TripInfoFragment : Fragment() {

    private var selectedTripId = ""
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

        selectedTripId = args.tripId
        if (selectedTripId.isNotEmpty()) {
            activity?.let {
                tripInfoViewModelFactory = TripInfoViewModelFactory(it.application, selectedTripId)
                tripInfoViewModel = ViewModelProvider(
                    this,
                    tripInfoViewModelFactory
                ).get(TripInfoViewModel::class.java)

                tripInfoViewModel.tripDetail.observe(viewLifecycleOwner, Observer { item ->
                    tripInfoFragmentBinding.tripItemInfo = item.trip

                    tripDefaultCheckList.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(context)
                        adapter =
                            DefaultCheckListAdapter(item.wears.filteredType(true))
                    }

                    // hide default check list ui when it empty
                    if (tripInfoViewModel.isDefaultListEmpty()) {
                        txtHideShow.visibility = View.GONE
                        txtDefaultListTitle.visibility = View.GONE
                        tripDefaultCheckList.visibility = View.GONE
                    }

                    val adapter1 =
                        PersonalCheckListAdapter(tripInfoViewModel)
                    val adapter2 = ItemAdapter(tripInfoViewModel)
                    val mergedAdapter = MergeAdapter(adapter1, adapter2)

                    tripPersonalCheckList.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(context)
                        adapter = mergedAdapter
                    }
                })

                tripInfoViewModel.defaultListVisibility.observe(viewLifecycleOwner, Observer {
                    when (it) {
                        true -> {
                            tripDefaultCheckList.visibility = View.VISIBLE
                            val icon =
                                requireContext().resources.getDrawable(R.drawable.ic_arrow_up, null)
                            txtHideShow.setCompoundDrawablesWithIntrinsicBounds(
                                null,
                                null,
                                icon,
                                null
                            )
                            txtHideShow.text = requireContext().getString(R.string.hide_title)
                        }
                        else -> {
                            tripDefaultCheckList.visibility = View.GONE
                            val icon =
                                requireContext().resources.getDrawable(
                                    R.drawable.ic_arrow_down,
                                    null
                                )
                            txtHideShow.setCompoundDrawablesWithIntrinsicBounds(
                                null,
                                null,
                                icon,
                                null
                            )
                            txtHideShow.text = requireContext().getString(R.string.show_title)
                        }
                    }
                })


            }
            txtHideShow.setOnClickListener {
                tripInfoViewModel.changeVisibility()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        tripInfoViewModel.updateWears((tripDefaultCheckList.adapter as DefaultCheckListAdapter).getCurrentWears())
    }
}
