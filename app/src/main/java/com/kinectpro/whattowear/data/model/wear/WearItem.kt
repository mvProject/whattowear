package com.kinectpro.whattowear.data.model.wear

const val ITEM_TYPE_DEFAULT = "DEFAULT"
const val ITEM_TYPE_PERSONAL = "PERSONAL"

data class WearItem(
    val id: Int,
    val name: String,
    var isChecked: Boolean = false,
    var tripId: String? = null,
    val type: String = ITEM_TYPE_PERSONAL
)