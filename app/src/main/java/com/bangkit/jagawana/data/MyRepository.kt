package com.bangkit.jagawana.data

import android.app.Activity
import android.content.Context
import androidx.room.Room
import com.bangkit.jagawana.data.model.DetailDeviceDataMod
import com.bangkit.jagawana.data.model.DeviceDataMod
import com.bangkit.jagawana.data.model.EventResultDataMod
import com.bangkit.jagawana.ui.adapter.RegionHistoryAdapter
import com.bangkit.jagawana.ui.adapter.RegionLListAdapter
import com.bangkit.jagawana.utility.function.TimeDiff

class MyRepository() {

    object keys{
        const val isExistKey = "qwerty"
        const val adaEventBaru = "event"
        const val adaEventBaru1 = "event1"
        const val jenisEvent = "jenisEvent"
        val eventBaruSudahDiTrigger = "sudah"
        val eventBaruBelumDiTrigger = "belum"

    }

    val remote = RemoteDataSource()
    /*
    val db = Room.databaseBuilder(
        context,
        MyDatabase.AppDatabase::class.java, "JagawanaDB"
    ).build()*/

    //from db
    fun getRegionHistory(regionName: String) {
        //return db.userDao().getRegionHistory(regionName)
    }

    fun getDeviceAtRegion(regionName: String){
        //return db.userDao().getDeviceOnARegion(regionName)
    }

    fun writeIdPreference(key: String, value: String, activity: Activity) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun writeIdPreference(key: String, value: Double, activity: Activity) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putFloat(key, value.toFloat())
            apply()
        }
    }

    fun readIdPreference(activity: Activity, key: String): String? {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getString(key, "null")
    }

    fun readDoublePreference(activity: Activity, key: String): Double {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getFloat(key, 0f).toDouble()
    }

    fun periksaNotifikasi(activity: Activity){
        val allFromServer = remote.getAllResult()

        val remoteRes = allFromServer[allFromServer.count() - 1]
        if(TimeDiff.inMinutes(remoteRes.timestamp) < 60){
            if(readIdPreference(activity, keys.isExistKey) != remoteRes.idClip){
                triggerAlarm(remoteRes, activity)
            }
        }
    }

    fun triggerAlarm(event: EventResultDataMod, activity: Activity){
        //todo push notification

        //write ke memory kalau notifikasi ini sudah pernah di trigger
        writeIdPreference(keys.isExistKey, event.idClip, activity)
        //untuk di fragment map
        writeIdPreference(keys.adaEventBaru, keys.eventBaruBelumDiTrigger, activity)
        //untuk di MapItemBottomSheetFragment
        writeIdPreference(keys.adaEventBaru1, keys.eventBaruBelumDiTrigger, activity)
        writeIdPreference(keys.jenisEvent, event.classifyResult, activity)
    }

    fun getRegionList(): ArrayList<RegionLListAdapter.RowData> {
        val data = arrayListOf<RegionLListAdapter.RowData>()
        remote.getAllDevice().forEach {  deviceData->
            var regionExist = false
            data.forEach { RegionList ->
                if(RegionList.nama == deviceData.region) regionExist = true
            }
            if(!regionExist) data.add(RegionLListAdapter.RowData(deviceData.region, deviceData.latitude, deviceData.longitude))
        }
        return data
    }

    fun getRegionHisory(activity: Activity): ArrayList<RegionHistoryAdapter.RowData>{
        val data = arrayListOf<RegionHistoryAdapter.RowData>()
        remote.getAllResult().forEach {
            if(it.region == readIdPreference(activity, "namaRegionAktif")){
                data.add(RegionHistoryAdapter.RowData(it.idDevice, it.classifyResult, it.timestamp, it.idClip))
            }
        }
        return data
    }

    fun getNumDevicePerRegion(regionName: String): Int{
        var ret = 0
        remote.getAllDevice().forEach {
            if(it.region == regionName) ret++
        }
        return ret
    }

    fun populateDetailDeviceFragment(deviceName: String): DetailDeviceDataMod {
        val ret = DetailDeviceDataMod()
        ret.status = "Aktif"
        remote.getAllResult().forEach {
            if(it.idDevice == deviceName){
                ret.lastTransmission = it.timestamp
                val koordinat = "${it.latitude}   ${it.longitude}"
                ret.location = koordinat

                ret.listRecord.add(RegionHistoryAdapter.RowData(it.idDevice, it.classifyResult, it.timestamp, it.idClip))
            }
        }
        ret.totalRecord = ret.listRecord.count().toString()

        return ret
    }
}