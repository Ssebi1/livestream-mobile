package com.example.livestream.ui.home

import android.app.Application
import android.util.Log
import androidx.core.view.isVisible
import com.example.livestream.models.Stream
import com.example.livestream.repository.StreamRepository
import com.example.livestream.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val streamRepository: StreamRepository
) : BaseViewModel(application) {

    val streams = MutableStateFlow(listOf(Stream()))

    init {
        executeIO {
            streams.tryEmit(streamRepository.getStreams())
            streamRepository.saveStreams(streams.value)
        }
    }
}