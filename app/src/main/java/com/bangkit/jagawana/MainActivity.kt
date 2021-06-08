package com.bangkit.jagawana

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bangkit.jagawana.data.MyRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val repo = MyRepository(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

        scheduler.scheduleAtFixedRate(Runnable {
            repo.periksaNotifikasi(this)
            Log.e("qwerty", "test")
        }, 0, 1, TimeUnit.MINUTES) //periksa notifikasi tiap 1 menit jika aplikasi dibuka

    }
}