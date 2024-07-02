package com.karan.cloudwalkerpoc1.ui.navigation

sealed class ScreenRoute(val route: String) {
    object MobileScreen : ScreenRoute("route_mobile")
    object OtpScreen : ScreenRoute("route_otp")
    object SuccessScreen : ScreenRoute("route_success")
}