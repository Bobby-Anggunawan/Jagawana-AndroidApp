package com.bangkit.jagawana.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.jagawana.R
import com.bangkit.jagawana.data.MyRepository
import com.bangkit.jagawana.ui.adapter.RegionHistoryAdapter
import com.bangkit.jagawana.ui.adapter.RegionLListAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class HistoryFragment : Fragment() {

    lateinit var topAppBar: MaterialToolbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topAppBar = view.findViewById(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        SetAdapter(view.findViewById(R.id.notificationList))
        view.findViewById<TextView>(R.id.namaRegion).text = MyRepository().readIdPreference(requireActivity(), "namaRegionAktif")

    }

    fun SetAdapter(myRecyclerView: RecyclerView){
        lateinit var data: ArrayList<RegionHistoryAdapter.RowData>
        runBlocking {
            val getFromApi = async(Dispatchers.IO) { MyRepository().getRegionHisory(requireActivity()) }
            data = getFromApi.await()
        }

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