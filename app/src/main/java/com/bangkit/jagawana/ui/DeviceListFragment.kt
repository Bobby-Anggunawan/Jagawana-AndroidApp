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
import com.bangkit.jagawana.data.model.DeviceDataMod
import com.bangkit.jagawana.ui.adapter.DeviceListAdapter
import com.bangkit.jagawana.ui.adapter.RegionLListAdapter
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class DeviceListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_device_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data: ArrayList<DeviceListAdapter.RowData> = arrayListOf()
        lateinit var realData : Array<DeviceDataMod>
        runBlocking {
            val getFromApi = async(Dispatchers.IO) { RemoteDataSource().getAllDevice() }
            realData = getFromApi.await()

            val getFromApi2 = async(Dispatchers.IO) { MyRepository().getRegionList() }
            getFromApi2.await().forEach { regionName ->
                data.add(DeviceListAdapter.RowData(null, regionName.nama, true))
                realData.forEach { device ->
                    if(device.region == regionName.nama) data.add(DeviceListAdapter.RowData(device.idDevice, null, false))
                }
            }
        }
        SetAdapter(view.findViewById(R.id.listDevice), data)

        view.findViewById<MaterialToolbar>(R.id.topAppBar).setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }


    fun SetAdapter(myRecyclerView: RecyclerView, data: ArrayList<DeviceListAdapter.RowData>){
        myRecyclerView.layoutManager = LinearLayoutManager(getActivity())
        val ListAdapter = DeviceListAdapter(data) //arraylist berisi data
        myRecyclerView.adapter = ListAdapter

        //mengatur onclick tiap item
        ListAdapter.onItemClick = {
            val bundle = Bundle()
            bundle.putString("idDetailDevice", it.namaDevice)
            findNavController().navigate(R.id.fragment_detailDevice, bundle)
        }
    }
}