package com.kinectpro.whattowear.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.databinding.DefaultChecklistItemBinding

class DefaultCheckListAdapter(var wears: List<WearItem>) :
    RecyclerView.Adapter<DefaultCheckListAdapter.DefaultCheckListHolder>() {

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

    fun getWearsList(): List<WearItem> {
        return wears
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