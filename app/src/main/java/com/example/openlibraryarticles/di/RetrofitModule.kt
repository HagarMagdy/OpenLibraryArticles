package com.example.openlibraryarticles.di

import com.example.openlibraryarticles.Config
import com.example.openlibraryarticles.api.OpenLibraryRetrofit
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
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor {
        return Interceptor {chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .build()


            // adding Authorization access token and content type as these headers are common in all api calls too
            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            println(chain.request().url)
            return@Interceptor chain.proceed(request)
        }
    }



    @Singleton
    @Provides
    fun provideOkHTTPClient(interceptor: Interceptor): OkHttpClient {
        // prepare okHttp client with request interceptor and connection time out
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            // this read time added as the open library api
            // sometimes take a long time to get response (to prevent socketTimeOut)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Config.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
    }

    @Singleton
    @Provides
    fun provideArticleService(retrofit: Retrofit.Builder): OpenLibraryRetrofit {
        return retrofit
            .build()
            .create(OpenLibraryRetrofit::class.java)
    }

}