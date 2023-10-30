package ie.setu.swimspot.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso

import ie.setu.swimspot.databinding.ActivitySwimspotMapsBinding
import ie.setu.swimspot.databinding.ContentSwimspotMapsBinding
import ie.setu.swimspot.main.MainApp


class SwimspotMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivitySwimspotMapsBinding
    private lateinit var contentBinding: ContentSwimspotMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MainApp

        binding = ActivitySwimspotMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentSwimspotMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)

        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }

    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        app.swimspots.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.name).position(loc)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
            map.setOnMarkerClickListener(this)
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {

        val tag = marker.tag as Long
        val swimspot = app.swimspots.findById(tag)
        contentBinding.currentName.text = swimspot!!.name
        contentBinding.currentCategorey.text = swimspot.categorey
        Picasso.get().load(swimspot.photo).into(contentBinding.currentPhoto)
        return false
    }
}