package com.android.aop.part2.locationsearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.aop.part2.locationsearch.databinding.ActivityMapBinding
import com.android.aop.part2.locationsearch.model.entity.SearchResultEntity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val binding by lazy { ActivityMapBinding.inflate(layoutInflater) }

    private lateinit var searchResult: SearchResultEntity
    private lateinit var map: GoogleMap

    private var currentSelectMarker: Marker? = null

    companion object {
        const val SEARCH_RESULT_EXTRA_KEY = "SearchResult"
        const val PERMISSION_REQUEST_CODE = 1
        const val CAMERA_ZOOM_LEVEL = 17f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (::searchResult.isInitialized.not()) {
            intent?.let {
                searchResult = intent.getParcelableExtra(SEARCH_RESULT_EXTRA_KEY)
                    ?: throw Exception("데이터가 존재하지 않습니다.")
                setupGoogleMap()
            }
        }

    }

    private fun setupGoogleMap() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        currentSelectMarker = setupMarker(searchResult)

        currentSelectMarker?.showInfoWindow()
    }

    private fun setupMarker(searchResult: SearchResultEntity): Marker? {
        val positionLatLng = LatLng(
            searchResult.locationLatLng.latitude.toDouble(),
            searchResult.locationLatLng.longitude.toDouble()
        )
        val markerOption = MarkerOptions().apply {
            position(positionLatLng)
            title(searchResult.name)
            snippet(searchResult.fullAdress)
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng, CAMERA_ZOOM_LEVEL))

        return map.addMarker(markerOption)

    }


}


