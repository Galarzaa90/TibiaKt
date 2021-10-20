package com.galarzaa.tibiakt.client.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AjaxResponse(
    @SerialName("AjaxObjects")
    val ajaxObjects: List<AjaxObject>,
)

@Serializable
data class AjaxObject(
    @SerialName("Data")
    val data: String,
    @SerialName("DataType")
    val dataType: String,
    @SerialName("Target")
    val target: String,
)