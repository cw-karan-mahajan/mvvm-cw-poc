package com.karan.cloudwalkerpoc1.di

import android.app.Application
import android.content.Context
import com.karan.cloudwalkerpoc1.core.Constants.BASE_URL
import com.karan.cloudwalkerpoc1.remote.PostsApiService
import com.karan.cloudwalkerpoc1.util.Network
import com.karan.cloudwalkerpoc1.util.NetworkConnectivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): NetworkConnectivity {
        return Network(context)
    }

    private fun getHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val requestInterceptor = Interceptor { chain ->
            val url = chain.request().url
                .newBuilder()
                .build()

            val request = chain.request()
                .newBuilder()
                .header("Content-Type", "application/json")
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        val httpClient = OkHttpClient.Builder()
            .hostnameVerifier { _, _ -> true }
            .addInterceptor(requestInterceptor)
            .addInterceptor(interceptor)

        return httpClient.build()
    }

    @Provides
    @Singleton
    fun providesPostApi(): PostsApiService = Retrofit
        .Builder()
        .client(getHttpClient())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PostsApiService::class.java)

}