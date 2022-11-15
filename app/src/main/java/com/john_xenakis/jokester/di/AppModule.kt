package com.john_xenakis.jokester.di

import com.john_xenakis.jokester.data.remote.JokeApi
import com.john_xenakis.jokester.repository.JokeRepository
import com.john_xenakis.jokester.repository.JokeRepositoryImpl
import com.john_xenakis.jokester.util.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * The app module containing
 * each providing dependencies to the app and its code.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * The Moshi instance variable.
     */
    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    /**
     * The retrofit instance variable.
     */
    val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    /**
     * Provides the joke repository to the app.
     * @param jokeApi The joke api.
     * @return The main implementation of joke repository.
     */
    @Singleton
    @Provides
    fun provideJokeRepository(
        jokeApi: JokeApi) = JokeRepositoryImpl(jokeApi, provideDispatcher())

    /**
     * Provides the joke api to the app and its code.
     * @return The joke api.
     */
    @Singleton
    @Provides
    fun provideJokeApi(): JokeApi {
        return retrofit.create(JokeApi::class.java)
    }

    /**
     * Provides the coroutine dispatcher to the app and its code.
     * @return The coroutine dispatcher.
     */
    @Singleton
    @Provides
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}

/**
 * The app module containing
 * each binding dependencies to the app and its code. The app module for binding functions.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModuleBinds {
    /**
     * Binds the joke repository to the app and its code.
     * @param jokeRepositoryImpl The main implementation of the joke repository.
     * and how joke repo works.
     * @return The joke repository.
     */
    @Singleton
    @Binds
    abstract fun bindJokeRepository(jokeRepositoryImpl: JokeRepositoryImpl): JokeRepository
}