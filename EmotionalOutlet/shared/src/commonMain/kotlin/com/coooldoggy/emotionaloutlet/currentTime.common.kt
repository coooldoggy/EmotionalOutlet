package com.coooldoggy.emotionaloutlet

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun timeToString(timestampMs: Long): String {
    // Convert milliseconds to an Instant
    val instant = Instant.fromEpochMilliseconds(timestampMs)

    // Convert Instant to LocalDateTime in the system's time zone
    val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    // Format hours and minutes as "HH:mm"
    val hh = if (dateTime.hour < 10) "0${dateTime.hour}" else dateTime.hour.toString()
    val mm = if (dateTime.minute < 10) "0${dateTime.minute}" else dateTime.minute.toString()

    return "$hh:$mm"
}

expect fun timestampMs(): Long
