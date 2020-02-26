package com.testapp.whattowear.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.testapp.whattowear.data.TripItem
import com.testapp.whattowear.data.TripKit
import com.testapp.whattowear.data.WearItem

class LocalStoragePrefsRepository(context: Context) :
    ILocalStoragePrefs {

    private val PREFS_NAME = "WhatToWearPrefs"
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun getTripKitForRange(): List<TripKit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setTripKitForRange(tripKits: List<TripKit>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWearItemsForSelect(): List<WearItem> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setWearItemsForSelect(wearItems: List<WearItem>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTripItemsForSelect(): List<TripItem> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setTripItemsForSelect(tripItems: List<TripItem>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}