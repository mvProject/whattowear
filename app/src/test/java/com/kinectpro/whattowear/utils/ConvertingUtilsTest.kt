package com.kinectpro.whattowear.utils

import android.graphics.Color
import android.text.SpannableString
import com.kinectpro.whattowear.data.model.response.Daily
import com.kinectpro.whattowear.data.model.response.DarkSkyWeather
import com.kinectpro.whattowear.data.model.response.WeatherData
import com.kinectpro.whattowear.data.model.response.Data
import com.kinectpro.whattowear.data.model.trip.TempSummary
import org.junit.Test
import org.junit.Assert.*

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

    private fun getTestDateList(): List<Long> {
        return listOf(1583704800, 1583791200, 1583877600, 1583964000, 1584050400)
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
            null, null, state, time, null,
            null, null, null, null, tempHigh,
            tempLow, null, null, null, null,
            null, null, null, null, null, null,
            null, null, null, null, null
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
            "summary",
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
            "timezone",
            getTestData(time, tempHigh, tempLow, state),
            getTestDaily(time, tempHigh, tempLow, state)
        )
    }

    @Test
    fun convertToWeatherDataModel_Test() {
        assertEquals(
            getExpectedValue(1583704800, 12.5f, 12f, "rain"),
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
            getExpectedValue(1583704800, -12.5f, -12f, "rain"),
            getTestValue(1583704800, -12.5f, -12f, "rain").convertToWeatherDataModel()
        )
    }

    @Test
    fun convertToWeatherDataModel_Test_HighTime_Is_Null() {
        assertEquals(null, getTestValue(1583704800, 12.5f, null, "rain").convertToWeatherDataModel())
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
            getExpectedValue(1583704800, 12.5f, -12f, null),
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
    fun convertToShortDateFormatString() {
        assertEquals(
            "09.03 10.03 11.03 12.03 13.03 ",
            getTestDateList().convertToShortDateFormatString()
        )
    }

    @Test
    fun convertToReadableRange_Proper() {
        assertEquals(
            "  -3.0 .. -1.0",
            TempSummary(-3f, -1f).convertToReadableRange()
        )
    }

    @Test
    fun roundedBackgroundSpannable_Proper_Single_Date() {
        val expectedSingleSpan = SpannableString("15.03 ")
        assertEquals(
            expectedSingleSpan.toString(),
            getDatesListAsRoundedBackgroundSpannable("15.03 ", Color.RED, Color.WHITE).toString()
        )
    }

    @Test
    fun roundedBackgroundSpannable_Proper_Multi_Date() {
        val expectedSingleSpan = SpannableString("15.03 16.03 17.03 ")
        assertEquals(
            expectedSingleSpan.toString(),
            getDatesListAsRoundedBackgroundSpannable("15.03 16.03 17.03 ", Color.RED, Color.WHITE).toString()
        )
    }

    @Test
    fun roundedBackgroundSpannable_Proper_Null_String() {
        assertEquals(null, getDatesListAsRoundedBackgroundSpannable(null, Color.RED, Color.WHITE))
    }
}