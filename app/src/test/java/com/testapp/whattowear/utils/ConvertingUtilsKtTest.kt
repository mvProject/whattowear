package com.testapp.whattowear.utils

import com.testapp.whattowear.data.Daily
import com.testapp.whattowear.data.DarkSkyWeather
import com.testapp.whattowear.data.WeatherData
import com.testapp.whattowear.data.Data
import org.junit.Test

import org.junit.Assert.*

class ConvertingUtilsKtTest {
    private val expectedValue = WeatherData("12","12.5","12")
    private val testData = Data(12L,null,null,null,null,
                null,null,null,null,12.5,
        null,12L,null,null,null,
                     null,null,null,null,null,null,
                     null,null,null,null)
    private val testListData = mutableListOf<Data>().apply {
        add(testData)
    }
    private val testDaily = Daily("summary","icon",testListData)
    private val testValue = DarkSkyWeather(1.5,2.5,"timezone", testData,testDaily)

    @Test
    fun convertToWeatherDataModel_Test() {
        assertEquals(expectedValue,testValue.convertToWeatherDataModel())
    }
}