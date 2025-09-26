/*
 * Copyright © 2024 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.EventsScheduleBuilder
import com.galarzaa.tibiakt.core.builders.eventEntryBuilder
import com.galarzaa.tibiakt.core.builders.eventsSchedule
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.news.EventsSchedule
import com.galarzaa.tibiakt.core.utils.cells
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.parsePopup
import com.galarzaa.tibiakt.core.utils.remove
import com.galarzaa.tibiakt.core.utils.yearMonthFormat
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth
import kotlinx.datetime.minus
import kotlinx.datetime.minusMonth
import kotlinx.datetime.plusMonth
import org.jsoup.nodes.Element

/** Parser for the events schedule. */
public object EventsScheduleParser : Parser<EventsSchedule> {
    override fun fromContent(content: String): EventsSchedule {
        val boxContent = boxContent(content)
        return eventsSchedule {
            val dateBlock = boxContent.selectFirst("div.eventscheduleheaderdateblock")
                ?: throw ParsingException("date block not found")
            yearMonth =
                YearMonth.parse(dateBlock.cleanText().remove("»").remove("«"), yearMonthFormat)
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
        var isFirstDay = true
        val onGoingEvents: MutableList<EventsScheduleBuilder.EventEntryBuilder> = mutableListOf()
        for (cell in calendarTable.cells()) {
            val dayDiv = cell.selectFirst("div") ?: throw ParsingException("could not find day's div")
            val day = dayDiv.cleanText().toInt()
            val spans = cell.select("span.HelperDivIndicator")
            // The calendar might start with a day from the previous month
            if (onGoingDay < day) currentMonth = currentMonth.minusMonth()
            if (day < onGoingDay) {
                currentMonth = currentMonth.plusMonth()
            }
            onGoingDay = day + 1
            val todayEvents: MutableList<EventsScheduleBuilder.EventEntryBuilder> = mutableListOf()
            for (popup in spans) {
                val (_, popupContent) = parsePopup(popup.attr("onmouseover"))
                val divs = popupContent.select("div")
                // Multiple events might be described in the same popup, divs come in pairs, title and content
                for (index in 0..divs.lastIndex step 2) {
                    val event = eventEntryBuilder {
                        title = divs[index].cleanText().remove(":")
                        description = divs[index + 1].cleanText().remove("• ")
                    }
                    todayEvents.add(event)
                    // If this is not an event that was already ongoing from a previous day, add to list
                    if (!onGoingEvents.contains(event)) {
                        // Only add start date if this is not the first day of the calendar.
                        // If it's the first day, we have no way to know if the event started that day or before
                        if (!isFirstDay) {
                            event.startDate = LocalDate(currentMonth.year, currentMonth.month, day)
                        }
                        onGoingEvents.add(event)
                    }
                }
            }
            // Check which of the ongoing events did not show up today, meaning it has ended now
            for (pendingEvent in onGoingEvents.toList()) {
                if (!todayEvents.contains(pendingEvent)) {
                    // If it didn't show up today, it means it ended yesterday.
                    pendingEvent.endDate = LocalDate(currentMonth.year, currentMonth.month, day).minus(DatePeriod(days = 1))
                    addEntry(pendingEvent.build())
                    onGoingEvents.remove(pendingEvent)
                }
            }
            isFirstDay = false
        }
        // Add any leftover ongoing events without an end date, as we don't know when they end.
        onGoingEvents.forEach { addEntry(it.build()) }
    }

}
