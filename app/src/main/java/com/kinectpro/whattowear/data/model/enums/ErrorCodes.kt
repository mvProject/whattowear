package com.kinectpro.whattowear.data.model.enums

/**
 * Enum describing possible error codes returning at request to dark sky api
 */
enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1),
    UnknownHostException(-2),
    EmptyDatesException(-3),
    EmptyStartDateException(-4),
    EmptyDestinationException(-5),
    TooLongDateRangeIntervalException(-6),
    NoInternetConnectionException(-7),
    StartDateIsGreaterException(-8),
    NoForecastException(-9),
    LanguageRequestException(400),
    TargetRequestAccessException(401),
    TargetDailyUsageLimitException(403),
    TargetRequestSourceException(404)
}