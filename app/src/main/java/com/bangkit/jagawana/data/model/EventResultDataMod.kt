package com.bangkit.jagawana.data.model

data class EventResultDataMod(
    val idClip: String,
    val idAudioFile: String,
    val startInterval: Int,
    val endInterval: Int,
    val idDevice: String,
    val region: String,
    val longitude: Double,
    val latitude: Double,
    val timestamp: String,
    val classifyResult: String,
    val link: String
)