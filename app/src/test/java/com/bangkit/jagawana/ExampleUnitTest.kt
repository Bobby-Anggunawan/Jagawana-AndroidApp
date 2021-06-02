package com.bangkit.jagawana

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.bangkit.jagawana.data.MyDatabase
import com.bangkit.jagawana.utility.function.TimeDiff
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val db = Room.databaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MyDatabase.AppDatabase::class.java, "JagawanaDB"
        ).build().userDao()

        //masukin data ke table daftar device
        db.newDevice(MyDatabase.DeviceList("0", "medan", 1.0, 9.0))
        db.newDevice(MyDatabase.DeviceList("1", "medan", 5.0, 2.0))
        db.newDevice(MyDatabase.DeviceList("2", "medan", 3.0, 1.0))
        db.newDevice(MyDatabase.DeviceList("3", "medan", 9.0, 4.0))

        db.newDevice(MyDatabase.DeviceList("4", "pekanbaru", 1.0, 9.0))
        db.newDevice(MyDatabase.DeviceList("5", "pekanbaru", 5.0, 2.0))
        db.newDevice(MyDatabase.DeviceList("6", "pekanbaru", 3.0, 1.0))
        db.newDevice(MyDatabase.DeviceList("7", "pekanbaru", 9.0, 4.0))

        //masukin data history
        db.newHistory(MyDatabase.RegionHistory("0", "3", "kebakaran", "1234567890"))
        db.newHistory(MyDatabase.RegionHistory("1", "3", "mobil", "1234567890"))
        db.newHistory(MyDatabase.RegionHistory("2", "1", "penebangan", "1234567890"))
        db.newHistory(MyDatabase.RegionHistory("3", "2", "pesawat", "1234567890"))

        db.newHistory(MyDatabase.RegionHistory("4", "1", "penebangan", "1234567890"))
        db.newHistory(MyDatabase.RegionHistory("5", "2", "pesawat", "1234567890"))
    }
}