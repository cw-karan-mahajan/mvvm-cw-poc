package com.karan.cloudwalkerpoc1.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.karan.cloudwalkerpoc1.ui.screens.MobileNumberScreen
import com.karan.cloudwalkerpoc1.ui.screens.OtpViewScreen
import com.karan.cloudwalkerpoc1.ui.screens.SuccessViewScreen
import com.karan.cloudwalkerpoc1.ui.viewmodels.LoginViewModel

@Composable
fun SetupNavGraph(navController: NavHostController, viewModel: LoginViewModel) {

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.MobileScreen.route
    ) {
        composable(route = ScreenRoute.MobileScreen.route) {
            MobileNumberScreen(viewModel = viewModel, navController = navController)
        }
        composable(route = ScreenRoute.OtpScreen.route) {
            OtpViewScreen(viewModel = viewModel, navController = navController)
        }
        composable(route = ScreenRoute.SuccessScreen.route) {
            SuccessViewScreen(viewModel = viewModel)
        }
    }
}