package com.karan.cloudwalkerpoc1.repository


import com.karan.cloudwalkerpoc1.core.Resource
import com.karan.cloudwalkerpoc1.data.OtpRequest
import com.karan.cloudwalkerpoc1.data.OtpResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Singleton

@Singleton
interface AuthRepository {
    fun submitOtp(userRequest: OtpRequest): Flow<Resource<OtpResponse>>
}
