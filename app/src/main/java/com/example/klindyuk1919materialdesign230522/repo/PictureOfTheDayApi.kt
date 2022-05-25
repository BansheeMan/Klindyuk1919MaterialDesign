package com.example.klindyuk1919materialdesign230522.repo

import com.example.klindyuk1919materialdesign230522.utils.NASA_ENDPOINT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayApi {
    @GET(NASA_ENDPOINT)
    fun getPictureOfTheDay(@Query("api_key") apiKey:String) : Call<PictureOfTheDayResponseData>
}