package com.example.livestream.ui.stream

import android.app.Application
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.livestream.models.Stream
import com.example.livestream.repository.StreamRepository
import com.example.livestream.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import javax.inject.Inject

@HiltViewModel
class StreamViewModel @Inject constructor(
    application: Application,
    private val streamRepository: StreamRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(application) {

    private val streamId = savedStateHandle.get<String>("stream_id")
    var stream = streamId?.let { streamRepository.getStream(it) }
    init {
        println(streamId)
        println(stream)
    }
}