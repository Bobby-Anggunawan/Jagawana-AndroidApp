package com.bangkit.jagawana.ui

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bangkit.jagawana.R
import com.bangkit.jagawana.data.MyRepository
import com.bangkit.jagawana.data.RemoteDataSource
import com.bangkit.jagawana.data.model.DeviceDataMod
import com.bangkit.jagawana.data.model.EventResultDataMod
import com.bangkit.jagawana.databinding.FragmentDetailEventBinding
import com.bangkit.jagawana.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

class DetailEventFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //animation https://material.io/develop/android/theming/motion#shared-axis
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)
    }


    private lateinit var binding: FragmentDetailEventBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailEventBinding.inflate(layoutInflater)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<MaterialToolbar>(R.id.topAppBar).setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        val repo = MyRepository(requireContext())

        val bundle = this.arguments
        if (bundle != null) {
            val clipId = bundle.getString("clipID", "")

            viewLifecycleOwner.lifecycleScope.launch{
                repo.getEvent(clipId).collect{ event->
                    binding.namaRegion.text = event.idClip
                    binding.recoedId.text = event.idAudioFile
                    binding.clip.text = event.idClip
                    binding.device.text = event.idDevice
                    binding.region.text = event.region
                    binding.result.text = event.classifyResult
                    binding.timestamp.text = event.timestamp

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
            }
        }
    }


}