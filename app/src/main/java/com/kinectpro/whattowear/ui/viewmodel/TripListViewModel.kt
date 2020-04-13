package com.kinectpro.whattowear.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kinectpro.whattowear.data.model.TripItem

class TripListViewModel(application: Application) : AndroidViewModel(application) {
    val listTrips = listOf(
        TripItem(
            "Place1",
            1586778211000,
            1586864611000
        ),
        TripItem(
            "Place2",
            1586864611000,
            1586951011000
        ),
        TripItem(
            "Place3",
            1586951011000,
            1587037411000
        ),
        TripItem(
            "Place4",
            1587037411000,
            1587123811000
        ),
        TripItem(
            "Place5",
            1587123811000,
            1587210211000
        )
    )

}
