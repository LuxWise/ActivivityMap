package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var textLatitud: TextView
    lateinit var textLongitud: TextView

    private var mGoogleMap: GoogleMap? = null
    private var currentMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textLatitud = findViewById(R.id.textLatitude)
        textLongitud = findViewById(R.id.textLongitude)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        addTextWatchers()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap

        val initialLocation = LatLng(-12.057816, -77.045132)
        currentMarker = mGoogleMap?.addMarker(MarkerOptions().position(initialLocation))
        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLng(initialLocation))
        mGoogleMap?.moveCamera(CameraUpdateFactory.zoomTo(10f))
    }

    private fun addTextWatchers() {
        val locationUpdateListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateLocation()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        textLatitud.addTextChangedListener(locationUpdateListener)
        textLongitud.addTextChangedListener(locationUpdateListener)
    }

    private fun updateLocation() {
        val latitud = textLatitud.text.toString().toDoubleOrNull()
        val longitud = textLongitud.text.toString().toDoubleOrNull()

        if (latitud != null && longitud != null && mGoogleMap != null) {
            val newLocation = LatLng(latitud, longitud)

            currentMarker?.remove()
            currentMarker = mGoogleMap?.addMarker(MarkerOptions().position(newLocation))
            mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLng(newLocation))
        }
    }
}
