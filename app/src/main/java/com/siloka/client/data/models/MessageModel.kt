package com.siloka.client.data.models

/**
 * viewType guide:
 * - 0 = bot
 * - 1 = user
 * - 2 = poptop
 * - 3 = feedback
 * - 4 = lanjut CS
 * - 5 = loading
 */

data class MessageModel (
    var viewType: Int,
    var message: String?,
)

data class MessagePayload (
    var room_id: String,
    var query: String,
)



