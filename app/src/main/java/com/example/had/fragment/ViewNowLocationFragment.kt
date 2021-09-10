package com.example.had.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.had.R
import com.example.had.activity.SetNowLocationActivity
import com.example.had.databinding.FragmentViewNowLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
//import com.naver.maps.geometry.LatLng
//import com.google.type.LatLng
//import com.naver.maps.map.MapView
//import com.naver.maps.map.NaverMap
//import com.naver.maps.map.OnMapReadyCallback
//import com.naver.maps.map.overlay.Marker

class ViewNowLocationFragment : Fragment(), OnMapReadyCallback {

    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SetNowLocationActivity)
            mContext = context
    }

    private lateinit var mView: MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_view_now_location , container, false)
        mView = view.findViewById(R.id.mapView)
        mView.onCreate(savedInstanceState)
        mView.getMapAsync(this)

        return view
    }

    override fun onMapReady(map: GoogleMap) {
        val marker = LatLng(37.5670135, 126.9783740)
        map.addMarker(MarkerOptions().position(marker).title("여기"))
        map.moveCamera(CameraUpdateFactory.newLatLng(marker))
        map.moveCamera(CameraUpdateFactory.zoomTo(15f))
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