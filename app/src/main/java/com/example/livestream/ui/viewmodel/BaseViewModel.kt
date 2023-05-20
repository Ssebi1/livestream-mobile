package com.example.livestream.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * The base ViewModel every ViewModel should extend. This class contains the implementation for displaying
 * alerts, toasts, snackbars and also making requests or running IO operation in background.
 */
abstract class BaseViewModel(@NonNull application: Application) : AndroidViewModel(application) {

    val progressBarStatus = SingleChannelEvent<Boolean>()

    fun executeRequest(showProgress: Boolean = true, block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (showProgress) progressBarStatus.trySend(true)
                block()
            } catch (t: Throwable) {  // the request timed out or any other errors
                Log.d("executeRequest", "error thrown", t)
                if (t is IOException) {

                } else {
                    // conversion error most probably
                }

                // Usually, unauthorised might be triggered by token refresh. This occurs in the
                // background and we don't want the user to be prompted with an error message.
                // Other times, when the user might get unauthorized, is when he enters wrong credentials.
                // We WANT to prompt the user with an error message then.
                val httpException = (t as? HttpException)
                val code = httpException?.response()?.code()

                val isLogin = httpException?.response()?.raw().toString().contains("login")
            } finally {
                if (showProgress) progressBarStatus.trySend(true)
            }
        }
    }

    fun executeIO(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                progressBarStatus.trySend(true)
                block()
            } catch (t: Throwable) {
                Log.d("executeIO", "error thrown", t)
            } finally {
                progressBarStatus.trySend(false)
            }
        }
    }
}