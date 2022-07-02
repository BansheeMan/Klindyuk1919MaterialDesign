package com.example.materialdesign.viewviewmodel.nasarequests

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.materialdesign.BuildConfig
import com.example.materialdesign.R
import com.example.materialdesign.repo.RetrofitImpl
import com.example.materialdesign.repo.dto.EarthEpicServerResponseData
import com.example.materialdesign.repo.dto.PictureOfTheDayResponseData
import com.example.materialdesign.repo.dto.lifs.SputnikServerResponseData
import com.example.materialdesign.utils.FORMAT_DATE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class NasaRequestViewModel(
    app: Application,
) : AndroidViewModel(app) {
    //AndroidViewModel. Подклассы должны иметь конструктор, который принимает Application в качестве единственного параметра.

    private val liveData: MutableLiveData<NasaRequestAppState> = MutableLiveData()
    private val retrofitImpl: RetrofitImpl =
        RetrofitImpl()

    fun getLiveData(): LiveData<NasaRequestAppState> {
        return liveData
    }

    //#####################  APOD ######################################################################
    fun sendRequestPOD(day: Int) {
        liveData.postValue(NasaRequestAppState.Loading(null))
        if (BuildConfig.NASA_API_KEY.isNullOrEmpty()) {
            errorNasaApiKey()
        } else {
            retrofitImpl.getPictureOfTheDay(BuildConfig.NASA_API_KEY, getData(day), podCallback)
        }
    }

    private val podCallback = object : Callback<PictureOfTheDayResponseData> {
        override fun onResponse(
            call: Call<PictureOfTheDayResponseData>,
            response: Response<PictureOfTheDayResponseData>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(NasaRequestAppState.SuccessPOD(it))
                }
            } else {
                val messageError = "${response.code()} ${response.message()}"
                liveData.postValue(NasaRequestAppState.Error(Exception(messageError)))
            }
            Log.d("@@@", "POD onResponse ${response.code()}")

        }

        override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
            liveData.postValue(NasaRequestAppState.Error(t))
            Log.d("@@@", "POD onFailure ${t.message}")

        }
    }

//#####################  EPIC ######################################################################

    fun sendRequestEPIC() {
        liveData.postValue(NasaRequestAppState.Loading(null))
        if (BuildConfig.NASA_API_KEY.isNullOrEmpty()) {
            errorNasaApiKey()
        } else {
            retrofitImpl.getEPIC(BuildConfig.NASA_API_KEY, epicCallback)
        }
    }

    private val epicCallback = object : Callback<List<EarthEpicServerResponseData>> {
        override fun onResponse(
            call: Call<List<EarthEpicServerResponseData>>,
            response: Response<List<EarthEpicServerResponseData>>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(NasaRequestAppState.SuccessEPIC(it))
                }
            } else {
                val messageError = "${response.code()} ${response.message()}"
                liveData.postValue(NasaRequestAppState.Error(Exception(messageError)))
            }
            Log.d("@@@", "EPIC onResponse ${response.code()}")
        }

        override fun onFailure(call: Call<List<EarthEpicServerResponseData>>, t: Throwable) {
            liveData.postValue(NasaRequestAppState.Error(t))
            Log.d("@@@", "EPIC onFailure ${t.message}")

        }
    }


//#####################  LandscapeImageFromSputnik #################################################

    fun sendRequestLIFS() {
        liveData.postValue(NasaRequestAppState.Loading(null))
        if (BuildConfig.NASA_API_KEY.isNullOrEmpty()) {
            errorNasaApiKey()
        } else {
            retrofitImpl.getLandscapeImageFromSputnik(BuildConfig.NASA_API_KEY, lifsCallback)
        }
    }

    private val lifsCallback = object : Callback<SputnikServerResponseData> {

        override fun onResponse(
            call: Call<SputnikServerResponseData>,
            response: Response<SputnikServerResponseData>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(NasaRequestAppState.SuccessLIFS(it))
                }
            } else {
                val messageError = "${response.code()} ${response.message()}"
                liveData.postValue(NasaRequestAppState.Error(Exception(messageError)))
            }
        }

        override fun onFailure(call: Call<SputnikServerResponseData>, t: Throwable) {
            liveData.postValue(NasaRequestAppState.Error(t))
        }
    }

//#####################  OTHER #####################################################################

    private fun errorNasaApiKey() {
        liveData.postValue(
            NasaRequestAppState.Error(
                Exception(
                    getApplication<Application>().resources.getString(
                        R.string.error_nasa_api_key
                    )
                )
            )
        )
    }

    private fun getData(day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, day)
        val dateFormat = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}
