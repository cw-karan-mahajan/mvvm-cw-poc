package com.karan.cloudwalkerpoc1.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.karan.cloudwalkerpoc1.R
import com.karan.cloudwalkerpoc1.ui.components.ViewState
import com.karan.cloudwalkerpoc1.ui.navigation.ScreenRoute
import com.karan.cloudwalkerpoc1.ui.viewmodels.LoginViewModel


@SuppressLint("UnrememberedMutableState")
@Composable
fun OtpViewScreen(viewModel: LoginViewModel = hiltViewModel(), navController: NavHostController) {
    val isLoading by viewModel.isLoading
    if (isLoading) {
        val state by viewModel.stateSubmitResponse.collectAsState()
        when (state) {
            is ViewState.Empty -> {
            }

            is ViewState.Failed -> {
                navController.navigate(ScreenRoute.SuccessScreen.route)
            }

            is ViewState.Loading -> {
                ShowLoading()
            }

            is ViewState.Success -> {
            }
        }

    } else {
        val otpNumber by viewModel.otpNumber
        val isValidOtpNumber by derivedStateOf { viewModel.isValidOtpNumber() }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Please enter 4 digit OTP sent to your mobile number",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                OutlinedTextField(
                    value = otpNumber,
                    onValueChange = {
                        viewModel.onOtpNumberChange(it)
                    },
                    label = { Text("Enter Otp Number") },
                    maxLines = 1,
                    enabled = false,
                    textStyle = TextStyle(textAlign = TextAlign.Start)
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            NumberPad(
                onDigitClick = viewModel::appendOtpDigit,
                onDeleteClick = viewModel::onDeleteOtpClicked,
                onClearClick = viewModel::clearOtpNumber,
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
            )

            Spacer(modifier = Modifier.height(10.dp))


            if (isValidOtpNumber) {
                Button(
                    onClick = { viewModel.submitOtp(viewModel.mobileNumber.value, "7777") },
                    modifier = Modifier.width(200.dp)
                ) {
                    Text(text = "Submit Otp")
                }
            }
        }
    }
}


@Composable
fun ShowLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}




