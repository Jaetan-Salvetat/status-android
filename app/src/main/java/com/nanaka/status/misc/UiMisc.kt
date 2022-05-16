package com.nanaka.status.misc

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nanaka.status.R

class UiMisc {
    companion object {

        fun getLoadingDialog(context: Context) : AlertDialog {
            return MaterialAlertDialogBuilder(context)
                .setCancelable(false)
                .setView(R.layout.circular_progress_indocator)
                .create()
        }
    }
}