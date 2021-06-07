package com.bangkit.jagawana.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeviceDataMod(
    @PrimaryKey val idDevice: String,
    val idRegion: Int,
    val latitude: Double,
    val longitude: Double,
    val region: String
)