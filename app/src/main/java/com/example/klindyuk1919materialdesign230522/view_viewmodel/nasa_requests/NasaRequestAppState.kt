package com.example.klindyuk1919materialdesign230522.view_viewmodel.nasa_requests

import com.example.klindyuk1919materialdesign230522.repo.dto.EarthEpicServerResponseData
import com.example.klindyuk1919materialdesign230522.repo.dto.PictureOfTheDayResponseData
import com.example.klindyuk1919materialdesign230522.repo.dto.lifs.SputnikServerResponseData

sealed class NasaRequestAppState {
    data class SuccessPOD(val pictureOfTheDayResponseData: PictureOfTheDayResponseData) :
        NasaRequestAppState()

    data class SuccessEPIC(val epic: List<EarthEpicServerResponseData>) :
        NasaRequestAppState()

    data class SuccessLIFS(val lifs: SputnikServerResponseData) :
        NasaRequestAppState()

    data class Error(val error: Throwable) : NasaRequestAppState()
    data class Loading(val progress: Int?) : NasaRequestAppState()
}
