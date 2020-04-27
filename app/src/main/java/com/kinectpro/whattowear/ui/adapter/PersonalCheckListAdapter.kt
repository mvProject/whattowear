package com.kinectpro.whattowear.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.databinding.AddPersonalItemBinding
import com.kinectpro.whattowear.databinding.TripInfoPersonalChecklistItemBinding
import java.util.*

class PersonalCheckListAdapter(
    var wears: List<WearItem>,
    private val listener: OnItemSelectedListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_ADD_OR_EDIT = 1
    private var editableWear: WearItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_ADD_OR_EDIT)
            return AddCheckListItemViewHolder(
                parent
            )
        return PersonalCheckListViewHolder(
            parent
        )
    }

    interface OnItemSelectedListener {
        fun onMenuAction(wear: WearItem, item: MenuItem?)
        fun onItemAction(wear: WearItem, isEditMode: Boolean)
    }

    override fun getItemCount(): Int {
        if (wears.isEmpty()) return ITEM_ADD_OR_EDIT
        return wears.size + ITEM_ADD_OR_EDIT
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM_ADD_OR_EDIT) {
            (holder as AddCheckListItemViewHolder).bindItem()
        } else {
            (holder as PersonalCheckListViewHolder).bindItem(wears[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == wears.size)
            return ITEM_ADD_OR_EDIT
        return super.getItemViewType(position)
    }

    fun getCurrentWears(): List<WearItem> {
        return wears
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
                isChecked = wears[adapterPosition].isChecked
                setOnCheckedChangeListener { _, isChecked ->
                    wears[adapterPosition].isChecked = isChecked
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
            val wear: WearItem = wears[adapterPosition]
            if (item!!.itemId == R.id.trip_item_edit) {
                editableWear = wear
                notifyDataSetChanged()
            }
            listener.onMenuAction(wear, item)
            return false
        }
    }

    inner class AddCheckListItemViewHolder(
        private val parent: ViewGroup,
        private val binding: AddPersonalItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.add_personal_item, parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem() {
            if (editableWear != null) {
                binding.addItem.apply {
                    text?.append(editableWear!!.name)
                    requestFocus()
                }
            }

            binding.addItem.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    p0.let {
                        binding.btnAdd.isEnabled = it!!.length >= 2
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            })

            binding.btnAdd.setOnClickListener {
                // edit current personal item
                if (editableWear != null) {
                    val newWear = editableWear!!.copy(name = binding.addItem.text.toString())
                    listener.onItemAction(newWear, true)
                }
                // add new personal item
                else {
                    listener.onItemAction(
                        WearItem(
                            Random().nextInt(),
                            binding.addItem.text.toString()
                        ), false
                    )
                }
            }
        }
    }
}