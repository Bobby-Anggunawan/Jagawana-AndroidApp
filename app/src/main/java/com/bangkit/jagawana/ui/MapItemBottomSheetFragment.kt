package com.bangkit.jagawana.ui

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bangkit.jagawana.R
import com.bangkit.jagawana.data.MyRepository
import com.bangkit.jagawana.data.RemoteDataSource
import com.bangkit.jagawana.data.model.DeviceDataMod
import com.bangkit.jagawana.databinding.FragmentMapItemBottomSheetBinding
import com.bangkit.jagawana.databinding.FragmentMapsBinding
import com.bangkit.jagawana.utility.function.TimeDiff
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class MapItemBottomSheetFragment : Fragment() {
    private lateinit var binding: FragmentMapItemBottomSheetBinding
    lateinit var repo: MyRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMapItemBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //scheduller di sini

        repo = MyRepository(requireContext())

        viewLifecycleOwner.lifecycleScope.launch{
            val regionName = repo.readIdPreference(requireActivity(), "namaRegionAktif")
            if(regionName != null){
                repo.getRegionLastHistory(regionName).collect { event->
                    if(TimeDiff.inMinutes(event.timestamp) < 60){
                        binding.playButton.visibility = View.VISIBLE
                        binding.textView2.text = event.classifyResult

                        //set play button
                        binding.playButton.setOnClickListener {
                            val url = event.link
                            val mediaPlayer = MediaPlayer().apply {
                                setAudioAttributes(
                                    AudioAttributes.Builder()
                                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                        .setUsage(AudioAttributes.USAGE_MEDIA)
                                        .build()
                                )
                                setDataSource(url)
                                prepare() // might take long! (for buffering, etc)
                                start()
                            }
                        }
                    }
                    else{
                        binding.playButton.visibility = View.GONE
                        binding.textView2.text = "Hutan Aman"
                    }
                }
            }
        }

        //get device connected
        var deviceNum = 0
        try{
            runBlocking {
                val getFromApi = async(Dispatchers.IO) { repo.readIdPreference(requireActivity(), "namaRegionAktif")?.let {
                    repo.getNumDevicePerRegion(it)
                } }
                deviceNum = getFromApi.await()!!
            }
        }
        catch (e: Exception){}
        val txt = "$deviceNum Device Connected"
        binding.textView.text = txt

    }
}