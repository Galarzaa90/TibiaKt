package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.getStaticFileUrl
import kotlinx.serialization.Serializable

@Serializable
data class DisplayMount(
    val name: String,
    val mountId: Int,
) {
    val imageUrl: String get() = getStaticFileUrl("images", "charactertrade", "mounts", "$mountId.gif")
}