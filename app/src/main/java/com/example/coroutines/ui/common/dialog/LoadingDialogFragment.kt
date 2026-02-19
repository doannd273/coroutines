package com.example.coroutines.ui.common.dialog

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.DialogFragment
import com.example.coroutines.R

class LoadingDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_loading)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        return dialog
    }

    companion object {
        const val TAG = "LoadingDialog"
    }
}