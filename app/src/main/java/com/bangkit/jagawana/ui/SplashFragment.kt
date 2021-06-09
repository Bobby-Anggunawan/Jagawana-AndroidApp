package com.bangkit.jagawana.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bangkit.jagawana.R
import com.bangkit.jagawana.data.MyRepository
import com.bangkit.jagawana.data.RemoteDataSource
import com.bangkit.jagawana.utility.function.IsOnline
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onResume() {
        super.onResume()

        val repo = MyRepository(requireContext())

        val handler = Handler()

        if(IsOnline.isOnline(requireActivity())){
            //todo handlernya deprecated dan kalau bisa delaynya(dibawah) dikurangi
            handler.postDelayed({
                runBlocking {
                    async(Dispatchers.IO) { repo.devicePopulateDB() }.await()
                    async(Dispatchers.IO) { repo.historyPopulateDB() }.await()
                }
                findNavController().navigate(R.id.fragment_home_container)
            }, 500);
        }
        else{
            viewLifecycleOwner.lifecycleScope.launch{
                repo.getAllDevice().collect {
                    if(it.count() == 0){
                        MaterialAlertDialogBuilder(requireContext())
                            .setMessage("Hubungkan perangkat anda ke internet")
                            .setPositiveButton("Ok") { dialog, which ->
                                requireActivity().finish()
                            }
                            .show()
                    }
                    else{
                        //do nothing
                        findNavController().navigate(R.id.fragment_home_container)
                    }
                }
            }
        }
    }
}