package com.example.tryout.network

import com.example.tryout.Util.Variabel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiService{
    var BASE_URL: String = "${Variabel().DOMAIN}/public/api/"
    val endpoint: ApiEndpoint
        get(){
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().connectTimeout(5,TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES).addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiEndpoint::class.java)
        }
}