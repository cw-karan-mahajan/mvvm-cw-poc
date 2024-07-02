package com.karan.cloudwalkerpoc1.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.karan.cloudwalkerpoc1.ui.navigation.ScreenRoute
import com.karan.cloudwalkerpoc1.ui.viewmodels.LoginViewModel


@SuppressLint("UnrememberedMutableState")
@Composable
fun MobileNumberScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val mobileNumber by viewModel.mobileNumber
    val isValidMobileNumber by derivedStateOf { viewModel.isValidMobileNumber() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Login or sign up to continue",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        BorderStroke(width = 1.dp, color = Color.LightGray),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    text = "+91",
                    fontSize = 12.sp,
                    modifier = Modifier
                        .width(45.dp)
                        .height(55.dp)
                        .padding(5.dp)
                        .wrapContentSize(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            OutlinedTextField(
                value = mobileNumber,
                onValueChange = {
                    viewModel.onMobileNumberChange(it)
                },
                label = { Text("Enter Mobile Number") },
                maxLines = 1,
                enabled = false,
                textStyle = TextStyle(textAlign = TextAlign.Start)
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        NumberPad(
            onDigitClick = viewModel::appendMobileDigit,
            onDeleteClick = viewModel::onDeleteMobileClicked,
            onClearClick = viewModel::clearMobileNumber,
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (isValidMobileNumber) {
            Button(
                onClick = {
                    navController.navigate(ScreenRoute.OtpScreen.route)
                },//viewModel.submitOtp(mobileNumber, "7777")
                modifier = Modifier.width(200.dp)
            ) {
                Text(text = "Get Otp")
            }
        }
    }
}

@Composable
fun NumberPad(
    onDigitClick: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        (1..9).chunked(3).forEach { row ->
            Row(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { digit ->
                    NumberButton(
                        digit = digit,
                        onClick = onDigitClick,
                    )
                }
            }
        }

        Row(
            modifier = Modifier.wrapContentWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NumberButton(
                digit = 0, onClick = onDigitClick
            )

            ExtraButton(
                input = "X", onTypeClick = onDeleteClick
            )

            ExtraButton(
                input = "Clr", onTypeClick = onClearClick
            )
        }
    }
}

@Composable
fun NumberButton(
    digit: Int, onClick: (Int) -> Unit
) {
    Box(modifier = Modifier.padding(5.dp)) {
        Button(
            onClick = { onClick(digit) },
            modifier = Modifier
                .width(75.dp)
                .height(70.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray
            ),
        ) {
            Text(
                text = digit.toString(), color = Color.White, fontSize = 12.sp
            )
        }
    }
}

@Composable
fun ExtraButton(
    input: String, onTypeClick: () -> Unit
) {
    Box(modifier = Modifier.padding(5.dp)) {
        Button(
            onClick = { onTypeClick() },
            modifier = Modifier
                .width(75.dp)
                .height(70.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray
            )
        ) {
            Text(
                text = input, color = Color.White, fontSize = 12.sp
            )
        }
    }
}
