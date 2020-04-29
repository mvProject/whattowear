package com.kinectpro.whattowear.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.databinding.AddPersonalItemBinding
import com.kinectpro.whattowear.ui.viewmodel.TripInfoViewModel

class ItemAdapter(
    vm: TripInfoViewModel
) :
    RecyclerView.Adapter<ItemAdapter.AddCheckListItemViewHolder>() {

    private val viewModel = vm

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddCheckListItemViewHolder {
        return AddCheckListItemViewHolder(
            parent
        )
    }

    override fun getItemCount(): Int {
        return 1
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
            if (viewModel.editWear != null) {
                binding.addItem.apply {
                    text?.append(viewModel.editWear!!.name)
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
                viewModel.addOrEditPersonalWear(binding.addItem.text.toString())
            }
        }
    }
}