package com.example.livestream.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.livestream.R
import com.example.livestream.databinding.FragmentLoginBinding
import com.example.livestream.ui.viewmodel.observeOnLifecycle
import com.example.livestream.ui.viewmodel.viewLifecycle
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {

    private val accountViewModel: AccountViewModel by viewModels()
    private var binding: FragmentLoginBinding? by viewLifecycle()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            viewModel = accountViewModel
            lifecycleOwner = this@LoginFragment
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeOnLifecycle {
            binding?.apply {
                loginButton.setOnClickListener(this@LoginFragment)
            }
            accountViewModel.loginSuccess.launchAndCollectFlow(this) {
                findNavController().navigate(R.id.action_navigation_login_to_navigation_profile)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.loginButton -> login()
        }
    }

    private fun login() {
        val binding = binding ?: return
        accountViewModel.login(
            binding.emailInput.text.toString(),
            binding.passwordInput.text.toString()
        )
    }
}