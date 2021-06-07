package com.bangkit.jagawana.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.bangkit.jagawana.R
import com.bangkit.jagawana.data.MyRepository
import com.bangkit.jagawana.databinding.FragmentHomeBinding
import com.bangkit.jagawana.databinding.FragmentHomeContainerBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    lateinit var repo : MyRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repo = MyRepository(requireContext())

        binding.MapsButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putDouble("latitude", repo.readDoublePreference(requireActivity(), "latitudeRegionAktif"))
            bundle.putDouble("longitude", repo.readDoublePreference(requireActivity(), "longitudeRegionAktif"))

            findNavController().navigate(R.id.fragment_map, bundle)
        }
        binding.listDeviceButton.setOnClickListener {
            findNavController().navigate(R.id.fragment_deviceList)
        }
        //nama region aktif di set di list region fragment
        binding.textButton.text = repo.readIdPreference(requireActivity(), "namaRegionAktif")
        binding.textButton.setOnClickListener {
            findNavController().navigate(R.id.fragment_regionList)
        }
    }

}