package com.kinectpro.whattowear.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.databinding.TripInfoCheckistItemBinding

class TripCheckListAdapter(var wears: List<WearItem>) :
    RecyclerView.Adapter<TripCheckListAdapter.TripCheckListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripCheckListViewHolder {
        return TripCheckListViewHolder(
            parent
        )
    }

    override fun getItemCount(): Int {
        return wears.size
    }

    override fun onBindViewHolder(holder: TripCheckListViewHolder, position: Int) {
        holder.bindItem(wears[position])
    }

    fun getTripsSelection(): List<WearItem> {
        return wears
    }

    inner class TripCheckListViewHolder(
        private val parent: ViewGroup,
        private val binding: TripInfoCheckistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.trip_info_checkist_item, parent, false
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