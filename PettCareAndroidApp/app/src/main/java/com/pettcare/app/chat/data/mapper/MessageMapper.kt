package com.pettcare.app.chat.data.mapper

import com.pettcare.app.chat.domain.model.Message
import com.pettcare.app.chat.network.model.MessageApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun MessageApi.toMessage(userName: String): Message {
    val inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    val dateTime = LocalDateTime.parse(date, inputFormatter)
    val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    val formattedDateTime = dateTime.format(outputFormatter)
    return Message(
        text = text,
        formattedTime = formattedDateTime,
        username = userName,
        dateTime = dateTime,
    )
}
