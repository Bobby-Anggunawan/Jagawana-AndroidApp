package com.bangkit.jagawana.data

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.bangkit.jagawana.data.model.DetailDeviceDataMod
import com.bangkit.jagawana.data.model.DeviceDataMod
import com.bangkit.jagawana.data.model.EventResultDataMod
import com.bangkit.jagawana.ui.adapter.NotificationAdapter
import com.bangkit.jagawana.ui.adapter.RegionHistoryAdapter
import com.bangkit.jagawana.ui.adapter.RegionLListAdapter
import com.bangkit.jagawana.utility.function.Notifikasi
import com.bangkit.jagawana.utility.function.TimeDiff
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Suppress("BlockingMethodInNonBlockingContext")
class MyRepository(val context: Context) {

    object keys{
        const val isExistKey = "qwerty"
        const val adaEventBaru = "event"
        const val adaEventBaru1 = "event1"
        const val jenisEvent = "jenisEvent"
        const val urlSound = "urld"
        const val idDevicen = "untukUbahMarker"
        val eventBaruSudahDiTrigger = "sudah"
        val eventBaruBelumDiTrigger = "belum"

    }

    val remote = RemoteDataSource()

    val db = Room.databaseBuilder(
        context,
        MyDatabase.AppDatabase::class.java, "JagawanaDB"
    ).build()

    fun devicePopulateDB(){
        val data = remote.getAllDevice()

        db.userDao().deleteAllDevice()
        data.forEach {
            db.userDao().newDevice(it)
        }
    }

    fun historyPopulateDB(){
        val data = remote.getAllResult()

        db.userDao().deleteAllHistory()
        data.forEach {
            db.userDao().newHistory(it)
        }
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

    fun periksaNotifikasi(activity: Activity, context: Context){
        val allFromServer = remote.getAllResult()

        val remoteRes = allFromServer[allFromServer.count() - 1]
        if(TimeDiff.inMinutes(remoteRes.timestamp) < 60){
            if(readIdPreference(activity, keys.isExistKey) != remoteRes.idClip){
                triggerAlarm(remoteRes, activity, context)
            }
        }
    }

    fun triggerAlarm(event: EventResultDataMod, activity: Activity, context: Context){

        //buat notifikasi
        val content = "${event.classifyResult} terdeteksi di region ${event.region}"
        Notifikasi.showAlarmNotification(context, content)

        //write ke memory kalau notifikasi ini sudah pernah di trigger
        writeIdPreference(keys.isExistKey, event.idClip, activity)
        //untuk di fragment map
        writeIdPreference(keys.adaEventBaru, keys.eventBaruBelumDiTrigger, activity)
        //untuk di MapItemBottomSheetFragment
        writeIdPreference(keys.adaEventBaru1, keys.eventBaruBelumDiTrigger, activity)
        writeIdPreference(keys.jenisEvent, event.classifyResult, activity)

        //untuk play sound di mapitembottomsheet fragment
        writeIdPreference(keys.urlSound, event.link, activity)

        //untuk ubah warna marker yang mendeteksi event di map fragment
        writeIdPreference(keys.idDevicen, event.idDevice, activity)
    }

    fun getRegionList(): ArrayList<RegionLListAdapter.RowData> {
        val data = arrayListOf<RegionLListAdapter.RowData>()

        db.userDao().getAllDevice().forEach {  deviceData->
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
        db.userDao().getAllHistory().forEach {
            if(it.region == readIdPreference(activity, "namaRegionAktif")){
                data.add(RegionHistoryAdapter.RowData(it.idDevice, it.classifyResult, it.timestamp, it.idClip))
            }
        }
        return data
    }

    fun getNumDevicePerRegion(regionName: String): Int{
        var ret = 0
        db.userDao().getAllHistory().forEach {
            if(it.region == regionName) ret++
        }
        return ret
    }

    fun populateDetailDeviceFragment(deviceName: String): DetailDeviceDataMod {
        val ret = DetailDeviceDataMod()
        ret.status = "Aktif"

        db.userDao().getAllHistory().forEach {
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

    //halaman notifikasi yang bisa diakses dari home di bottom navigation
    fun getNotifikasi(): ArrayList<NotificationAdapter.RowData>{
        val ret = arrayListOf<NotificationAdapter.RowData>()

        db.userDao().getAllHistory().forEach{
            val content = "${it.classifyResult} terdeteksi di region ${it.region}"
            ret.add(NotificationAdapter.RowData(it.classifyResult, content, it.timestamp))
        }
        return ret
    }

    //request tanpa diubah
    fun getAllDevice(): Flow<List<DeviceDataMod>> {
        return flow{
            emit(db.userDao().getAllDevice())
        }
    }

    fun getAllEvent(): Flow<List<EventResultDataMod>>{
        return flow{
            emit(db.userDao().getAllHistory())
        }
    }

    //todo pindahin data class ini ke file lain
    data class MarkerData(
        val deviceId: String,
        val latitude: Double,
        val longitude: Double,
        val isRed: Boolean
    )
    fun getMarketForMap(region: String): Flow<ArrayList<MarkerData>>{
        return flow{
            val device = withContext(Dispatchers.IO){db.userDao().getAllDevice()}
            val event = withContext(Dispatchers.IO){db.userDao().getAllHistory()}

            val ret = arrayListOf<MarkerData>()
            device.forEach { d ->
                var isRed = false
                event.forEach { e ->
                    if(e.idDevice == d.idDevice && TimeDiff.inMinutes(e.timestamp) < 60) isRed = true
                }
                if(d.region == region){
                    ret.add(MarkerData(d.idDevice, d.latitude, d.longitude, isRed))
                }
            }

            emit(ret)
        }
    }

    //di fragment mapitembottomsheet
    fun getRegionLastHistory(region: String): Flow<EventResultDataMod>{
        return flow{
            val allEvent = withContext(Dispatchers.IO){db.userDao().getRegionHistory(region)}
            emit(allEvent[allEvent.count()-1])
        }
    }

    //untuk di mainactivity masukin shared preference jika null
    fun getOneRegion(): Flow<DeviceDataMod>{
        return flow{
            val allDevice = withContext(Dispatchers.IO){db.userDao().getAllDevice()}
            emit(allDevice[0])
        }
    }
}