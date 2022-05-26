package com.example.klindyuk1919materialdesign230522.repo

import com.example.klindyuk1919materialdesign230522.utils.NASA_ENDPOINT
import com.example.klindyuk1919materialdesign230522.utils.QUERY_API_KEY
import com.example.klindyuk1919materialdesign230522.utils.QUERY_DATE
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayApi {
    @GET(NASA_ENDPOINT)
    fun getPictureOfTheDay(
        @Query(QUERY_API_KEY) apiKey: String,
        @Query(QUERY_DATE) date: String
    ): Call<PictureOfTheDayResponseData>
}