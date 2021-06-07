package com.bangkit.jagawana.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bangkit.jagawana.R
import com.bangkit.jagawana.data.MyRepository
import com.bangkit.jagawana.data.RemoteDataSource
import com.bangkit.jagawana.data.model.DeviceDataMod
import com.bangkit.jagawana.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class MapsFragment : Fragment() {
    private lateinit var binding: FragmentMapsBinding
    private var collapsBefore = true
    lateinit var repo: MyRepository
    val markerlist:  ArrayList<Marker> = arrayListOf()

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        googleMap.setMapType(MAP_TYPE_HYBRID)


        /*
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

        //todo example marker
        /*
        googleMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("My Spot")
                .snippet("This is my spot!")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        )*/
        lateinit var device: Array<DeviceDataMod>
        runBlocking {
            val getFromApi = async(Dispatchers.IO) { RemoteDataSource().getAllDevice() }
            device = getFromApi.await()
        }
        device.forEach {
            val pos = LatLng(it.latitude, it.longitude)
            val regionName = repo.readIdPreference(requireActivity(), "namaRegionAktif")
            if(it.region == regionName){
                val mark = googleMap.addMarker(MarkerOptions().position(pos).title(it.idDevice))
                if(mark != null){
                    mark.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    markerlist.add(mark)
                }
            }
        }

        googleMap.setOnMarkerClickListener { marker ->
            val bundle = Bundle()
            bundle.putString("idDetailDevice", marker.title)
            findNavController().navigate(R.id.fragment_detailDevice, bundle)
            true
        }

        //gerakkan kamera ke region yang dipilih
        val bundle = this.arguments
        if (bundle != null) {
            val latitude = bundle.getDouble("latitude", 0.0)
            val longitude = bundle.getDouble("longitude", 0.0)
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 6.0f))
        }


        //scheduller di sini
        val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        scheduler.scheduleAtFixedRate(Runnable {
            Log.e("myerr", markerlist.count().toString())
            if(repo.readIdPreference(requireActivity(), MyRepository.keys.adaEventBaru) == MyRepository.keys.eventBaruBelumDiTrigger){
                //tandai event sudah di trigger
                repo.writeIdPreference(MyRepository.keys.adaEventBaru, MyRepository.keys.eventBaruSudahDiTrigger, requireActivity())

                val idMarkerUntukDiubah = repo.readIdPreference(requireActivity(), MyRepository.keys.idDevicen)
                markerlist.forEach {
                    Log.e("myerr1", it.title.toString())
                    Log.e("myerr2", idMarkerUntukDiubah.toString())
                    if(it.title == idMarkerUntukDiubah){
                        it.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    }
                }
            }
        }, 0, 1, TimeUnit.MINUTES) //periksa notifikasi tiap 1 menit jika aplikasi dibuka
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentMapsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repo = MyRepository(requireContext())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        //my code
        binding.openMenu.setOnClickListener {
            findNavController().navigate(R.id.fragment_home_container)
        }

        setBackDrop(binding.backdrop)

    }

    fun setBackDrop(container: FrameLayout){
        val sheetBehavior = BottomSheetBehavior.from(container)
        sheetBehavior.setFitToContents(false)
        sheetBehavior.setHideable(false)
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)

        //Offset increases as this bottom sheet is moving upward. From 0 to 1 the sheet is between collapsed and expanded states and from -1 to 0 it is between hidden and collapsed states
        sheetBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //buat gak bisa jadi STATE_HALF_EXPANDED
                if(newState == BottomSheetBehavior.STATE_HALF_EXPANDED){
                    if(collapsBefore){
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                        collapsBefore = false
                    }
                    else{
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
                        collapsBefore = true
                    }
                }
                else if(newState == BottomSheetBehavior.STATE_EXPANDED){
                    collapsBefore = false
                }
                else if(newState == BottomSheetBehavior.STATE_COLLAPSED){
                    collapsBefore = true
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.history.alpha = slideOffset
                binding.mapPeekBottomSheet.alpha = 1 - slideOffset
            }
        })
    }
}
