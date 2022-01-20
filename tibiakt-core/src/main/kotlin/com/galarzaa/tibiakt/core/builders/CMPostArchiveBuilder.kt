package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.forums.CMPost
import com.galarzaa.tibiakt.core.models.forums.CMPostArchive
import java.time.LocalDate

class CMPostArchiveBuilder {
    var startDate: LocalDate? = null
    var endDate: LocalDate? = null
    var currentPage: Int? = null
    var totalPages: Int? = null
    var resultsCount: Int? = null
    val entries: MutableList<CMPost> = mutableListOf()

    fun startDate(startDate: LocalDate) = apply { this.startDate = startDate }
    fun endDate(endDate: LocalDate) = apply { this.endDate = endDate }
    fun currentPage(currentPage: Int) = apply { this.currentPage = currentPage }
    fun totalPages(totalPages: Int) = apply { this.totalPages = totalPages }
    fun resultsCount(resultsCount: Int) = apply { this.resultsCount = resultsCount }
    fun addEntry(post: CMPost) = apply { entries.add(post) }

    fun build() = CMPostArchive(
        startDate = startDate ?: throw IllegalStateException("startDate is required"),
        endDate = endDate ?: throw IllegalStateException("endDate is required"),
        currentPage = currentPage ?: throw IllegalStateException("currentPage is required"),
        totalPages = totalPages ?: throw IllegalStateException("totalPages is required"),
        resultsCount = resultsCount ?: throw IllegalStateException("resultsCount is required"),
        entries = entries
    )
}