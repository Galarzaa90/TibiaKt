package com.galarzaa.tibiakt.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AjaxResponse(
    @SerialName("AjaxObjects")
    val ajaxObjects: List<AjaxObject>,
)

@Serializable
internal data class AjaxObject(
    @SerialName("Data")
    val data: String,
    @SerialName("DataType")
    val dataType: String,
    @SerialName("Target")
    val target: String,
)