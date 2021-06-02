package com.bangkit.jagawana.utility.function

import java.util.*
import java.util.concurrent.TimeUnit

object TimeDiff {
    //untuk dipake notifikasi. kalau timenya beda > 60 menit dari sekarang alarm tidak di trigger
    fun inMinutes(timeToCompare: String): Int{
        val cmpTime = Calendar.getInstance()
        val todayTime = Calendar.getInstance()
        cmpTime.set(timeToCompare.subSequence(0, 4).toString().toInt(), timeToCompare.subSequence(5, 7).toString().toInt() - 1, timeToCompare.subSequence(8, 10).toString().toInt(), timeToCompare.subSequence(11, 13).toString().toInt(), timeToCompare.subSequence(14, 16).toString().toInt())
        //if cmpTime before todayTime karena gak mungkin cmpTime lebih dari waktu sekarang
        if(cmpTime.time.before(todayTime.time)){
            return TimeUnit.MILLISECONDS.toMinutes(todayTime.timeInMillis - cmpTime.timeInMillis).toInt()
        }
        else{
            throw IllegalStateException("Waktu untuk dibandingkan lebih dari waktu sekarang")
        }
    }
}