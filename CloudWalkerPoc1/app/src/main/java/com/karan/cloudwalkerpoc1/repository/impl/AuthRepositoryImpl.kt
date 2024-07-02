package com.karan.cloudwalkerpoc1.repository.impl

import com.karan.cloudwalkerpoc1.core.Resource
import com.karan.cloudwalkerpoc1.data.OtpRequest
import com.karan.cloudwalkerpoc1.data.OtpResponse
import com.karan.cloudwalkerpoc1.remote.PostsApiService
import com.karan.cloudwalkerpoc1.repository.AuthRepository
import com.karan.cloudwalkerpoc1.util.NetworkConnectivity
import com.karan.cloudwalkerpoc1.util.getResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@Singleton
class AuthRepositoryImpl @Inject internal constructor(
    private val authService: PostsApiService,
    private val networkConnectivity: NetworkConnectivity
) : AuthRepository {

    override fun submitOtp(userRequest: OtpRequest): Flow<Resource<OtpResponse>> = flow {
        if (!networkConnectivity.isConnected()) {
            emit(Resource.error("You are offline. Connect to the Internet to access the app"))
            return@flow
        } else {
            val code = authService.submitOtp(userRequest).code()
            val authServiceResponse = authService.submitOtp(userRequest).getResponse()
            val state =
                if (authServiceResponse != null && code == 200) Resource.success(authServiceResponse) else Resource.error(
                    "Something went wrong"
                )
            emit(state)
        }
    }.catch { e -> emit(Resource.error("Something went wrong $e")) }
        //.flowOn(Dispatchers.IO)

    //.catch { e -> emit(Resource.error("Something went wrong $e")) }
}