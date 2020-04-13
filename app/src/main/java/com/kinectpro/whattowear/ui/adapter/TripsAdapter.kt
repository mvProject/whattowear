package com.kinectpro.whattowear.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.TripItem
import com.kinectpro.whattowear.databinding.TripListItemBinding

class TripsAdapter(var trips: List<TripItem>) :
    RecyclerView.Adapter<TripsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsViewHolder {

        return TripsViewHolder(
            parent
        )
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    override fun onBindViewHolder(holder: TripsViewHolder, position: Int) {
        holder.bindItem(trips[position])
    }
}

class TripsViewHolder(
    private val parent: ViewGroup,
    private val binding: TripListItemBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.context), R.layout.trip_list_item, parent, false
    )
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(trip: TripItem) {
        binding.tripItem = trip
    }
}