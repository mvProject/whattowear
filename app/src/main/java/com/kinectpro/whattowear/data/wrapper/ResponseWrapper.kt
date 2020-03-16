package com.kinectpro.whattowear.data.wrapper

import com.kinectpro.whattowear.data.model.enums.ErrorCodes
import retrofit2.HttpException
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Helper class which provides returning data when request successful or error code when not
 */
open class ResponseWrapper {
    fun <T : Any> handleSuccess(data: T): ResourceWrapper<T> {
        return ResourceWrapper.success(data)
    }

    fun <T : Any> handleException(e: Exception): ResourceWrapper<T> {
        return when (e) {
            is HttpException -> ResourceWrapper.error(e.code(), null)
            is SocketTimeoutException -> ResourceWrapper.error(ErrorCodes.SocketTimeOut.code, null)
            is UnknownHostException -> ResourceWrapper.error(
                ErrorCodes.UnknownHostException.code,
                null
            )
            else -> ResourceWrapper.error(Int.MAX_VALUE, null)
        }
    }
}