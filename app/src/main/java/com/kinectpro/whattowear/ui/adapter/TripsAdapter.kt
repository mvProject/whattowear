package com.kinectpro.whattowear.ui.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.trip.TripItem
import com.kinectpro.whattowear.databinding.TripListItemBinding

class TripsAdapter(var trips: List<TripItem>, private val listener: OnItemSelectedListener) :
    RecyclerView.Adapter<TripsAdapter.TripsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsViewHolder {
        return TripsViewHolder(
            parent
        )
    }

    interface OnItemSelectedListener {
        fun onMenuAction(trip: TripItem, item: MenuItem?)
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    override fun onBindViewHolder(holder: TripsViewHolder, position: Int) {
        holder.bindItem(trips[position])
    }

    inner class TripsViewHolder(
        private val parent: ViewGroup,
        private val binding: TripListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.trip_list_item, parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root), PopupMenu.OnMenuItemClickListener {

        fun bindItem(trip: TripItem) {
            binding.tripItem = trip
            // define menu and show on click
            binding.itemMenu.setOnClickListener {
                val contextMenu = PopupMenu(parent.context, it)
                contextMenu.inflate(R.menu.trip_item_menu)
                contextMenu.setOnMenuItemClickListener(this)
                contextMenu.show()
            }
        }

        // specify which item menu clicked
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            val trip: TripItem = trips[adapterPosition]
            listener.onMenuAction(trip, item)
            return false
        }
    }
}
