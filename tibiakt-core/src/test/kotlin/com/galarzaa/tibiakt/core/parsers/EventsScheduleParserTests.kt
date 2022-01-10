package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class EventsScheduleParserTests : StringSpec({
    "Parse event schedule"{
        val eventSchedule = EventsScheduleParser.fromContent(getResource("events/eventCalendar.txt"))
        eventSchedule.yearMonth.year shouldBe 2021
        eventSchedule.entries shouldHaveSize 8
    }
})