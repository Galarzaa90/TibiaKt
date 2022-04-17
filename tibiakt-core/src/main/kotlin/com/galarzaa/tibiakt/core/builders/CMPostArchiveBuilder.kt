package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.forums.CMPost
import com.galarzaa.tibiakt.core.models.forums.CMPostArchive
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.LocalDate


@BuilderDsl
inline fun cmPostArchive(block: CMPostArchiveBuilder.() -> Unit) = CMPostArchiveBuilder().apply(block).build()

@BuilderDsl
inline fun cmPostArchiveBuilder(block: CMPostArchiveBuilder.() -> Unit) = CMPostArchiveBuilder().apply(block)

@BuilderDsl
class CMPostArchiveBuilder : TibiaKtBuilder<CMPostArchive>() {
    var startDate: LocalDate? = null
    var endDate: LocalDate? = null
    var currentPage: Int = 1
    var totalPages: Int = 1
    var resultsCount: Int = 0
    val entries: MutableList<CMPost> = mutableListOf()

    fun addEntry(post: CMPost) = apply { entries.add(post) }

    override fun build() = CMPostArchive(
        startDate = startDate ?: throw IllegalStateException("startDate is required"),
        endDate = endDate ?: throw IllegalStateException("endDate is required"),
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        entries = entries,
    )
}