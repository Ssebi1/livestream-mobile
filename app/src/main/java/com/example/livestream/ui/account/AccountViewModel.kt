package com.example.livestream.ui.account

import android.app.Application
import com.example.livestream.models.User
import com.example.livestream.repository.UserRepository
import com.example.livestream.ui.viewmodel.BaseViewModel
import com.example.livestream.ui.viewmodel.SingleChannelEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository
) : BaseViewModel(application) {

    val loginSuccess = SingleChannelEvent<Any>()
    val logoutSuccess = SingleChannelEvent<Any>()
    var loggedUser = User()


    init {
        executeIO {
            try {
                loggedUser = userRepository.getCurrentyleLoggedUser()!!
                loginSuccess.trySend(Any())
            } catch (e: java.lang.Exception) {
                logoutSuccess.trySend(Any())
            }
        }
    }

    fun login(email: String? = null, password: String? = null) {
        executeRequest {
            val params = hashMapOf<String, Any?>(
                "email" to email,
                "password" to password,
            )
            val response = userRepository.login(params)
            println(response)
            handleLoginResponse(response)
        }
    }

    private suspend fun handleLoginResponse(user: User) {
        if (user.id.isEmpty()) {
            return
        }
        user.loggedIn = true
        userRepository.saveUser(user)
        loginSuccess.trySend(Any())
        println(user)
    }

    fun logout() {
        executeIO {
            userRepository.logoutUser()
            logoutSuccess.trySend(Any())
        }
    }
}