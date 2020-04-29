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
import com.kinectpro.whattowear.ui.viewmodel.TripInfoViewModel
import com.kinectpro.whattowear.utils.filteredType

class PersonalCheckListAdapter(
    vm: TripInfoViewModel
) :
    RecyclerView.Adapter<PersonalCheckListAdapter.PersonalCheckListViewHolder>() {

    var wears: List<WearItem> = vm.tripDetail.value?.wears?.filteredType(false)!!
    private val viewModel = vm

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalCheckListViewHolder {
        return PersonalCheckListViewHolder(
            parent
        )
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
                binding.chbWearItem.isChecked = binding.chbWearItem.isChecked != true
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
            when (item!!.itemId) {
                R.id.trip_item_edit -> {
                    viewModel.editWear = wear
                    notifyDataSetChanged()
                }
                R.id.trip_item_delete -> {
                    viewModel.deleteSelectedWearFromDb(wear)
                    notifyDataSetChanged()
                }
            }
            return false
        }
    }
}