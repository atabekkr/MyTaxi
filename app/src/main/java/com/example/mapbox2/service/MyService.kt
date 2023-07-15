package com.example.mapbox2.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.mapbox2.service.listener.MyLocationListener


class MyService : Service() {

    companion object {
        var locationListener: MyLocationListener? = null
    }

    private var locationManager: LocationManager? = null
    var locationChangeListener: LocationListener? = null

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        locationChangeListener = LocationListener {
            locationListener?.onChangeLocation(it.longitude, it.latitude)
        }

        if (locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)!!) {
            locationManager?.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 2, 1f, locationChangeListener!!
            )
        } else {
            Toast.makeText(this, "No Service Provider is available", Toast.LENGTH_SHORT).show()
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): Nothing? = null

}