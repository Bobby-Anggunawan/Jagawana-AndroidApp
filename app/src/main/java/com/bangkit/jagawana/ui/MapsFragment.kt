package com.bangkit.jagawana.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bangkit.jagawana.R
import com.bangkit.jagawana.data.MyRepository
import com.bangkit.jagawana.data.model.DeviceDataMod
import com.bangkit.jagawana.databinding.FragmentMapsBinding
import com.bangkit.jagawana.utility.function.TimeDiff
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class MapsFragment : Fragment(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener,
    ActivityCompat.OnRequestPermissionsResultCallback {
    private lateinit var binding: FragmentMapsBinding
    private var collapsBefore = true
    lateinit var repo: MyRepository

    private var permissionDenied = false
    lateinit var map: GoogleMap

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
        enableMyLocation()


        googleMap.setMapType(MAP_TYPE_HYBRID)


        viewLifecycleOwner.lifecycleScope.launch{
            val regionName = repo.readIdPreference(requireActivity(), "namaRegionAktif")
            if(regionName != null){
                repo.getMarketForMap(regionName).collect {
                    it.forEach { md ->
                        val pos = LatLng(md.latitude, md.longitude)
                        val mark = googleMap.addMarker(MarkerOptions().position(pos).title(md.deviceId))

                        if(mark != null){
                            if(md.isRed) mark.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            else mark.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        }
                    }
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



    //show my location
    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(requireContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(requireContext(), "Current location:\n$location", Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return
        }
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation()
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true
        }
    }



    //todo ini tadi kuubah
    override fun onResume() {
        super.onResume()

        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError()
            permissionDenied = false
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private fun showMissingPermissionError() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage("Permission lokasi tidak diberikan")
            .setPositiveButton("Ok") { dialog, which ->
                // Respond to positive button press
            }
            .show()
    }


}
