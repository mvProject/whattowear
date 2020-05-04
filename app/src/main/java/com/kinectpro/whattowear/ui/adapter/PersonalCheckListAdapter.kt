package com.kinectpro.whattowear.ui.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.databinding.TripInfoPersonalChecklistItemBinding

class PersonalCheckListAdapter(
    personalWears: List<WearItem>, private val listener: OnItemSelectedListener
) :
    RecyclerView.Adapter<PersonalCheckListAdapter.PersonalCheckListViewHolder>() {

    var wears = personalWears

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalCheckListViewHolder {
        return PersonalCheckListViewHolder(
            parent
        )
    }

    interface OnItemSelectedListener {
        fun onEditAction(wear: WearItem)
        fun onDeleteAction(wear: WearItem)
    }

    override fun getItemCount(): Int {
        return wears.size
    }

    override fun onBindViewHolder(holder: PersonalCheckListViewHolder, position: Int) {
        holder.bindItem(wears[position])
    }

    inner class PersonalCheckListViewHolder(
        private val parent: ViewGroup,
        private val binding: TripInfoPersonalChecklistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.trip_info_personal_checklist_item,
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root), PopupMenu.OnMenuItemClickListener {

        fun bindItem(wear: WearItem) {
            binding.wearItem = wear

            binding.txtWearItem.setOnClickListener {
                binding.chbWearItem.isChecked = !binding.chbWearItem.isChecked
            }
            // keep item selection
            binding.chbWearItem.apply {
                isChecked = wears[layoutPosition].isChecked
                setOnCheckedChangeListener { _, isChecked ->
                    wears[layoutPosition].isChecked = isChecked
                }
            }
            // define menu and show on click
            binding.checkItemMenu.setOnClickListener {
                val contextMenu = PopupMenu(parent.context, it)
                contextMenu.inflate(R.menu.item_list_context_menu)
                contextMenu.setOnMenuItemClickListener(this)
                contextMenu.show()
            }
        }

        // specify which item menu clicked
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            val wear: WearItem = wears[layoutPosition]
            item?.let {
                when (it.itemId) {
                    R.id.trip_item_edit -> {
                        listener.onEditAction(wear)
                        notifyDataSetChanged()
                    }
                    R.id.trip_item_delete -> {
                        listener.onDeleteAction(wear)
                        notifyDataSetChanged()
                    }
                }
            }
            return false
        }
    }
}