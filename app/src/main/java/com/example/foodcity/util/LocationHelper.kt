
package com.example.foodcity.util


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


class LocationHelper(var activity: Activity, var locationManager: LocationManager) {
    private val INTERVAL = 4 * 1000 /* 4 secs */
    private val FAST_INTERVAL = 2000 /* 2 sec */
    private val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION )
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null
    private var locationRequest: LocationRequest? = null
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    private var permissionLocationGranted: Boolean = false

    init {
        if (fusedLocationClient == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        }
        createLocationRequest()
        createLocationCallBack()
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest!!.interval = INTERVAL.toLong()
        locationRequest!!.fastestInterval = FAST_INTERVAL.toLong()
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY


        // Create LocationSettingsRequest object using location request
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest!!)
        val locationSettingsRequest = builder.build()
        val settingsClient = LocationServices.getSettingsClient(activity)
        settingsClient.checkLocationSettings(locationSettingsRequest)
    }

    private fun createLocationCallBack() {
        if (locationCallback == null) {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    /*  if (locationResult != null) {
                          locationManager.onLocationChanged(locationResult.lastLocation)
                      }*/

                    if (locationResult != null) {
                        for (location in locationResult.locations) {
                            if (location != null) {
                                locationManager.onLocationChanged(location)
                                longitude = location.longitude
                                latitude = location.latitude
                            }
                        }
                    }
                }
            }
        }
    }

    fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this.activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this.activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this.activity, PERMISSIONS, 1234)
            return
        }

        fusedLocationClient!!.requestLocationUpdates(locationRequest, locationCallback, null)
        getLastKnownLocation()
    }

    private fun getLastKnownLocation() {
        if (fusedLocationClient == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.activity)
        }

        if (ActivityCompat.checkSelfPermission(
                this.activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this.activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient!!.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                locationManager.getLastKnownLocation(location)
                longitude = location.longitude
                latitude = location.latitude
            }
        }
    }

    fun stopLocationUpdates() {
        fusedLocationClient!!.removeLocationUpdates(locationCallback)
    }


    private fun isServicesOK(): Boolean {
        Log.d(activity.localClassName, "isServicesOK: checking google services version")
        val available =
            GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity)
        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(activity.localClassName, "isServicesOK: Google Play Services is working")
            return true
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Log.d(activity.localClassName, "isServicesOK: an error occured but we can fix it")
            val dialog = GoogleApiAvailability.getInstance()
                .getErrorDialog(activity, available, 901)
            dialog.show()
        } else {
            Toast.makeText(activity, "You can't make map requests", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    private fun isGPSEnabled(): Boolean {
        val manager =
            activity.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager
        if (!manager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
           // showGpsDialog().show()
            return false
        }
        return true
    }

    fun checkLocationPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        requestLocationPermission()
        return false
    }

    private fun requestLocationPermission() {
        Dexter.withContext(activity)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            //activity.recreate()
                            permissionLocationGranted = true
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    // Remember to invoke this method when the custom rationale is closed
                    // or just by default if you don't want to use any custom rationale.
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener {
                // Toast.makeText(activity,it.name, Toast.LENGTH_LONG).show()
            }
            .check()
    }

    fun checkMapServices(): Boolean {
        if (isServicesOK()) {
            if (isGPSEnabled()) {
                return true
            }
        }
        return false
    }

    fun getGrantedLocation(): Boolean {
        return permissionLocationGranted
    }




}