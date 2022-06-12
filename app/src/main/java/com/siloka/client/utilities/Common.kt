package com.siloka.client.utilities

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

fun showToast(
    ctx: Context,
    message: String,
    length: Int = Toast.LENGTH_SHORT,
) {
    Toast.makeText(ctx, message, length).show()
}

fun delay(
    func: () -> Unit,
    delayMillis: Long = 1000
) {
    Handler(Looper.getMainLooper())
        .postDelayed({
            func()
        }, delayMillis)
}