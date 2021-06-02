package com.bangkit.jagawana.data

import androidx.room.*

object MyDatabase {
    //dipakai di fragment pilih region dan map
    @Entity
    data class DeviceList(
        @PrimaryKey val deviceName: String,
        val region: String,
        val longitude: Double,
        val latitude: Double
    )

    //dipakai di fragment riwayat per region(bottom sheet di fragment map). Ini emang nampung semua region tapi pas ditampilin bakal di filter berdasarkan table DeviceList di atas
    @Entity
    data class RegionHistory(
        @PrimaryKey val idClip: String,
        val deviceName: String,
        val prediction: String,
        val dateAndTime: String     //entahlah mau type data apa.. keknya string lebih bagus
    )

    @Dao
    interface MapsDao {
        @Query("SELECT * FROM RegionHistory where EXISTS(select * from DeviceList where RegionHistory.deviceName = DeviceList.deviceName and DeviceList.region= :regionName)")
        fun getRegionHistory(regionName: String): List<RegionHistory>

        @Query("SELECT * FROM RegionHistory")
        fun getAllHistory(): List<RegionHistory>

        @Query("SELECT * FROM DeviceList WHERE region = :regionName")
        fun getDeviceOnARegion(regionName: String): List<DeviceList>

        @Insert
        fun newHistory(vararg data: RegionHistory)

        @Insert
        fun newDevice(vararg data: DeviceList)
    }

    @Database(entities = arrayOf(DeviceList::class, RegionHistory::class), version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun userDao(): MapsDao
    }
}