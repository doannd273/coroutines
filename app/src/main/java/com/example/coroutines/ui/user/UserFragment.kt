package com.example.coroutines.ui.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.coroutines.R
import com.example.coroutines.databinding.FragmentUserBinding
import com.example.coroutines.ui.common.dialog.ErrorDialogFactory
import com.example.coroutines.ui.common.dialog.LoadingDialogFactory
import dagger.hilt.android.AndroidEntryPoint
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
        initEvents()
    }

    private fun initEvents() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
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
                viewModel.users.collect { users ->
                    adapter.submitList(users)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    if (isLoading) {
                        LoadingDialogFactory.show(this@UserFragment)
                    } else {
                        LoadingDialogFactory.dismiss(this@UserFragment)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collect { message ->
                ErrorDialogFactory.show(this@UserFragment, message)
            }
        }
    }

    private fun initData() {
        viewModel.loadUsers()
    }
}