package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.EventEntryBuilder
import com.galarzaa.tibiakt.core.builders.EventsScheduleBuilder
import com.galarzaa.tibiakt.core.builders.eventsSchedule
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.news.EventsSchedule
import com.galarzaa.tibiakt.core.utils.cells
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.parsePopup
import com.galarzaa.tibiakt.core.utils.remove
import org.jsoup.nodes.Element
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

object EventsScheduleParser : Parser<EventsSchedule> {
    override fun fromContent(content: String): EventsSchedule {
        val boxContent = boxContent(content)
        return eventsSchedule {
            val dateBlock =
                boxContent.selectFirst("div.eventscheduleheaderdateblock")
                    ?: throw ParsingException("date block not found")
            yearMonth =
                YearMonth.parse(dateBlock.cleanText().remove("»").remove("«"), DateTimeFormatter.ofPattern("MMMM yyyy"))
            parseCalendar(boxContent, yearMonth)
        }

    }

    private fun EventsScheduleBuilder.parseCalendar(
        boxContent: Element,
        yearMonth: YearMonth,
    ) {
        var currentMonth = yearMonth
        val calendarTable = boxContent.selectFirst("#eventscheduletable")
        var onGoingDay = 1
        var firstDay = true
        val onGoingEvents: MutableList<EventEntryBuilder> = mutableListOf()
        for (cell in calendarTable.cells()) {
            val dayDiv = cell.selectFirst("div") ?: throw ParsingException("could not find day's div")
            val day = dayDiv.cleanText().toInt()
            val spans = cell.select("span.HelperDivIndicator")
            // The calendar might start with a day from the previous month
            if (onGoingDay < day)
                currentMonth = currentMonth.minusMonths(1)
            if (day < onGoingDay) {
                currentMonth = currentMonth.plusMonths(1)
            }
            onGoingDay = day + 1
            val todayEvents: MutableList<EventEntryBuilder> = mutableListOf()
            for (popup in spans) {
                val (_, popupContent) = parsePopup(popup.attr("onmouseover"))
                val divs = popupContent.select("div")
                // Multiple events might be described in the same popup, divs come in pairs, title and content
                for (index in 0..divs.lastIndex step 2) {
                    val title = divs[index].cleanText().remove(":")
                    val description = divs[index + 1].cleanText().remove("• ")
                    val event = EventEntryBuilder()
                        .title(title)
                        .description(description)
                    todayEvents.add(event)
                    // If this is not an event that was already ongoing from a previous day, add to list
                    if (!onGoingEvents.contains(event)) {
                        // Only add start date if this is not the first day of the calendar.
                        // If it's the first day, we have no way to know if the event started that day or before
                        if (!firstDay) {
                            event.startDate(LocalDate.of(currentMonth.year, currentMonth.month, day))
                        }
                        onGoingEvents.add(event)
                    }
                }
            }
            // Check which of the ongoing events did not show up today, meaning it has ended now
            for (pendingEvent in onGoingEvents.toList()) {
                if (!todayEvents.contains(pendingEvent)) {
                    //If it didn't show up today, it means it ended yesterday.
                    pendingEvent.endDate(LocalDate.of(currentMonth.year, currentMonth.month, day).minusDays(1))
                    addEntry(pendingEvent.build())
                    onGoingEvents.remove(pendingEvent)
                }
            }
            firstDay = false
        }
        // Add any leftover ongoing events without an end date, as we don't know when they end.
        onGoingEvents.forEach { addEntry(it.build()) }
    }

}