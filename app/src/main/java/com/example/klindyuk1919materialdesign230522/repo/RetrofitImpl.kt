package com.example.klindyuk1919materialdesign230522.repo

import com.example.klindyuk1919materialdesign230522.repo.dto.PictureOfTheDayResponseData
import com.example.klindyuk1919materialdesign230522.utils.NASA_DOMAIN
import com.example.klindyuk1919materialdesign230522.repo.dto.EarthEpicServerResponseData
import com.example.klindyuk1919materialdesign230522.repo.dto.lifs.SputnikServerResponseData
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl {
    private val nasaBaseUrl = NASA_DOMAIN

    private val api by lazy {
        Retrofit.Builder()
            .baseUrl(nasaBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(RetrofitApi::class.java)
    }

    fun getPictureOfTheDay(apiKey: String, date: String, podCallback: Callback<PictureOfTheDayResponseData>) {
        api.getPictureOfTheDay(apiKey, date).enqueue(podCallback)
    }

    fun getEPIC(apiKey: String, epicCallback: Callback<List<EarthEpicServerResponseData>>) {
        api.getEPIC(apiKey).enqueue(epicCallback)
    }

    fun getLandscapeImageFromSputnik(apiKey: String, lifsCallback: Callback<SputnikServerResponseData>) {
        api.getLandscapeImageFromSputnik(apiKey = apiKey).enqueue(lifsCallback)
    }
}