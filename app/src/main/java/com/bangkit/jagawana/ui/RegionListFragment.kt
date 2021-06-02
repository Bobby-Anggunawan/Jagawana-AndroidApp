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
import com.bangkit.jagawana.ui.adapter.RegionLListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class RegionListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_region_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myRecyclerView = view.findViewById<RecyclerView>(R.id.regionList)
        SetAdapter(myRecyclerView)
    }

    fun SetAdapter(myRecyclerView: RecyclerView){
        val repo = MyRepository()

        lateinit var data: ArrayList<RegionLListAdapter.RowData>
        runBlocking {
            val getFromApi = async(Dispatchers.IO) { MyRepository().getRegionList() }
            data = getFromApi.await()
        }

        myRecyclerView.layoutManager = LinearLayoutManager(getActivity())
        val ListAdapter = RegionLListAdapter(data) //arraylist berisi data
        myRecyclerView.adapter = ListAdapter

        //mengatur onclick tiap item
        ListAdapter.onItemClick = {
            MyRepository().writeIdPreference("namaRegionAktif", it.nama, requireActivity())
            MyRepository().writeIdPreference("latitudeRegionAktif", it.latitude, requireActivity())
            MyRepository().writeIdPreference("longitudeRegionAktif", it.longitude, requireActivity())
            findNavController().navigate(R.id.fragment_home_container)
        }
    }
}