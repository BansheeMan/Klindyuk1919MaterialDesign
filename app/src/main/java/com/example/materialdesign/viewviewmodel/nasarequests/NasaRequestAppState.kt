package com.example.materialdesign.viewviewmodel.nasarequests

import com.example.materialdesign.repo.dto.EarthEpicServerResponseData
import com.example.materialdesign.repo.dto.PictureOfTheDayResponseData
import com.example.materialdesign.repo.dto.lifs.SputnikServerResponseData

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
