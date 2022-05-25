package com.example.klindyuk1919materialdesign230522.repo

import com.example.klindyuk1919materialdesign230522.utils.NASA_DOMAIN
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PictureOfTheDayRetrofitImpl {
    private val nasaBaseUrl = NASA_DOMAIN
    fun getRetrofit(): PictureOfTheDayApi {
        val pictureOfTheDayRetrofit = Retrofit.Builder()
            .baseUrl(nasaBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        return pictureOfTheDayRetrofit.create(PictureOfTheDayApi::class.java)
    }
}