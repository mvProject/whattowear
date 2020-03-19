package com.kinectpro.whattowear.data.model.enums

/**
 * Enum describing possible error codes returning at request to dark sky api
 */
enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1),
    UnknownHostException(-2),
    EmptyDatesException(-3),
    EmptyDestinationException(-5),
    TooLongDateRangeIntervalException(-4),
    InvalidDatesRangeException(-5),
    NoInternetConnectionException(-6),
    LanguageRequestException(400),
    TargetRequestAccessException(403),
    TargetRequestSourceException(404)
}