package com.bangkit.jagawana.utility.function

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import java.net.HttpURLConnection


object IsOnline {
    fun isOnline(activity: Activity): Boolean {
        val myConnectivity =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = myConnectivity.activeNetworkInfo
        return network?.isConnected ?: false
    }
}