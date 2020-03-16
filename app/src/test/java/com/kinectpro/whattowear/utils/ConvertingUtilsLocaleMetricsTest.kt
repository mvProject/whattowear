package com.kinectpro.whattowear.utils

import org.junit.Assert
import org.junit.Test
import java.util.*

class ConvertingUtilsLocaleMetricsTest {

    @Test
    fun getProperLanguageValue_Proper_Ru() {
        Locale.setDefault(Locale("ru", "RU"))
        Assert.assertEquals("ru", convertCurrentLocaleLanguageToApiLanguageFormat())
    }

    @Test
    fun getProperLanguageValue_Proper_Ua() {
        Locale.setDefault(Locale("uk", "UA"))
        Assert.assertEquals("uk", convertCurrentLocaleLanguageToApiLanguageFormat())
    }

    @Test
    fun getProperLanguageValue_Proper_En() {
        Locale.setDefault(Locale("en", "EN"))
        Assert.assertEquals("en", convertCurrentLocaleLanguageToApiLanguageFormat())
    }

    @Test
    fun getProperLanguageValue_Proper_Other() {
        Locale.setDefault(Locale("de", "DE"))
        Assert.assertEquals("en", convertCurrentLocaleLanguageToApiLanguageFormat())
    }

    @Test
    fun getProperMetricTempValue_Proper_Round_High_Celsius() {
        val testCelsius = 36.7f
        Assert.assertEquals(37, testCelsius.getProperMetricTempValue())
    }

    @Test
    fun getProperMetricTempValue_Proper_Round_Low_Celsius() {
        val testCelsius = 36.2f
        Assert.assertEquals(36, testCelsius.getProperMetricTempValue())
    }

    @Test
    fun getProperMetricTempValue_Proper_Round_High_Fahrenheit() {
        Locale.setDefault(Locale("en", "US"))
        val testCelsius = 10.7f
        Assert.assertEquals(51, testCelsius.getProperMetricTempValue())
    }

    @Test
    fun getProperMetricTempValue_Proper_Round_Low_Fahrenheit() {
        Locale.setDefault(Locale("en", "US"))
        val testCelsius = 10.2f
        Assert.assertEquals(50, testCelsius.getProperMetricTempValue())
    }

    @Test
    fun getProperMetricTempValue_ifNullSource() {
        val testCelsius = null
        Assert.assertEquals(null, testCelsius.getProperMetricTempValue())
    }
}