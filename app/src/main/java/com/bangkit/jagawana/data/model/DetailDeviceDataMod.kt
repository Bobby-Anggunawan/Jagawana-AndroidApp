package com.bangkit.jagawana.data.model

import com.bangkit.jagawana.ui.adapter.RegionHistoryAdapter

data class DetailDeviceDataMod(
    var lastTransmission: String = "",
    var status: String = "",
    var location: String = "",
    var totalRecord: String = "",
    var listRecord: ArrayList<RegionHistoryAdapter.RowData> = arrayListOf()
)