package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.model.wear.WearItem
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals

class DataMapperTest {

    private val defaultStrings = "test1, test1"
    private val defaultWearItems =
        listOf(WearItem("test1", true, "tripid"), WearItem("test1", false, "tripid"))
    private val emptyWearItems = listOf<WearItem>()

}