package com.siloka.client.utilities

import android.content.Context
import android.widget.Toast

fun showToast(
    ctx: Context,
    message: String,
    length: Int = Toast.LENGTH_SHORT,
) {
    Toast.makeText(ctx, message, length).show()
}