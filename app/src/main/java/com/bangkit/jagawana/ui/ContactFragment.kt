package com.bangkit.jagawana.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bangkit.jagawana.R
import com.bangkit.jagawana.databinding.FragmentContactBinding
import com.bangkit.jagawana.databinding.FragmentMapsBinding


class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.submitButton.setOnClickListener {
            //todo belum diimplementasikan

            findNavController().navigate(R.id.fragment_home_container)
        }
    }
}