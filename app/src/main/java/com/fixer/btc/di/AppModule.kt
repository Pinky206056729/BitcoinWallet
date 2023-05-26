package com.fixer.btc.di

import com.fixer.btc.BuildConfig
import com.fixer.btc.data.remote.dto.FixerApi
import com.fixer.btc.data.repository.CurrencyRepoImpl
import com.fixer.btc.domain.model.repository.CurrencyRepository
import com.fixer.btc.domain.model.use_case.GetConvertedCurrencies
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val authInterceptor = Interceptor { chain ->
            val newRequest = chain.request()
                .newBuilder().addHeader("apikey", BuildConfig.API_KEY)
                .build()
            chain.proceed(newRequest)
        }
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
//            .addInterceptor(MockInterceptor())
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideFixerApi(okHttpClient: OkHttpClient): FixerApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.END_POINT)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FixerApi::class.java)

    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(api: FixerApi): CurrencyRepository {
        return CurrencyRepoImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetConvertedCurrencies(repo: CurrencyRepository): GetConvertedCurrencies {
        return GetConvertedCurrencies(repo)
    }
}