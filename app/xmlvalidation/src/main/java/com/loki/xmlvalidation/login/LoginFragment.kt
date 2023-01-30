package com.loki.xmlvalidation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.loki.xmlvalidation.R
import com.loki.xmlvalidation.databinding.FragmentLoginBinding
import com.loki.xmlvalidation.util.hideKeyboard
import com.loki.xmlvalidation.util.navigateSafely
import com.loki.xmlvalidation.util.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.apply {

            viewModel.apply {

                etUsername.doOnTextChanged { text, _, _, _ ->
                    if (text?.isNotEmpty()!!) {
                        onEvent(LoginViewModel.LoginFormEvent.UsernameChanged(text.toString()))
                        loginState.value.usernameError = null
                    }
                    else {
                        loginState.value.username = ""
                    }
                }

                etEmail.doOnTextChanged { text, _, _, _ ->

                    if (text?.isNotEmpty()!!){
                        onEvent(LoginViewModel.LoginFormEvent.EmailChanged(text.toString()))
                        loginState.value.emailError = null
                    }
                    else {
                        loginState.value.email = ""
                    }
                }

                etPassword.doOnTextChanged { text, _, _, _ ->

                    if (text?.isNotEmpty()!!){
                        onEvent(LoginViewModel.LoginFormEvent.PasswordChanged(text.toString()))
                        loginState.value.passwordError = null
                    }
                    else{
                        loginState.value.password = ""
                    }
                }

                loginBtn.setOnClickListener {
                    onEvent(LoginViewModel.LoginFormEvent.Submit)
                    root.hideKeyboard()
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.loginState.collectLatest { state ->
                binding.apply {
                    lUsername.helperText = state.usernameError
                    lEmail.helperText = state.emailError
                    lPassword.helperText = state.passwordError
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.loginEvent.collectLatest { event ->

                when(event) {

                    is LoginViewModel.LoginEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is LoginViewModel.LoginEvent.Success -> {
                        binding.progressBar.isVisible = false

                        showToast(event.message)

                        findNavController().navigateSafely(R.id.action_loginFragment_to_homeFragment)
                    }

                    is LoginViewModel.LoginEvent.Error -> {}
                }
            }
        }
    }
}