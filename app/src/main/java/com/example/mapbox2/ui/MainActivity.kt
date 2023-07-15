package com.example.mapbox2.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.mapbox2.service.listener.MyLocationListener
import com.example.mapbox2.service.MyService
import com.example.mapbox2.R
import com.example.mapbox2.databinding.ActivityMainBinding
import com.example.mapbox2.presentation.impl.MainViewModelImpl
import com.example.mapbox2.utils.bitmapFromDrawableRes
import com.example.mapbox2.utils.toast
import com.mapbox.geojson.Point.fromLngLat
import com.mapbox.maps.*
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), MyLocationListener {

    private val viewModel by viewModel<MainViewModelImpl>()
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        MyService.locationListener = this

        initListeners()
        checkPermission()
        initObservers()
    }


    private fun initListeners(){

        lifecycleScope.launchWhenResumed {
            viewModel.getLastLocation()
        }

        binding.btnNavigate.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                viewModel.getLastLocation()
            }
        }


    }

    private fun onMapReady(longitude: Double, latitude: Double) {
        binding.mapView.getMapboxMap().setCamera(
            CameraOptions.Builder().center(fromLngLat(longitude, latitude)).pitch(0.0)
                .bearing(-17.8).zoom(15.0).build()
        )

        binding.mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            binding.mapView.annotations.cleanup()
            addAnnotationToMap(longitude, latitude)
        }
    }

    private fun addAnnotationToMap(longitude: Double, latitude: Double) {
        bitmapFromDrawableRes(
            this@MainActivity, R.drawable.yellow_taxi
        )?.let {
            val annotationApi = binding.mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager()
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(fromLngLat(longitude, latitude))
                .withIconImage(it)
            pointAnnotationManager.create(pointAnnotationOptions)
        }
    }


    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 100
            )
        } else {
            startService(Intent(this, MyService::class.java))
        }
    }

    private fun initObservers() {
        viewModel.getLastLocationFlow.onEach {
            onMapReady(it.longitude, it.latitude)
        }.launchIn(lifecycleScope)

        viewModel.messageFlow.onEach {
            toast(it)
        }.launchIn(lifecycleScope)

    }

    override fun onChangeLocation(longitude: Double, latitude: Double) {
        lifecycleScope.launchWhenResumed {
            viewModel.addLocation(longitude, latitude)
            toast("$longitude")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 100) {
            checkPermission()
        } else {
            Toast.makeText(this, "NO GPS PERMISSIONS", Toast.LENGTH_SHORT).show()
        }
    }

    //haw menda ozgerrimgo
    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, MyService::class.java))
    }
}