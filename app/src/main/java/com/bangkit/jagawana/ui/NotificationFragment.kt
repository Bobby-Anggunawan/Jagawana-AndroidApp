package com.bangkit.jagawana.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.jagawana.R
import com.bangkit.jagawana.data.MyRepository
import com.bangkit.jagawana.data.RemoteDataSource
import com.bangkit.jagawana.data.model.DeviceDataMod
import com.bangkit.jagawana.databinding.FragmentHomeBinding
import com.bangkit.jagawana.databinding.FragmentNotificationBinding
import com.bangkit.jagawana.ui.adapter.NotificationAdapter
import com.bangkit.jagawana.ui.adapter.RegionHistoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lateinit var allNotification: ArrayList<NotificationAdapter.RowData>
        runBlocking {
            val getFromApi = async(Dispatchers.IO) { MyRepository().getNotifikasi() }
            allNotification = getFromApi.await()
        }
        SetAdapter(binding.notificationList, allNotification)
    }


    fun SetAdapter(myRecyclerView: RecyclerView, data: ArrayList<NotificationAdapter.RowData>){
        myRecyclerView.layoutManager = LinearLayoutManager(getActivity())
        val ListAdapter = NotificationAdapter(data) //arraylist berisi data
        myRecyclerView.adapter = ListAdapter
        //mengatur onclick tiap item
        ListAdapter.onItemClick = {
            //todo.. keknya gak usah onclick
        }
    }
}