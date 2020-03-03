package com.kinectpro.whattowear.data

/**
 *  Helper class for wrap data
 *
 *  @param T the type of a data
 *  @property status status of data state
 *  @property data data of specified type
 *  @property error type of a error
 */
data class ResourceWrapper<out T>(val status: Status, val data: T?, val error: Error?) {
    /**
     * @return specified instance of wrapper class according state
     */
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

/**
 * enum for data response state updating
 */
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}