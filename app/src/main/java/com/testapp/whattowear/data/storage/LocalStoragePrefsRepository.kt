package com.testapp.whattowear.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.testapp.whattowear.data.item.model.TripItem
import com.testapp.whattowear.data.wear.model.WearItem

class LocalStoragePrefsRepository(context: Context) :
        ILocalStoragePrefs {

    private val PREFS_NAME = "WhatToWearPrefs"
    private val TRIP_ITEM_LIST_NAME = "itemListPrefs"
    private val TRIP_WEAR_LIST_NAME = "wearListPrefs"

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun getWearItemsForSelect(): List<WearItem> {
        TODO("load wears list from prefs")
    }

    override fun setWearItemsForSelect(wearItems: List<WearItem>) {
        TODO("save wears list in prefs")
    }

    override fun getTripItemsForSelect(): List<TripItem> {
        TODO("load items list from prefs")
    }

    override fun setTripItemsForSelect(tripItems: List<TripItem>) {
        TODO("save items list in prefs")
    }
}