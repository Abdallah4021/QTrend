package com.neu.trend.dependencyinjection

import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import com.neu.trend.BuildConfig
import com.neu.trend.data.datasource.RemoteDataSource
import com.neu.trend.data.services.UserService
import com.neu.trend.network.AccessTokenInterceptor
import com.neu.trend.network.client
import com.neu.trend.network.loggerInterceptor
import com.neu.trend.utils.CONNECTION_TIMEOUT
import com.neu.trend.utils.moshi.moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /* create first Retrofit client to inject it to Authenticator interceptor, called : AuthenticationRetrofitClient */
    @Provides
    @Singleton
    @Named("AuthenticationRetrofitClient")
    fun provideAuthenticationRetrofit(@Named("AuthenticationOkHttpClient") okHttpClient: OkHttpClient): Retrofit {
        return retrofitInstance(
                BuildConfig.SERVER_URL,
                okHttpClient,
                ScalarsConverterFactory.create(),
                provideMoshi()
        )
    }

    @Provides
    @Singleton
    @Named("AuthenticationOkHttpClient")
    fun provideAuthenticationOkHttpClient(): OkHttpClient = client {
        addInterceptor(loggerInterceptor)
        connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
        readTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
    }
    /*  end AuthenticationRetrofitClient instance */

    /* create second Retrofit client for all other network process, called : AppOkHttpClient */
    @Provides
    @Singleton
    @Named("AppOkHttpClient")
    fun provideOkHttpClient(): OkHttpClient = client {
        addInterceptor(AccessTokenInterceptor())
        addInterceptor(loggerInterceptor)
        connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
        readTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
    }

    @Provides
    @Singleton
    @Named("AppRetrofitClient")
    fun provideRetrofit(@Named("AppOkHttpClient") okHttpClient: OkHttpClient): Retrofit {
        return retrofitInstance(
                BuildConfig.SERVER_URL,
                okHttpClient,
                ScalarsConverterFactory.create(),
                provideMoshi()
        )
    }

    private fun retrofitInstance(
            baseUrl: String,
            okHttpClient: OkHttpClient,
            vararg factories: Converter.Factory = emptyArray()
    ): Retrofit =
            Retrofit.Builder().apply {
                baseUrl(baseUrl)
                client(okHttpClient)
                addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
                factories.forEach { addConverterFactory(it) }
            }.build()

    /* end  AppOkHttpClient instance */

    // Network Factories
    @Provides
    @Singleton
    fun provideMoshi(): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    // App services
    @Provides
    @Singleton
    fun provideUserService(@Named("AppRetrofitClient") retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
            userService: UserService
    ): RemoteDataSource {
        return RemoteDataSource(userService)
    }

}