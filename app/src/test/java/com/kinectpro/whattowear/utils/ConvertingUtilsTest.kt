package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.model.response.Daily
import com.kinectpro.whattowear.data.model.response.DarkSkyWeather
import com.kinectpro.whattowear.data.model.response.Data
import com.kinectpro.whattowear.data.model.response.WeatherData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ConvertingUtilsTest {

    private val pattern = "dd/MM/yy"

    private fun getExpectedWeatherList(): List<WeatherData> {
        return listOf(
            WeatherData(
                1583704800,
                11f,
                3f,
                "rain"
            ),
            WeatherData(
                1583791200,
                10.5f,
                2.5f,
                "wind"
            ),
            WeatherData(
                1583877600,
                10f,
                2f,
                "clear-day"
            ),
            WeatherData(
                1583964000,
                9f,
                1f,
                null
            ),
            WeatherData(
                1584050400,
                8f,
                3f,
                "wind"
            )
        )
    }

    private fun getExpectedDayTempList(): List<Float> {
        return listOf(11f, 10.5f, 10f, 9f, 8f)
    }

    private fun getExpectedNightTempList(): List<Float> {
        return listOf(3f, 2.5f, 2f, 1f, 3f)
    }

    private fun getExpectedStatesTempList(): List<String> {
        return listOf("rain", "wind", "defaultWeatherState")
    }


    private fun getExpectedValue(
        time: Long,
        tempHigh: Float,
        tempLow: Float,
        state: String?
    ): WeatherData {
        return WeatherData(
            time,
            tempHigh,
            tempLow,
            state
        )
    }

    private fun getTestData(time: Long?, tempHigh: Float?, tempLow: Float?, state: String?): Data {
        return Data(
            state, time, tempHigh, tempLow
        )
    }

    private fun getTestListData(
        time: Long?,
        tempHigh: Float?,
        tempLow: Float?,
        state: String?
    ): MutableList<Data> {
        return mutableListOf<Data>().apply {
            add(getTestData(time, tempHigh, tempLow, state))
        }
    }

    private fun getTestDaily(
        time: Long?,
        tempHigh: Float?,
        tempLow: Float?,
        state: String?
    ): Daily {
        return Daily(
            "icon",
            getTestListData(time, tempHigh, tempLow, state)
        )
    }

    private fun getTestValue(
        time: Long?,
        tempHigh: Float?,
        tempLow: Float?,
        state: String?
    ): DarkSkyWeather {
        return DarkSkyWeather(
            1.5,
            2.5,
            getTestDaily(time, tempHigh, tempLow, state), 3.5f
        )
    }

    @Test
    fun convertToWeatherDataModel_Test() {
        assertEquals(
            getExpectedValue(1583715600, 12.5f, 12f, "rain"),
            getTestValue(1583704800, 12.5f, 12f, "rain").convertToWeatherDataModel()
        )
    }

    @Test
    fun convertToWeatherDataModel_Test_Not_Null() {
        assertNotEquals(null, getTestValue(12L, 12.5f, 12f, "rain").convertToWeatherDataModel())
    }

    @Test
    fun convertToWeatherDataModel_Test_Weather_Sub_Zero() {
        assertEquals(
            getExpectedValue(1583715600, -12.5f, -12f, "rain"),
            getTestValue(1583704800, -12.5f, -12f, "rain").convertToWeatherDataModel()
        )
    }

    @Test
    fun convertToWeatherDataModel_Test_HighTime_Is_Null() {
        assertEquals(
            null,
            getTestValue(1583704800, 12.5f, null, "rain").convertToWeatherDataModel()
        )
    }

    @Test
    fun convertToWeatherDataModel_Test_Temp_Null() {
        assertEquals(null, getTestValue(1583704800, null, 12f, "rain").convertToWeatherDataModel())
    }

    @Test
    fun convertToWeatherDataModel_Test_Time_Null() {
        assertEquals(null, getTestValue(null, 12.5f, 12f, "rain").convertToWeatherDataModel())
    }

    @Test
    fun convertToWeatherDataModel_Test_State_Null() {
        assertEquals(
            getExpectedValue(1583715600, 12.5f, -12f, null),
            getTestValue(1583704800, 12.5f, -12f, null).convertToWeatherDataModel()
        )
    }

    @Test
    fun getDateToReadableFormat_Test_ZeroDate() {
        val longZero = 0L
        val expectedDateSmall = "01/01/70"
        assertEquals(expectedDateSmall, longZero.convertDateToReadableFormat(pattern))
    }

    @Test
    fun getDateToReadableFormat_Test_NullDate() {
        val longNull = null
        assertEquals(null, longNull?.convertDateToReadableFormat(pattern))
    }

    @Test
    fun getDateToReadableFormat_Test_SmallDate() {
        val expectedDateSmall = "01/01/70"
        val longSmallValue = 123L
        assertEquals(expectedDateSmall, longSmallValue.convertDateToReadableFormat(pattern))
    }

    @Test
    fun getDateToReadableFormat_Test_ProperDate() {
        val expectedDate = "19/02/20"
        val longValue = 1582114347000L
        assertEquals(expectedDate, longValue.convertDateToReadableFormat(pattern))
    }

    @Test
    fun getTemperatureAsList_DayTemp() {
        assertEquals(getExpectedDayTempList(), getExpectedWeatherList().getDayTemperatureAsList())
    }

    @Test
    fun getTemperatureAsList_NightTemp() {
        assertEquals(
            getExpectedNightTempList(),
            getExpectedWeatherList().getNightTemperatureAsList()
        )
    }

    @Test
    fun getWeatherUniqueStatesAsList_PROPER() {
        assertEquals(
            getExpectedStatesTempList(),
            getExpectedWeatherList().getWeatherStatesUniqueAsList()
        )
    }

    @Test
    fun isDateConvertible_ProperLong() {
        val testLong = 12345L
        assertTrue(isDateConvertible(testLong))
    }

    @Test
    fun isDateConvertible_ZeroDate() {
        val testZero = 0L
        assertFalse(isDateConvertible(testZero))
    }

    @Test
    fun isDateConvertible_NullDate() {
        val testNull = null
        assertFalse(isDateConvertible(testNull))
    }
}