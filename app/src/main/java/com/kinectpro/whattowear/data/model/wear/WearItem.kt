package com.kinectpro.whattowear.data.model.wear

data class WearItem(
    val id: Int,
    val name: String,
    var isChecked: Boolean = false,
    var tripId: String? = null,
    val isDefault: Boolean = false
)