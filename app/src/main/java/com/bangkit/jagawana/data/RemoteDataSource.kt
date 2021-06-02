package com.bangkit.jagawana.data

import com.bangkit.jagawana.data.model.DeviceDataMod
import com.bangkit.jagawana.data.model.EventResultDataMod
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class RemoteDataSource {
    fun getAllResult(): Array<EventResultDataMod> {
        val myUrl = "https://xenon-anthem-312407.et.r.appspot.com/getallresult"
        val client = OkHttpClient()
        val request = Request.Builder().url(myUrl)
        client.newCall(request.build()).execute()
            .use { response ->
                val gson = Gson()
                return gson.fromJson(response.body!!.string(), Array<EventResultDataMod>::class.java)
            }
    }

    fun getAllDevice(): Array<DeviceDataMod> {
        val myUrl = "https://xenon-anthem-312407.et.r.appspot.com/getalldevices"
        val client = OkHttpClient()
        val request = Request.Builder().url(myUrl)
        client.newCall(request.build()).execute()
            .use { response ->
                val gson = Gson()
                return gson.fromJson(response.body!!.string(), Array<DeviceDataMod>::class.java)
            }
    }

    fun getSingleResult(resId: String): EventResultDataMod{
        val myUrl = "https://xenon-anthem-312407.et.r.appspot.com/getresultid?id=$resId"
        val client = OkHttpClient()
        val request = Request.Builder().url(myUrl)
        client.newCall(request.build()).execute()
            .use { response ->
                val gson = Gson()
                return gson.fromJson(response.body!!.string(), Array<EventResultDataMod>::class.java)[0]
            }
    }
}