package com.kinectpro.whattowear.data.wrapper

import com.kinectpro.whattowear.data.model.enums.ResourceStatus

/**
 *  Helper class for wrap data
 *
 *  @param T the type of a data
 *  @property status status of data state
 *  @property data data of specified type
 *  @property error type of a error
 */
data class ResourceWrapper<out T>(val status: ResourceStatus, val data: T?, val errorCode: Int?) {
    /**
     * @return specified instance of wrapper class according state
     */
    companion object {
        fun <T> loading(): ResourceWrapper<T> {
            return ResourceWrapper(
                ResourceStatus.LOADING,
                null,
                null
            )
        }

        fun <T> success(data: T?): ResourceWrapper<T> {
            return ResourceWrapper(
                ResourceStatus.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(error: Int, data: T?): ResourceWrapper<T> {
            return ResourceWrapper(
                ResourceStatus.ERROR,
                data,
                error
            )
        }
    }
}

