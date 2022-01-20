package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.forums.CMPost
import java.time.LocalDate

class CMPostArchiveBuilder {
    var startDate: LocalDate? = null
    var endDate: LocalDate? = null
    var currentPage: Int? = null
    var totalPages: Int? = null
    var resultsCount: Int? = null
    val entries: MutableList<CMPost> = mutableListOf()

    fun addEntry(post: CMPost) = apply { entries.add(post) }
}