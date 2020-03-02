package com.testapp.whattowear.data

data class ResourceWrapper<out T>(val status: Status, val data: T?, val error: Error?) {

    companion object {
        fun <T> loading(): ResourceWrapper<T> {
            return ResourceWrapper(Status.LOADING, null, null)
        }

        fun <T> success(data: T?): ResourceWrapper<T> {
            return ResourceWrapper(Status.SUCCESS, data, null)
        }

        fun <T> error(error: Error?): ResourceWrapper<T> {
            return ResourceWrapper(Status.ERROR, null, error)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}