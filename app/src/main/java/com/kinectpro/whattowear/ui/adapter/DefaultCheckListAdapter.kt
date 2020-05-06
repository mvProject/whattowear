package com.kinectpro.whattowear.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.databinding.TripInfoCheckistItemBinding

class DefaultCheckListAdapter(defaultWears: List<WearItem>) :
    RecyclerView.Adapter<DefaultCheckListAdapter.DefaultCheckListViewHolder>() {

    val wears = defaultWears

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultCheckListViewHolder {
        return DefaultCheckListViewHolder(
            parent
        )
    }

    override fun getItemCount(): Int {
        return wears.size
    }

    override fun onBindViewHolder(holder: DefaultCheckListViewHolder, position: Int) {
        holder.bindItem(wears[position])
    }

    inner class DefaultCheckListViewHolder(
        private val parent: ViewGroup,
        private val binding: TripInfoCheckistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.trip_info_checkist_item, parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(wear: WearItem) {
            binding.wearItem = wear

            binding.txtWearItem.setOnClickListener {
                binding.chbWearItem.isChecked = !binding.chbWearItem.isChecked
            }

            binding.chbWearItem.apply {
                isChecked = wears[layoutPosition].isChecked
                setOnCheckedChangeListener { _, isChecked ->
                    wears[layoutPosition].isChecked = isChecked
                }
            }
        }
    }
}