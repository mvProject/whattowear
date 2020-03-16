package com.kinectpro.whattowear.utils

import org.junit.Assert
import org.junit.Test

class ConvertingUtilsTempTest {
    @Test
    fun convertCelsiusToFahrenheit_Proper() {
        val testCelsius = 15f
        Assert.assertEquals(59.0f, testCelsius.convertCelsiusToFahrenheit())
    }

    @Test
    fun convertCelsiusToFahrenheit_Proper_Less_Zero() {
        val testCelsius = -15f
        Assert.assertEquals(5.0f, testCelsius.convertCelsiusToFahrenheit())
    }

    @Test
    fun convertCelsiusToFahrenheit_NotProper() {
        val testCelsius = 15f
        Assert.assertNotEquals(95.0f, testCelsius.convertCelsiusToFahrenheit())
    }

    @Test
    fun convertCelsiusToFahrenheit_ifNull() {
        val testCelsius = null
        Assert.assertEquals(null, testCelsius.convertCelsiusToFahrenheit())
    }
}