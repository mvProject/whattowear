package com.kinectpro.whattowear.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.databinding.TripInfoCheckistItemBinding

class TripCheckListAdapter(var trips: List<WearItem>) :
    RecyclerView.Adapter<TripCheckListAdapter.TripCheckListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripCheckListViewHolder {
        return TripCheckListViewHolder(
            parent
        )
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    override fun onBindViewHolder(holder: TripCheckListViewHolder, position: Int) {
        holder.bindItem(trips[position])
    }

    inner class TripCheckListViewHolder(
        private val parent: ViewGroup,
        private val binding: TripInfoCheckistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.trip_info_checkist_item, parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(trip: WearItem) {
            binding.wearItem = trip
        }
    }
}