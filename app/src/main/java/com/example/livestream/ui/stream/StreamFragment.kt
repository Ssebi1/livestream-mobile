package com.example.livestream.ui.stream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.livestream.databinding.FragmentStreamBinding
import com.example.livestream.ui.viewmodel.launchAndCollect
import com.example.livestream.ui.viewmodel.observeOnLifecycle
import com.example.livestream.ui.viewmodel.viewLifecycle
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StreamFragment : Fragment() {

    private val streamViewModel: StreamViewModel by viewModels()
    private var binding: FragmentStreamBinding? by viewLifecycle()

    private var exoPlayer: ExoPlayer? = null
    private var playbackPosition = 0L
    private var playWhenReady = true
    var stream_id: String = ""
    var stream_title: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStreamBinding.inflate(inflater, container, false).apply {
            viewModel = streamViewModel
            lifecycleOwner = this@StreamFragment
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        stream_id = arguments?.getString("stream_id") ?: ""
        Picasso.get().load("https://leven-tv.com/profile-pictures/" + streamViewModel.stream?.user?.id + ".png").into(binding?.imageView);
        preparePlayer(arguments?.getString("vod_recording_hls_url") ?: "none")
    }

    private fun preparePlayer(uri: String) {
        exoPlayer = this.context?.let { ExoPlayer.Builder(it).build() }
        exoPlayer?.playWhenReady = true
        binding?.playerView?.player = exoPlayer
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaItem = MediaItem.fromUri(uri)
        val mediaSource =
            HlsMediaSource.Factory(defaultHttpDataSourceFactory).createMediaSource(mediaItem)
        exoPlayer?.apply {
            setMediaSource(mediaSource)
            seekTo(playbackPosition)
            playWhenReady = playWhenReady
            prepare()
        }
    }

    private fun releasePlayer() {
        exoPlayer?.let { player ->
            playbackPosition = player.currentPosition
            playWhenReady = player.playWhenReady
            player.release()
            exoPlayer = null
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}