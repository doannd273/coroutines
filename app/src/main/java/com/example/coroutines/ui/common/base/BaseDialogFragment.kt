package com.example.coroutines.ui.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.example.coroutines.ui.common.dialog.ErrorDialogFactory
import com.example.coroutines.ui.common.dialog.LoadingDialogFactory

abstract class BaseDialogFragment<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : DialogFragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    /* ===============================
       Dialog size config (0f -> 1f)
       =============================== */

    open val widthPercent: Float? = null   // ví dụ: 0.9f
    open val heightPercent: Float? = null  // ví dụ: 0.5f

    open val cancelableOnTouchOutside: Boolean = true

    /* ===============================
       Lifecycle
       =============================== */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if (!isAdded) return

        dialog?.window?.let { window ->

            val displayMetrics = resources.displayMetrics

            val width = widthPercent
                ?.takeIf { it in 0f..1f }
                ?.let { (displayMetrics.widthPixels * it).toInt() }
                ?: ViewGroup.LayoutParams.WRAP_CONTENT

            val height = heightPercent
                ?.takeIf { it in 0f..1f }
                ?.let { (displayMetrics.heightPixels * it).toInt() }
                ?: ViewGroup.LayoutParams.WRAP_CONTENT

            window.setLayout(width, height)
        }

        dialog?.setCanceledOnTouchOutside(cancelableOnTouchOutside)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            LoadingDialogFactory.show(this)
        } else {
            LoadingDialogFactory.dismiss(this)
        }
    }

    open fun showError(message: String) {
        ErrorDialogFactory.show(this, message)
    }
}
