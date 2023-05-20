package com.example.livestream.ui.streamers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.livestream.databinding.FragmentStreamersBinding
import com.example.livestream.models.User
import com.example.livestream.ui.viewmodel.launchAndCollect
import com.example.livestream.ui.viewmodel.observeOnLifecycle
import com.example.livestream.ui.viewmodel.viewLifecycle
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StreamersFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModels()
    private var binding: FragmentStreamersBinding? by viewLifecycle()
    private val streamersAdapter = StreamersListAdapter(onStreamerClick = ::onStreamerClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStreamersBinding.inflate(inflater, container, false).apply {
            viewModel = userViewModel
            lifecycleOwner = this@StreamersFragment
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadStreams()
        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = streamersAdapter
        }
    }

    private fun loadStreams() = observeOnLifecycle {
        binding?.progressBar?.isVisible = false
        userViewModel.streamers.launchAndCollect(this) {
            streamersAdapter.update(it)
        }
        userViewModel.progressBarStatus.launchAndCollectFlow(this) {
            binding?.progressBar?.isVisible = it
        }
    }

    private fun onStreamerClick(streamer: User) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}