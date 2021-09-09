package com.example.had.fragment

import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.had.R
import com.example.had.activity.SetNowLocationActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MarkerOptions
import com.google.type.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback

class ViewNowLocationFragment : Fragment(), OnMapReadyCallback {

    lateinit var mContext: Context
    private lateinit var mView: MapView

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SetNowLocationActivity)
            mContext = context
    }

    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_view_now_location, container, false)
        mView = rootView.findViewById(R.id.mapView)
        mView.onCreate(savedInstanceState)
        mView.getMapAsync(this)

        return rootView
    }*/


    /*override fun onMapReady(googleMap: GoogleMap) {
        //val marker = LatLng(37.568291, 126.997780)
        //googleMap.addMarker(MarkerOptions().position(marker).title("여기"))
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
    }*/

    override fun onMapReady(map: NaverMap) {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        super.onStart()
        mView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mView.onStop()
    }

    override fun onResume() {
        super.onResume()
        mView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mView.onLowMemory()
    }

    override fun onDestroy() {
        mView.onDestroy()
        super.onDestroy()
    }

}