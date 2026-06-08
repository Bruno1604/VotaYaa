package com.example.votaya.onboarding.signIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.votaya.core.FragmentCommunicator
import com.example.votaya.core.ResponseService
import com.example.votaya.databinding.FragmentForgotPasswordBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SignInViewModel>()
    private lateinit var communicator: FragmentCommunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        communicator = requireActivity() as FragmentCommunicator
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        observeState()
    }

    private fun setupClickListeners() {
        binding.recoverButton.setOnClickListener {
            val email = binding.emailTiet.text.toString().trim()
            if (email.isEmpty()) {
                binding.emailTil.error = "Ingresa tu correo"
                return@setOnClickListener
            }
            viewModel.resetPassword(email)
        }

        binding.backToLoginTv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.resetState.collect { state ->
                when (state) {
                    is ResponseService.Loading -> communicator.manageLoader(true)
                    is ResponseService.Success -> {
                        communicator.manageLoader(false)
                        Snackbar.make(binding.root, "Correo de recuperación enviado", Snackbar.LENGTH_LONG).show()
                        findNavController().popBackStack()
                    }
                    is ResponseService.Error -> {
                        communicator.manageLoader(false)
                        Snackbar.make(binding.root, state.error, Snackbar.LENGTH_LONG).show()
                    }
                    else -> communicator.manageLoader(false)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}