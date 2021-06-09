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
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class RegionListFragment : Fragment() {

    lateinit var repo: MyRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //animation https://material.io/develop/android/theming/motion#shared-axis
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_region_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repo = MyRepository(requireContext())

        val myRecyclerView = view.findViewById<RecyclerView>(R.id.regionList)
        SetAdapter(myRecyclerView)
        view.findViewById<MaterialToolbar>(R.id.topAppBar).setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    fun SetAdapter(myRecyclerView: RecyclerView){
        lateinit var data: ArrayList<RegionLListAdapter.RowData>
        runBlocking {
            val getFromApi = async(Dispatchers.IO) { repo.getRegionList() }
            data = getFromApi.await()
        }

        myRecyclerView.layoutManager = LinearLayoutManager(getActivity())
        val ListAdapter = RegionLListAdapter(data) //arraylist berisi data
        myRecyclerView.adapter = ListAdapter

        //mengatur onclick tiap item
        ListAdapter.onItemClick = {
            repo.writeIdPreference("namaRegionAktif", it.nama, requireActivity())
            repo.writeIdPreference("latitudeRegionAktif", it.latitude, requireActivity())
            repo.writeIdPreference("longitudeRegionAktif", it.longitude, requireActivity())
            findNavController().navigate(R.id.fragment_home_container)
        }
    }
}