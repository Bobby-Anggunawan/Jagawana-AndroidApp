package com.bangkit.jagawana.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.jagawana.R
import com.bangkit.jagawana.data.MyRepository
import com.bangkit.jagawana.data.RemoteDataSource
import com.bangkit.jagawana.data.model.DetailDeviceDataMod
import com.bangkit.jagawana.data.model.EventResultDataMod
import com.bangkit.jagawana.databinding.FragmentDetailDeviceBinding
import com.bangkit.jagawana.databinding.FragmentDetailEventBinding
import com.bangkit.jagawana.ui.adapter.RegionHistoryAdapter
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class DetailDeviceFragment : Fragment() {

    private lateinit var binding: FragmentDetailDeviceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailDeviceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        var deviceID = ""
        if(bundle != null){
            deviceID = bundle.getString("idDetailDevice", "null")
        }

        lateinit var pageData: DetailDeviceDataMod
        runBlocking {
            val getFromApi = async(Dispatchers.IO) { MyRepository(requireContext()).populateDetailDeviceFragment(deviceID) }
            pageData = getFromApi.await()
        }

        //populate data
        binding.topAppBar.title = deviceID
        binding.lastTransmission.text = pageData.lastTransmission
        binding.Status.text = pageData.status
        binding.location.text = pageData.location
        binding.totalRecord.text = pageData.totalRecord

        SetAdapter(binding.notificationList, pageData.listRecord)

        view.findViewById<MaterialToolbar>(R.id.topAppBar).setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }


    fun SetAdapter(myRecyclerView: RecyclerView, data: ArrayList<RegionHistoryAdapter.RowData>){
        myRecyclerView.layoutManager = LinearLayoutManager(getActivity())
        val ListAdapter = RegionHistoryAdapter(data) //arraylist berisi data
        myRecyclerView.adapter = ListAdapter
        //mengatur onclick tiap item
        ListAdapter.onItemClick = {
            val bundle = Bundle()
            bundle.putString("clipID", it.idClip)
            findNavController().navigate(R.id.fragment_detailEvent, bundle)
        }
    }
}