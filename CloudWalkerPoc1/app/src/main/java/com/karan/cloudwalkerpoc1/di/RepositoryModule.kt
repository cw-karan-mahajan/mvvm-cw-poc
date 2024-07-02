package com.karan.cloudwalkerpoc1.di

import com.karan.cloudwalkerpoc1.repository.AuthRepository
import com.karan.cloudwalkerpoc1.repository.impl.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun providesAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository


}
