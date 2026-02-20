package com.example.coroutines.ui.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.coroutines.databinding.FragmentUserBinding
import com.example.coroutines.ui.common.base.BaseFragment
import com.example.coroutines.ui.common.dialog.ErrorDialogFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val viewModel: UserViewModel by viewModels()

    // adapter
    private val adapter by lazy {
        UserAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observer()
        initData()
        initEvents()
    }

    private fun initEvents() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initView() {
        binding.userRv.adapter = adapter

        binding.reloadBtn.setOnClickListener {
            initData()
        }
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.users.collect { users ->
                        adapter.submitList(users)
                    }
                }

                launch {
                    viewModel.loading.collect { isLoading ->
                        showLoading(isLoading)
                    }
                }

                launch {
                    viewModel.error.collect { message ->
                        ErrorDialogFactory.show(this@UserFragment, message)
                    }
                }
            }
        }
    }

    private fun initData() {
        viewModel.loadUsers()
    }
}