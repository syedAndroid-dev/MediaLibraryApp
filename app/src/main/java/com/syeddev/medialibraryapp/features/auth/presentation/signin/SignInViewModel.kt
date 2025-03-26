package com.syeddev.medialibraryapp.features.auth.presentation.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syeddev.medialibraryapp.core.apiutils.Resource
import com.syeddev.medialibraryapp.features.auth.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    val authRepository: AuthRepository
): ViewModel(){

    private val _signInState = MutableStateFlow(SignInUiState())
    val signInState = _signInState.asStateFlow()

    private val _event: Channel<SignInUiEvents> = Channel()
    val event = _event.receiveAsFlow()

    fun onEvent(signInUiEvents: SignInUiEvents){
        when(signInUiEvents){
            SignInUiEvents.ButtonClick.SignIn -> {
                onSignIn()
            }

            is SignInUiEvents.Input.EnterUserEmail -> {
                _signInState.update { it.copy(userEmail = signInUiEvents.email) }
            }

            is SignInUiEvents.Input.EnterUserPassword -> {
                _signInState.update { it.copy(userPassword = signInUiEvents.password) }
            }

            else -> { }
        }
    }

    private fun onSignIn(){
        viewModelScope.launch {
            authRepository.signIn(email = _signInState.value.userEmail, password = _signInState.value.userPassword).collect { authState ->

                Log.e("AuthState","AuthState : ${authState}")
                when(authState){
                    is Resource.Success -> {
                        Log.e("AuthState","Trigger to Home : ${authState}")
                        _event.send(SignInUiEvents.Navigate.Home)
                    }
                    is Resource.Error -> {

                    }
                }
            }

        }
    }
}

data class SignInUiState(
    val isLoading : Boolean = false,
    val userEmail : String = "",
    val userPassword : String = "",
)

sealed class SignInUiEvents{
    data object Idle : SignInUiEvents()
    
    sealed class Input{
        data class EnterUserEmail(val email: String) : SignInUiEvents()
        data class EnterUserPassword(val password: String) : SignInUiEvents()
    }

    sealed class ButtonClick{
        data object SignIn : SignInUiEvents()
    }

    sealed class Navigate{
        data object Home : SignInUiEvents()
        data object SignUp : SignInUiEvents()
    }

    data class ShowSnackBar(val message: String): SignInUiEvents()
}