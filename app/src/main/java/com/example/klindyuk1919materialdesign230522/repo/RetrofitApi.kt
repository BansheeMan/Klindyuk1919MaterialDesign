package com.example.klindyuk1919materialdesign230522.repo

import com.example.klindyuk1919materialdesign230522.repo.dto.EarthEpicServerResponseData
import com.example.klindyuk1919materialdesign230522.repo.dto.PictureOfTheDayResponseData
import com.example.klindyuk1919materialdesign230522.repo.dto.lifs.SputnikServerResponseData
import com.example.klindyuk1919materialdesign230522.utils.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET(NASA_ENDPOINT_POD)
    fun getPictureOfTheDay(
        @Query(QUERY_API_KEY) apiKey: String,
        @Query(QUERY_DATE) date: String
    ): Call<PictureOfTheDayResponseData>

    @GET(NASA_ENDPOINT_EPIC)
    fun getEPIC(
        @Query(QUERY_API_KEY) apiKey: String,
    ): Call<List<EarthEpicServerResponseData>>

    @GET(NASA_ENDPOINT_EARTH_ASSETS)
    fun getLandscapeImageFromSputnik(
        @Query("lon") lon: Float = LIFS_DEFAULT_LON ,
        @Query("lat") lat: Float = LIFS_DEFAULT_LON,
        @Query("date") dateString: String = LIFS_DEFAULT_DATE,
        @Query("dim") dim: Float = 0.60f,
        @Query(QUERY_API_KEY) apiKey: String
    ): Call<SputnikServerResponseData>
}