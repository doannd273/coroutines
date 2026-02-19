package com.example.coroutines.ui.common.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ErrorDialogFragment : DialogFragment() {

    companion object {
        private const val KEY_MESSAGE = "key_message"

        fun newInstance(message: String): ErrorDialogFragment {
            val fragment = ErrorDialogFragment()
            val bundle = Bundle()
            bundle.putString(KEY_MESSAGE, message)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val message = arguments?.getString(KEY_MESSAGE) ?: "Có lỗi xảy ra"

        return AlertDialog.Builder(requireContext())
            .setTitle("Lỗi")
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }
}