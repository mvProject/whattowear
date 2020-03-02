package com.testapp.whattowear.data.trip

import com.testapp.whattowear.data.ITripKitItem

interface ITripRepository {
    fun getTripNeededThings(): List<ITripKitItem>
}