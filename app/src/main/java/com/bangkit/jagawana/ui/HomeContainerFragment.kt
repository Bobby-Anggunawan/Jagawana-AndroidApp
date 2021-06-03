package com.bangkit.jagawana.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.bangkit.jagawana.R
import com.bangkit.jagawana.databinding.FragmentHomeContainerBinding
import com.bangkit.jagawana.databinding.FragmentMapsBinding

class HomeContainerFragment : Fragment() {
    private lateinit var binding: FragmentHomeContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeContainerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSmoothBottomMenu()


        //pop splash from backstack
        try{
            findNavController().popBackStack(R.id.splashFragment, true)
        }
        catch (e: Exception){}
    }


    private fun setupSmoothBottomMenu() {
        val initFR = getChildFragmentManager().beginTransaction()
        initFR.replace(R.id.viewContainer, HomeFragment())
        initFR.commit()

        binding.bottomBar.onItemSelected = {
            val fr = getChildFragmentManager().beginTransaction()
            if(it == 0){
                fr.replace(R.id.viewContainer, HomeFragment())
            }
            else if(it == 1){
                fr.replace(R.id.viewContainer, NotificationFragment())
            }
            else if(it == 2){
                fr.replace(R.id.viewContainer, SettingFragment())
            }
            fr.commit()
        }
    }
}