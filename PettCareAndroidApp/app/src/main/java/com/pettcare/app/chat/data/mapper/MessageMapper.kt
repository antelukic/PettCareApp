package com.pettcare.app.chat.data.mapper

import com.pettcare.app.chat.domain.model.Message
import com.pettcare.app.chat.network.model.MessageApi
import java.text.DateFormat
import java.util.Date

fun MessageApi.toMessage(): Message {
    val date = Date(timestamp)
    val formattedDate = DateFormat
        .getDateInstance(DateFormat.DEFAULT)
        .format(date)
    return Message(
        text = text,
        formattedTime = formattedDate,
        username = username,
    )
}
