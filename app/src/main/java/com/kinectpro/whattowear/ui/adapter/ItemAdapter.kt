package com.kinectpro.whattowear.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.databinding.AddPersonalItemBinding
import java.util.*

class ItemAdapter(
    private val listener: OnAddItemSelectedListener
) :
    RecyclerView.Adapter<ItemAdapter.AddCheckListItemViewHolder>() {

    private val MIN_NAME_LENGTH = 2
    private val ITEM_COUNT = 1

    private var focusListener: View.OnFocusChangeListener? = null
    private var wearItemForEdit: WearItem? = null

    interface OnAddItemSelectedListener {
        fun onAddAction(wear: WearItem, isNewItem: Boolean)
    }

    fun setWearForEdit(item: WearItem) {
        wearItemForEdit = item
    }

    fun setViewFocusListener(focusListener: View.OnFocusChangeListener) {
        this.focusListener = focusListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddCheckListItemViewHolder {
        return AddCheckListItemViewHolder(
            parent
        )
    }

    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    override fun onBindViewHolder(holder: AddCheckListItemViewHolder, position: Int) {
        holder.bindItem()
    }

    inner class AddCheckListItemViewHolder(
        private val parent: ViewGroup,
        private val binding: AddPersonalItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.add_personal_item, parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem() {
            wearItemForEdit?.let {
                binding.addItem.apply {
                    append(it.name)
                    requestFocus()
                }
            }

            focusListener?.let {
                binding.addItem.onFocusChangeListener = it
            }

            binding.addItem.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(currentText: Editable?) {
                    currentText?.let {
                        binding.btnAdd.isEnabled = it.length >= MIN_NAME_LENGTH
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            })

            binding.btnAdd.setOnClickListener {
                when (wearItemForEdit) {
                    null -> {
                        listener.onAddAction(
                            WearItem(
                                Random().nextInt(),
                                binding.addItem.text.toString(),
                                false,
                                null
                            ), true
                        )
                    }
                    else -> {
                        wearItemForEdit?.let {
                            val wearItem = it.copy(name = binding.addItem.text.toString())
                            listener.onAddAction(wearItem, false)
                        }
                        wearItemForEdit = null
                    }
                }
            }
        }
    }
}