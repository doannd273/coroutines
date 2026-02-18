package com.example.coroutines.ui.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.coroutines.R
import com.example.coroutines.databinding.FragmentUserBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : Fragment(R.layout.fragment_user) {

    private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: FragmentUserBinding

    // adapter
    private val adapter by lazy {
        UserAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        observer()
        initData()
    }

    private fun initView(view: View) {
        binding = FragmentUserBinding.bind(view)

        binding.userRv.adapter = adapter

        binding.reloadBtn.setOnClickListener {
            initData()
        }
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userState.collect { userState ->
                    when (userState) {
                        is UserUiState.Success -> {
                            adapter.submitList(userState.data)
                        }

                        is UserUiState.Error -> {
                            Toast.makeText(requireContext(),
                                userState.message,
                                Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { value ->
                    showLoading(value)
                }
            }
        }
    }

    private fun showLoading(isShow: Boolean) {
        if (isShow) {
            binding.loadingLayout.visibility = View.VISIBLE
        } else {
            binding.loadingLayout.visibility = View.GONE
        }
    }

    private fun initData() {
        viewModel.loadUsers()
    }
}