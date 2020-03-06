package com.kinectpro.whattowear.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.trip.WeatherCondition
import com.kinectpro.whattowear.databinding.ItemStateSummaryBinding

class WeatherConditionsViewHolder(
    private val parent: ViewGroup,
    private val binding: ItemStateSummaryBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.context), R.layout.item_state_summary, parent, false
    )
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(weather: WeatherCondition) {
        binding.weatherState = weather
    }
}