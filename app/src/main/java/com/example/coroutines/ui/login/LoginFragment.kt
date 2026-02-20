package com.example.coroutines.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.coroutines.R
import com.example.coroutines.databinding.FragmentLoginBinding
import com.example.coroutines.ui.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvents()
        observer()
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.loginSuccess.collect {
                        findNavController().navigate(R.id.userFragment)
                    }
                }

                launch {
                    viewModel.error.collect { message ->
                        showError(message)
                    }
                }

                launch {
                    viewModel.loading.collect { isLoading ->
                        showLoading(isLoading)
                    }
                }
            }
        }
    }

    private fun initEvents() {
        binding.btnLogin.setOnClickListener {
            handleLogin()
        }
    }

    private fun handleLogin() {
        val email = binding.etEmail.text?.toString()?.trim()
        val password = binding.etPassword.text?.toString()?.trim()

        binding.tilEmail.error = null
        binding.tilPassword.error = null

        when {
            email.isNullOrEmpty() -> {
                binding.tilEmail.error = "Email required"
            }

            password.isNullOrEmpty() -> {
                binding.tilPassword.error = "Password required"
            }

            else -> {
                performLogin(email, password)
            }
        }
    }

    private fun performLogin(email: String, password: String) {
        viewModel.login(email = email, password = password)
    }
}