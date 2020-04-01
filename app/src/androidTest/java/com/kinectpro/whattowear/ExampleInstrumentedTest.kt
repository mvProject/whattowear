package com.kinectpro.whattowear

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kinectpro.whattowear.data.model.trip.TempSummary
import com.kinectpro.whattowear.utils.convertToReadableRange
import com.kinectpro.whattowear.utils.getProperMetricTempValue
import com.kinectpro.whattowear.utils.getProperMetricValue

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import kotlin.math.roundToInt

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun useAppContext() {
        // Context of the app under test.
        assertEquals("com.kinectpro.whattowear", appContext.packageName)
    }

    @Test
    fun convertToReadableRange_Test() {
        val someOtherString = "Some string"
        val currentMetricFormat = getProperMetricValue()
        val result = String.format(
            appContext.getString(R.string.temperature_value_description),
            15f.getProperMetricTempValue(),
            currentMetricFormat,
            5f.getProperMetricTempValue(),
            currentMetricFormat
        )
        val tempSummary = TempSummary(5f, 15f)
        assertNotNull(tempSummary.convertToReadableRange(appContext))
        assertEquals(result, tempSummary.convertToReadableRange(appContext))
        assertNotEquals(someOtherString, tempSummary.convertToReadableRange(appContext))
    }
}
