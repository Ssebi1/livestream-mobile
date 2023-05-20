package com.example.livestream.ui.streamers

import android.app.Application
import com.example.livestream.models.User
import com.example.livestream.repository.UserRepository
import com.example.livestream.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository
) : BaseViewModel(application) {

    val streamers = MutableStateFlow(listOf(User()))

    init {
        executeIO {
            streamers.tryEmit(userRepository.getStreamers())
        }
    }
}