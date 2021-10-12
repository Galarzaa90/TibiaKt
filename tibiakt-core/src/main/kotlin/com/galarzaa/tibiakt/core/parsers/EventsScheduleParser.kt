package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.EventsScheduleBuilder
import com.galarzaa.tibiakt.core.models.EventEntry
import com.galarzaa.tibiakt.core.models.EventsSchedule
import com.galarzaa.tibiakt.core.parsePopup
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.columns
import com.galarzaa.tibiakt.core.utils.remove
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

object EventsScheduleParser : Parser<EventsSchedule> {
    override fun fromContent(content: String): EventsSchedule {
        val boxContent = boxContent(content)
        val builder = EventsScheduleBuilder()
        val dateBlock = boxContent.selectFirst("div.eventscheduleheaderdateblock")?.apply {
            builder.yearMonth(YearMonth.parse(this.cleanText(), DateTimeFormatter.ofPattern("MMMM yyyy")))
        }
        val calendarTable = boxContent.selectFirst("#eventscheduletable")
        var onGoingDay = 1
        var firstDay = true
        var month = builder.yearMonth!!.month.value
        var year = builder.yearMonth!!.year
        val ongoingEvents = mutableListOf<MutableEvent>()
        for (cell in calendarTable.columns()) {
            val dayDiv = cell.selectFirst("div")!!
            val day = dayDiv.cleanText().toInt()
            val spans = cell.select("span.HelperDivIndicator")
            if (onGoingDay < day)
                month--
            if (day < onGoingDay) {
                month++
            }
            if (month > 12) {
                month = 1
                year++
            }
            if (month < 1) {
                month = 12
                year--
            }
            onGoingDay = day + 1
            val todayEvents = mutableListOf<MutableEvent>()
            for (popup in spans) {
                val (_, popupContent) = parsePopup(popup.attr("onmouseover"))
                val divs = popupContent.select("div")
                for (index in 0..divs.lastIndex step 2) {
                    val title = divs[index].cleanText()
                    val description = divs[index + 1].cleanText()
                    val event = MutableEvent(title.remove(":"), description.remove("• "))
                    todayEvents.add(event)
                    if (ongoingEvents.contains(event)) {
                        if (!firstDay) {
                            event.startDate = LocalDate.of(year, month, day)
                        }
                        ongoingEvents.add(event)
                    }
                }
            }
            for (pendingEvent in ongoingEvents.subList(0, ongoingEvents.size)) {
                if (!todayEvents.contains(pendingEvent)) {
                    //If it didn't show up today, it means it ended yesterday.
                    pendingEvent.endDate = LocalDate.of(year, month, day).minusDays(1)
                    builder.addEntry(pendingEvent.toEventEntry())
                    ongoingEvents.remove(pendingEvent)
                }
            }
            firstDay = false
        }
        return builder.build()
    }

    private data class MutableEvent(
        var title: String,
        var description: String,
        var startDate: LocalDate? = null,
        var endDate: LocalDate? = null,
    ) {
        fun toEventEntry() = EventEntry(title, description, startDate, endDate)
    }
}