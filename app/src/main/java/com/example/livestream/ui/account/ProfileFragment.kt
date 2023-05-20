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
import com.example.livestream.databinding.FragmentProfileBinding
import com.example.livestream.ui.viewmodel.observeOnLifecycle
import com.example.livestream.ui.viewmodel.viewLifecycle
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext


@AndroidEntryPoint
class ProfileFragment() : Fragment(), View.OnClickListener {

    private val accountViewModel: AccountViewModel by viewModels()
    private var binding: FragmentProfileBinding? by viewLifecycle()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false).apply {
            viewModel = accountViewModel
            lifecycleOwner = this@ProfileFragment
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeOnLifecycle {
            binding?.apply {
                logoutButton.setOnClickListener(this@ProfileFragment)
            }
            accountViewModel.logoutSuccess.launchAndCollectFlow(this) {
                findNavController().navigate(R.id.action_navigation_profile_to_navigation_login)
            }
        }
        Picasso.get().load("https://leven-tv.com/profile-pictures/" + accountViewModel.loggedUser.id + ".png").into(binding?.imageView);
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.logoutButton -> logout()
        }
    }

    private fun logout() {
        accountViewModel.logout()
    }
}