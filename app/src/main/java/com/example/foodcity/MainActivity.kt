package com.example.foodcity


import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodcity.databinding.ActivityMainBinding
import com.example.foodcity.util.*
import com.example.foodcity.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var navController: NavController
    lateinit var viewModel: AuthViewModel
    lateinit var pref: MySharedPref
    lateinit var locationHelper: LocationHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        pref = MySharedPref(this)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentMain) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.bottomNav.setupWithNavController(navController)
        hideBottomBar()
        getCurrentUserLocation()

    }

    private fun getCurrentUserLocation() {
        locationHelper = LocationHelper(this, object : LocationManager {
            override fun onLocationChanged(location: Location?) {
                //   Log.e(TAG, "onLocationChanged: ${location?.latitude},,, ${location?.longitude}", )
                saveLocationInPref(location)
            }

            override fun getLastKnownLocation(location: Location?) {
                saveLocationInPref(location)
            }
        })
    }

    private fun saveLocationInPref(location: Location?) {
        if (location != null) {
            pref.setDouble("lat", location.latitude)
            pref.setDouble("lng", location.longitude)
        }
    }

    override fun onStop() {
        super.onStop()
        locationHelper.stopLocationUpdates()

    }

    override fun onResume() {
        super.onResume()

        if (locationHelper.checkLocationPermissions()) {
            if (locationHelper.checkMapServices()) {
                locationHelper.startLocationUpdates()

            }
        }
    }

    fun setToolbarTitle(title: String) {
        binding.toolbar.tvToolbarTitle.text = title
    }

    private fun hideBottomBar() {
        navController.addOnDestinationChangedListener { controller, destination, argument ->
            when (destination.id) {
                R.id.registerFragment, R.id.signInFragment -> {
                    binding.bottomNav.gone()
                    binding.toolbar.root.gone()
                }else->{
                binding.bottomNav.visible()
                binding.toolbar.root.visible()
            }

            }


        }
    }
}