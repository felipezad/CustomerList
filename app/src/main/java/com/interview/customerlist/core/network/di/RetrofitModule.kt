package com.interview.customerlist.core.network.di

import com.interview.customerlist.core.network.CustomerDetailsService
import com.interview.customerlist.core.network.CustomerListService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.code-challenge.patronus-group.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    fun provideCustomerListService(retrofit: Retrofit): CustomerListService {
        return retrofit.create()
    }

    @Provides
    fun provideCustomerDetailsService(retrofit: Retrofit): CustomerDetailsService {
        return retrofit.create()
    }


    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }
}
