package com.karan.cloudwalkerpoc1.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karan.cloudwalkerpoc1.core.Resource
import com.karan.cloudwalkerpoc1.data.OtpRequest
import com.karan.cloudwalkerpoc1.data.OtpResponse
import com.karan.cloudwalkerpoc1.repository.AuthRepository
import com.karan.cloudwalkerpoc1.ui.components.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _stateSubmitResponse =
        MutableStateFlow<ViewState<OtpResponse>>(ViewState.loading(false))

    // UI collect from this stateFlow to get the state updates
    val stateSubmitResponse: StateFlow<ViewState<OtpResponse>> = _stateSubmitResponse

    private var _mobileNumber = mutableStateOf("")
    var mobileNumber: State<String> = _mobileNumber

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private var _otpNumber = mutableStateOf("")
    var otpNumber: State<String> = _otpNumber

    var isSuccessScreen: Boolean = false

    // mobile data
    fun appendMobileDigit(digit: Int) {
        if (_mobileNumber.value.length <= 9)
            _mobileNumber.value += digit.toString()
    }

    fun clearMobileNumber() {
        _mobileNumber.value = ""
    }

    fun isValidMobileNumber(): Boolean {
        return _mobileNumber.value.length in 10..10
    }

    fun onDeleteMobileClicked() {
        val currentNumber = _mobileNumber.value
        if (currentNumber.isNotEmpty()) {
            _mobileNumber.value = currentNumber.dropLast(1)
        }
    }

    fun onMobileNumberChange(newNumber: String) {
        _mobileNumber.value = newNumber.take(10)
    }

    // otp data
    fun appendOtpDigit(digit: Int) {
        if (_otpNumber.value.length <= 3)
            _otpNumber.value += digit.toString()
    }

    fun clearOtpNumber() {
        _otpNumber.value = ""
    }

    fun isValidOtpNumber(): Boolean {
        return _otpNumber.value.length in 4..4
    }

    fun onDeleteOtpClicked() {
        val currentNumber = _otpNumber.value
        if (currentNumber.isNotEmpty()) {
            _otpNumber.value = currentNumber.dropLast(1)
        }
    }

    fun onOtpNumberChange(newNumber: String) {
        _otpNumber.value = newNumber.take(4)
    }


    fun submitOtp(mobile: String, otp: String) {
        val otpRequest = OtpRequest(mobile, otp, "d92845f8c66fb19dfbfc2cdeedce057a")
        _isLoading.value = true
        _stateSubmitResponse.value = ViewState.Loading(_isLoading.value)
        viewModelScope.launch {
            repository.submitOtp(otpRequest).distinctUntilChanged().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _isLoading.value = true
                        _stateSubmitResponse.value = ViewState.Success(result.data)
                    }

                    is Resource.Error -> {
                        _isLoading.value = true
                        _stateSubmitResponse.value = ViewState.Failed(result.message)
                    }
                }
            }
        }

    }
}