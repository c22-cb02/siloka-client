package com.siloka.client.data.models

/**
 * viewType guide:
 * - 0 = Bot
 * - 1 = User
 * - 2 = Shortcut Message/Popular Topics
 * - 3 = Feedback
 * - 4 = Continue to CS Prompt
 * - 5 = Loading
 */

data class MessageModel (
    var viewType: Int,
    var message: String?,
)
