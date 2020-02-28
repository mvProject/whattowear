package com.testapp.whattowear.data

data class WeatherEvent<out T>(val status: Status, val data: T?, val error: Error?) {

    companion object {
        fun <T> loading(): WeatherEvent<T> {
            return WeatherEvent(Status.LOADING, null, null)
        }

        fun <T> success(data: T?): WeatherEvent<T> {
            return WeatherEvent(Status.SUCCESS, data, null)
        }

        fun <T> error(error: Error?): WeatherEvent<T> {
            return WeatherEvent(Status.ERROR, null, error)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}