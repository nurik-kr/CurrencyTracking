package com.example.currencytracking.utils

import android.app.AlertDialog
import android.content.Context
import com.example.currencytracking.R
import com.example.currencytracking.data.constants.Messages
import com.example.currencytracking.data.constants.Messages.Companion.CHECK_CONNECTION
import com.example.currencytracking.data.constants.Messages.Companion.CONTACT_SERVICE
import com.example.currencytracking.data.constants.Messages.Companion.EMPTY_STRING
import java.net.UnknownHostException

class ErrorHandler {

    companion object {

        fun getMessageByThrowable(cause: Throwable): String {
            val message = if (cause is UnknownHostException) {
                CHECK_CONNECTION
            } else {
                EMPTY_STRING
            }
            return message
        }

        fun showAlertDialog(
            context: Context,
            message: String,
            onPositiveButtonClicked: () -> Unit,
        ) {
            val title = message.ifBlank {
                Messages.SOMETHING_WRONG
            }

            val subtitle = if (message.isNotBlank()) {
                EMPTY_STRING
            } else {
                CONTACT_SERVICE
            }

            AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(subtitle)
                .setPositiveButton(R.string.retry) { _, _ ->
                    onPositiveButtonClicked.invoke()
                }
                .setCancelable(false)
                .show()
        }

    }
}