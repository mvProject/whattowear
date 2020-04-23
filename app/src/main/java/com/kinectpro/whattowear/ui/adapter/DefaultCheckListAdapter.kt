package com.kinectpro.whattowear.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.databinding.DefaultChecklistItemBinding
import com.kinectpro.whattowear.ui.viewmodel.TripViewModel

class DefaultCheckListAdapter(viewModel: TripViewModel) :
    RecyclerView.Adapter<DefaultCheckListAdapter.DefaultCheckListHolder>() {

    private val tripViewModel = viewModel
    private var wears: List<WearItem> = tripViewModel.getDefaultCheckList()!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultCheckListHolder {
        return DefaultCheckListHolder(
            parent
        )
    }

    override fun getItemCount(): Int {
        return wears.size
    }

    override fun onBindViewHolder(holder: DefaultCheckListHolder, position: Int) {
        holder.bindItem(wears[position])
    }

    fun updateWears() {
        tripViewModel.wears.value = wears
    }

    inner class DefaultCheckListHolder(
        private val parent: ViewGroup,
        private val binding: DefaultChecklistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.default_checklist_item, parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(trip: WearItem) {
            binding.wearItem = trip
            binding.chbWearItem.apply {
                isChecked = wears[adapterPosition].isChecked
                setOnCheckedChangeListener { _, isChecked ->
                    wears[adapterPosition].isChecked = isChecked
                }
            }
        }
    }
}