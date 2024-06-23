package com.pettcare.app.auth.signin.domain

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class GetDateFromMillis {

    operator fun invoke(value: Long): String {
        val date = Instant
            .ofEpochMilli(value)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        return convertMillisToLocalDateWithFormatter(date, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)).toString()
    }

    private fun convertMillisToLocalDateWithFormatter(
        date: LocalDate,
        dateTimeFormatter: DateTimeFormatter,
    ): LocalDate {
        // Convert the date to a long in millis using a dateformmater
        val dateInMillis = LocalDate.parse(date.format(dateTimeFormatter), dateTimeFormatter)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        // Convert the millis to a localDate object
        return Instant
            .ofEpochMilli(dateInMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    companion object {
        private const val DATE_TIME_FORMAT = "dd/MM/yyyy"
    }
}
