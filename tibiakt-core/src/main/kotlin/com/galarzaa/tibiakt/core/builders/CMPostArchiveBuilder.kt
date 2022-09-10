package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.forums.CMPost
import com.galarzaa.tibiakt.core.models.forums.CMPostArchive
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.LocalDate


@BuilderDsl
public inline fun cmPostArchive(block: CMPostArchiveBuilder.() -> Unit): CMPostArchive =
    CMPostArchiveBuilder().apply(block).build()

@BuilderDsl
public inline fun cmPostArchiveBuilder(block: CMPostArchiveBuilder.() -> Unit): CMPostArchiveBuilder =
    CMPostArchiveBuilder().apply(block)

@BuilderDsl
public class CMPostArchiveBuilder : TibiaKtBuilder<CMPostArchive>() {
    public var startDate: LocalDate? = null
    public var endDate: LocalDate? = null
    public var currentPage: Int = 1
    public var totalPages: Int = 1
    public var resultsCount: Int = 0
    public val entries: MutableList<CMPost> = mutableListOf()

    public fun addEntry(post: CMPost): CMPostArchiveBuilder = apply { entries.add(post) }

    override fun build(): CMPostArchive = CMPostArchive(
        startDate = startDate ?: throw IllegalStateException("startDate is required"),
        endDate = endDate ?: throw IllegalStateException("endDate is required"),
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        entries = entries,
    )
}
