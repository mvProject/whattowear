package com.testapp.whattowear.utils

import com.testapp.whattowear.data.Daily
import com.testapp.whattowear.data.DarkSkyWeather
import com.testapp.whattowear.data.WeatherData
import com.testapp.whattowear.data.Data
import org.junit.Test
import org.junit.Assert.*

class ConvertingUtilsKtTest {
    private fun getExpectedValue(
        time: String,
        tempHigh: String,
        tempHighTime: String
    ): WeatherData {
        return WeatherData(time, tempHigh, tempHighTime)
    }

    private fun getTestData(time: Long?, tempHigh: Double?, tempHighTime: Long?): Data {
        return Data(
            time, null, null, null, null,
            null, null, null, null, tempHigh,
            null, tempHighTime, null, null, null,
            null, null, null, null, null, null,
            null, null, null, null
        )
    }

    private fun getTestListData(
        time: Long?,
        tempHigh: Double?,
        tempHighTime: Long?
    ): MutableList<Data> {
        return mutableListOf<Data>().apply {
            add(getTestData(time, tempHigh, tempHighTime))
        }
    }

    private fun getTestDaily(time: Long?, tempHigh: Double?, tempHighTime: Long?): Daily {
        return Daily("summary", "icon", getTestListData(time, tempHigh, tempHighTime))
    }

    private fun getTestValue(time: Long?, tempHigh: Double?, tempHighTime: Long?): DarkSkyWeather {
        return DarkSkyWeather(
            1.5,
            2.5,
            "timezone",
            getTestData(time, tempHigh, tempHighTime),
            getTestDaily(time, tempHigh, tempHighTime)
        )
    }

    @Test
    fun convertToWeatherDataModel_Test() {
        assertEquals(
            getExpectedValue("12", "12.5", "12"),
            getTestValue(12L, 12.5, 12L).convertToWeatherDataModel()
        )
    }

    @Test
    fun convertToWeatherDataModel_Test_Not_Null() {
        assertNotEquals(null, getTestValue(12L, 12.5, 12L).convertToWeatherDataModel())
    }

    @Test
    fun convertToWeatherDataModel_Test_Weather_Sub_Zero() {
        assertEquals(
            getExpectedValue("12", "-12.5", "12"),
            getTestValue(12L, -12.5, 12L).convertToWeatherDataModel()
        )
    }

    @Test
    fun convertToWeatherDataModel_Test_HighTime_Is_Null() {
        assertEquals(null, getTestValue(12L, 12.5, null).convertToWeatherDataModel())
    }

    @Test
    fun convertToWeatherDataModel_Test_Temp_Null() {
        assertEquals(null, getTestValue(12L, null, 12L).convertToWeatherDataModel())
    }

    @Test
    fun convertToWeatherDataModel_Test_Time_Null() {
        assertEquals(null, getTestValue(null, 12.5, 12L).convertToWeatherDataModel())
    }

    @Test
    fun getDateToReadableFormat_Test_ZeroDate() {
        val longZero = 0L

        assertEquals(null, longZero.getDateToReadableFormat())
    }

    @Test
    fun getDateToReadableFormat_Test_NullDate() {
        val longNull = null
        assertEquals(null, longNull?.getDateToReadableFormat())
    }

    @Test
    fun getDateToReadableFormat_Test_SmallDate() {
        val expectedDateSmall = "01/01/70"
        val longSmallValue = 123L

        assertEquals(expectedDateSmall, longSmallValue.getDateToReadableFormat())
    }

    @Test
    fun getDateToReadableFormat_Test_ProperDate() {
        val expectedDate = "19/02/20"
        val longValue = 1582114347000L

        assertEquals(expectedDate, longValue.getDateToReadableFormat())
    }
}