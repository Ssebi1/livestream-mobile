package com.example.livestream.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.livestream.R
import com.example.livestream.databinding.FragmentHomeBinding
import com.example.livestream.models.Stream
import com.example.livestream.ui.viewmodel.launchAndCollect
import com.example.livestream.ui.viewmodel.observeOnLifecycle
import com.example.livestream.ui.viewmodel.viewLifecycle
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private var binding: FragmentHomeBinding? by viewLifecycle()
    private val streamsAdapter = StreamsListAdapter(onStreamClick = ::onStreamClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            viewModel = homeViewModel
            lifecycleOwner = this@HomeFragment
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadStreams()
        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = streamsAdapter
        }
    }

    private fun loadStreams() = observeOnLifecycle {
        binding?.progressBar?.isVisible = false
        homeViewModel.streams.launchAndCollect(this) {
            streamsAdapter.update(it)
        }
        homeViewModel.progressBarStatus.launchAndCollectFlow(this) {
            binding?.progressBar?.isVisible = it
        }
    }

    private fun onStreamClick(stream: Stream) {
        val bundle = Bundle()
        bundle.putString("stream_id", stream.id)
        bundle.putString("title", stream.title)
        bundle.putString("vod_recording_hls_url", stream.vod_recording_hls_url)
        findNavController().navigate(R.id.action_navigation_home_to_navigation_stream, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}