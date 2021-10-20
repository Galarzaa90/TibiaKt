package com.galarzaa.tibiakt.core.models.bazaar

import com.galarzaa.tibiakt.core.utils.getStaticFileUrl
import kotlinx.serialization.Serializable

@Serializable
data class DisplayFamiliar(
    val name: String,
    val familiarId: Int,
) {
    val imageUrl get() = getStaticFileUrl("images", "charactertrade", "summons", "$familiarId.gif")
}