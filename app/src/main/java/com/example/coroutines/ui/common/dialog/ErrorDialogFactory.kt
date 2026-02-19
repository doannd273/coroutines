package com.example.coroutines.ui.common.dialog

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

object ErrorDialogFactory {

    private const val TAG = "ErrorDialog"

    fun show(fragment: Fragment, message: String) {
        val manager = fragment.childFragmentManager

        // Tránh show trùng
        if (manager.findFragmentByTag(TAG) != null) return

        val dialog = ErrorDialogFragment.newInstance(message)
        dialog.show(manager, TAG)
    }

    fun dismiss(fragment: Fragment) {
        val manager = fragment.childFragmentManager
        val dialog = manager.findFragmentByTag(TAG) as? DialogFragment
        dialog?.dismiss()
    }
}