package com.example.themovies.utils

import com.example.themovies.data.MoviesAPIService
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitProvider {
    companion object {
        private const val BASE_URL = "https://www.omdbapi.com/"
        private const val API_KEY = "8099beff"

        fun getRetrofit(): MoviesAPIService {
            val client = OkHttpClient.Builder().addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url()

                val url: HttpUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter("apikey", API_KEY)
                    .build()

                val requestBuilder = original.newBuilder().url(url)
                val request = requestBuilder.build()
                chain.proceed(request)
            }.build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(MoviesAPIService::class.java)
        }
    }
}