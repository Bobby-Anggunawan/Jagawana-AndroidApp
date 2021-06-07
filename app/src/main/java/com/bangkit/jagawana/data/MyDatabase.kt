package com.bangkit.jagawana.data

import androidx.room.*
import com.bangkit.jagawana.data.model.DeviceDataMod
import com.bangkit.jagawana.data.model.EventResultDataMod

object MyDatabase {

    @Dao
    interface MapsDao {
        @Query("SELECT * FROM EventResultDataMod where EXISTS(select * from DeviceDataMod where EventResultDataMod.idDevice = DeviceDataMod.idDevice and DeviceDataMod.region= :regionName)")
        fun getRegionHistory(regionName: String): List<EventResultDataMod>

        @Query("SELECT * FROM EventResultDataMod")
        fun getAllHistory(): List<EventResultDataMod>

        @Query("SELECT * FROM DeviceDataMod WHERE region = :regionName")
        fun getDeviceOnARegion(regionName: String): List<DeviceDataMod>

        @Query("SELECT * FROM DeviceDataMod")
        fun getAllDevice(): List<DeviceDataMod>

        @Insert
        fun newHistory(vararg data: EventResultDataMod)

        @Insert
        fun newDevice(vararg data: DeviceDataMod)

        @Query("Delete from DeviceDataMod" )
        fun deleteAllDevice()

        @Query("Delete from EventResultDataMod" )
        fun deleteAllHistory()
    }

    @Database(entities = arrayOf(DeviceDataMod::class, EventResultDataMod::class), version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun userDao(): MapsDao
    }
}