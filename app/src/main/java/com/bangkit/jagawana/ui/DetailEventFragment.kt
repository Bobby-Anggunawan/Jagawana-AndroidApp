package com.bangkit.jagawana.ui

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.jagawana.R
import com.bangkit.jagawana.data.RemoteDataSource
import com.bangkit.jagawana.data.model.DeviceDataMod
import com.bangkit.jagawana.data.model.EventResultDataMod
import com.bangkit.jagawana.databinding.FragmentDetailEventBinding
import com.bangkit.jagawana.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class DetailEventFragment : Fragment() {

    private lateinit var binding: FragmentDetailEventBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailEventBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        if (bundle != null) {
            val clipId = bundle.getString("clipID", "")
            lateinit var singleResult: EventResultDataMod
            runBlocking {
                val getFromApi = async(Dispatchers.IO) { RemoteDataSource().getSingleResult(clipId) }
                singleResult = getFromApi.await()
            }

            binding.namaRegion.text = singleResult.idClip
            binding.recoedId.text = singleResult.idAudioFile
            binding.clip.text = singleResult.idClip
            binding.device.text = singleResult.idDevice
            binding.region.text = singleResult.region
            binding.result.text = singleResult.classifyResult
            binding.timestamp.text = singleResult.timestamp

            binding.playButton.setOnClickListener {
                val url = singleResult.link
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


}