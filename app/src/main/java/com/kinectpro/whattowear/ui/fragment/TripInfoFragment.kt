package com.kinectpro.whattowear.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.databinding.TripInfoFragmentBinding
import com.kinectpro.whattowear.ui.adapter.DefaultCheckListAdapter
import com.kinectpro.whattowear.ui.adapter.ItemAdapter
import com.kinectpro.whattowear.ui.adapter.PersonalCheckListAdapter
import com.kinectpro.whattowear.ui.viewmodel.TripInfoViewModel
import com.kinectpro.whattowear.ui.viewmodel.TripInfoViewModelFactory
import com.kinectpro.whattowear.utils.*
import kotlinx.android.synthetic.main.trip_info_fragment.*

class TripInfoFragment : Fragment(), PersonalCheckListAdapter.OnMenuItemSelectedListener,
    ItemAdapter.OnAddItemSelectedListener {

    private var selectedTripId = ""
    private lateinit var tripInfoViewModel: TripInfoViewModel
    private lateinit var tripInfoViewModelFactory: TripInfoViewModelFactory
    private lateinit var tripInfoFragmentBinding: TripInfoFragmentBinding

    private val itemAdapter = ItemAdapter(this@TripInfoFragment)

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
            activity?.let { activity ->
                tripInfoViewModelFactory =
                    TripInfoViewModelFactory(activity.application, selectedTripId)
                tripInfoViewModel = ViewModelProvider(
                    this,
                    tripInfoViewModelFactory
                ).get(TripInfoViewModel::class.java)
            }

            // need for checking is selected trip has default checklist for showing ui
            tripInfoViewModel.defaultCheckList.observe(viewLifecycleOwner, Observer {
            })

            tripInfoViewModel.tripDetailInformation.observe(
                viewLifecycleOwner,
                Observer {
                    tripInfoFragmentBinding.tripModel = tripInfoViewModel

                    tripDefaultCheckList.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(context)
                        adapter =
                            DefaultCheckListAdapter(it.wears.filteredDefaultType(true))
                    }

                    tripPersonalCheckList.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(context)
                        adapter = MergeAdapter(
                            PersonalCheckListAdapter(
                                it.wears.filteredDefaultType(false),
                                this@TripInfoFragment
                            ),
                            itemAdapter
                        )
                    }
                }
            )

            tripInfoViewModel.defaultListVisibility.observe(viewLifecycleOwner, Observer {
                setViewVisibility(tripDefaultCheckList, it)
                setTextViewTitleAndIcon(txtHideShow, it)
            })

            txtHideShow.setOnClickListener {
                tripInfoViewModel.changeVisibility()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        tripInfoViewModel.updateWears()
    }

    override fun onEditMenuAction(wear: WearItem) {
        itemAdapter.setWearForEdit(wear)
    }

    override fun onDeleteMenuAction(wear: WearItem) {
        tripInfoViewModel.deleteSelectedWearFromDb(wear)
    }

    override fun onAddAction(wear: WearItem, isNewItem: Boolean) {
        when (isNewItem) {
            true -> {
                tripInfoViewModel.addPersonalWear(wear)
            }
            false -> {
                tripInfoViewModel.editPersonalWear(wear)
            }
        }
    }
}
