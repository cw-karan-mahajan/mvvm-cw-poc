package com.karan.cloudwalkerpoc1.remote

import com.karan.cloudwalkerpoc1.data.OtpRequest
import com.karan.cloudwalkerpoc1.data.OtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PostsApiService {

    @POST("profile/otp")
    suspend fun submitOtp(
        @Body userRequest: OtpRequest
    ): Response<OtpResponse>

}