package com.example.klindyuk1919materialdesign230522.view_viewmodel.picture

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.klindyuk1919materialdesign230522.BuildConfig
import com.example.klindyuk1919materialdesign230522.R
import com.example.klindyuk1919materialdesign230522.repo.PictureOfTheDayResponseData
import com.example.klindyuk1919materialdesign230522.repo.PictureOfTheDayRetrofitImpl
import com.example.klindyuk1919materialdesign230522.utils.FORMAT_DATE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<PictureOfTheDayAppState> = MutableLiveData(),
    private val pictureOfTheDayRetrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<PictureOfTheDayAppState> {
        return liveData
    }

    fun sendRequest(context: Context, day: Int) { //TODO утончинть про контекст
        liveData.postValue(PictureOfTheDayAppState.Loading(null))
        if (BuildConfig.NASA_API_KEY.isNullOrEmpty()) {
            liveData.postValue(PictureOfTheDayAppState.Error(Exception(context.getString(R.string.error_nasa_api_key))))
        } else {
            pictureOfTheDayRetrofitImpl.getRetrofit()
                .getPictureOfTheDay(BuildConfig.NASA_API_KEY, getData(day))
                .enqueue(callback)
        }
    }

    private fun getData(day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, day)
        val dateFormat = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private val callback = object : Callback<PictureOfTheDayResponseData> {
        override fun onResponse(
            call: Call<PictureOfTheDayResponseData>,
            response: Response<PictureOfTheDayResponseData>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(PictureOfTheDayAppState.Success(it))
                }
            } else {
                val messageError = "${response.code()} ${response.message()}"
                liveData.postValue(PictureOfTheDayAppState.Error(Exception(messageError)))
            }
        }

        override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
            liveData.postValue(PictureOfTheDayAppState.Error(t))
        }

    }


}