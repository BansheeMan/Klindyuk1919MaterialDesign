package com.example.klindyuk1919materialdesign230522.repo.dto.lifs

import com.google.gson.annotations.SerializedName

data class SputnikServerResponseData(
    val date: String,
    val id: String,
    val resource: Resource,
    @SerializedName("service_version")
    val serviceVersion: String,
    val url: String
)
