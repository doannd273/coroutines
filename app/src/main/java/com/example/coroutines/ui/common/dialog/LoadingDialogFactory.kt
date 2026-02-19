package com.example.coroutines.ui.common.dialog

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object LoadingDialogFactory {

    fun show(fragment: Fragment) {
        val fm = fragment.childFragmentManager
        if (fm.findFragmentByTag(LoadingDialogFragment.TAG) == null) {
            LoadingDialogFragment()
                .show(fm, LoadingDialogFragment.TAG)
        }
    }

    fun show(activity: FragmentActivity) {
        val fm = activity.supportFragmentManager
        if (fm.findFragmentByTag(LoadingDialogFragment.TAG) == null) {
            LoadingDialogFragment()
                .show(fm, LoadingDialogFragment.TAG)
        }
    }

    fun dismiss(fragment: Fragment) {
        (fragment.childFragmentManager
            .findFragmentByTag(LoadingDialogFragment.TAG) as? DialogFragment)
            ?.dismissAllowingStateLoss()
    }

    fun dismiss(activity: FragmentActivity) {
        (activity.supportFragmentManager
            .findFragmentByTag(LoadingDialogFragment.TAG) as? DialogFragment)
            ?.dismissAllowingStateLoss()
    }
}