package com.bangkit.jagawana.ui

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.jagawana.R
import com.bangkit.jagawana.data.MyRepository
import com.bangkit.jagawana.data.RemoteDataSource
import com.bangkit.jagawana.data.model.DeviceDataMod
import com.bangkit.jagawana.databinding.FragmentMapItemBottomSheetBinding
import com.bangkit.jagawana.databinding.FragmentMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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

        val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        scheduler.scheduleAtFixedRate(Runnable {
            //todo belum bisa play audio
            if(repo.readIdPreference(requireActivity(), MyRepository.keys.adaEventBaru1) == MyRepository.keys.eventBaruBelumDiTrigger){
                //tandai event sudah di trigger
                repo.writeIdPreference(MyRepository.keys.adaEventBaru1, MyRepository.keys.eventBaruSudahDiTrigger, requireActivity())

                binding.playButton.visibility = View.VISIBLE
                binding.textView2.text = repo.readIdPreference(requireActivity(), MyRepository.keys.jenisEvent)
            }
            else{
                binding.playButton.visibility = View.GONE
                binding.textView2.text = "Hutan Aman"
            }
        }, 0, 1, TimeUnit.MINUTES)

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

        binding.playButton.setOnClickListener {
            val url = repo.readIdPreference(requireActivity(), MyRepository.keys.urlSound)
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
}