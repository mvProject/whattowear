package com.kinectpro.whattowear.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kinectpro.whattowear.data.model.trip.WeatherCondition

class WeatherConditionsAdapter(var forecast: List<WeatherCondition>) :
    RecyclerView.Adapter<WeatherConditionsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherConditionsViewHolder {

        return WeatherConditionsViewHolder(
            parent
        )
    }

    override fun getItemCount(): Int {
        return forecast.size
    }

    override fun onBindViewHolder(holder: WeatherConditionsViewHolder, position: Int) {
        holder.bindItem(forecast[position])
    }
}