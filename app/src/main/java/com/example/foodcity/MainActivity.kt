package com.example.foodcity

import android.content.Intent
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.foodcity.databinding.ActivityMainBinding
import com.example.foodcity.util.LocationHelper
import com.example.foodcity.util.MySharedPref
import com.example.foodcity.util.Status
import com.example.foodcity.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


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
}












































//class MainActivity : AppCompatActivity() {
//
//
//    private val TAG ="MainActivity"
//    lateinit var firebaseAuth: FirebaseAuth
//    lateinit var googleSignInClient: GoogleSignInClient
//    lateinit var viewModel: AuthViewModel
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        firebaseAuth = FirebaseAuth.getInstance() //initialization
//
//        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
//
//        viewModel.logIn(firebaseAuth, "araga997@gmail.com", "11")
//            .observe(this, {
//                when (it.status) {
//
//                    Status.LOADING -> {
//                        Log.e(TAG, "LOADING")
//                    }
//
//                    Status.SUCCESS -> {
//                        Log.e(TAG, "SUCCESS")
//                    }
//
//                    Status.ERROR -> {
//                        Log.e(TAG, "ERROR")
//                    }
//
//                }
//            })
//
//
//    }
//
//
//    loginByGoole()
//}
//
// private fun loginByGoole(){
//     val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//         .requestIdToken(getString(R.string.default_web_client_id))
//         .requestEmail()
//         .build()
//
//     googleSignInClient = GoogleSignIn.getClient(this,)
//
//
//
//
// }
//









































//private lateinit var signIn : Button
//private lateinit var auth: FirebaseAuth
//override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_main)
//    signIn = findViewById(R.id.btntvSignIn)
//    // Initialize Firebase Auth
//    auth = FirebaseAuth.getInstance()
//
//    // Configure Google Sign In
//    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestIdToken(getString(R.string.default_web_client_id))
//        .requestEmail()
//        .build()
//
//    var googleSignInClient = GoogleSignIn.getClient(this, gso)
//    signIn.setOnClickListener {
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, 50)
//
//    }
//}
//
//override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    super.onActivityResult(requestCode, resultCode, data)
//
//    // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//    if (requestCode == 50) {
//        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//        try {
//            // Google Sign In was successful, authenticate with Firebase
//            val account = task.getResult(ApiException::class.java)!!
////                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
//            firebaseAuthWithGoogle(account.idToken!!)
//        } catch (e: ApiException) {
//            // Google Sign In failed, update UI appropriately
////                Log.w(TAG, "Google sign in failed", e)
//        }
//    }
//}
//private fun firebaseAuthWithGoogle(idToken: String) {
//    val credential = GoogleAuthProvider.getCredential(idToken, null)
//    auth.signInWithCredential(credential)
//        .addOnCompleteListener(this) { task ->
//            if (task.isSuccessful) {
//                // Sign in success, update UI with the signed-in user's information
////                    Log.d(TAG, "signInWithCredential:success")
//                val user = auth.currentUser
//                Log.i("infoo",user!!.displayName.toString())
//                Log.i("infoo",user!!.email.toString())
//                Toast.makeText(this,"Sign in Successful\n"+user!!.displayName.toString(), Toast.LENGTH_LONG).show()
////                    updateUI(user)
//            } else {
//                // If sign in fails, display a message to the user.
////                    Log.w(TAG, "signInWithCredential:failure", task.exception)
////                    updateUI(null)
//            }
//        }
//}










//
//
//private lateinit var signIn : Button
//private lateinit var auth: FirebaseAuth
//override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_main)
//    signIn = findViewById(R.id.btntvSignIn)
//    // Initialize Firebase Auth
//    auth = FirebaseAuth.getInstance()
//
//    // Configure Google Sign In
//    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestIdToken(getString(R.string.default_web_client_id))
//        .requestEmail()
//        .build()
//
//    var googleSignInClient = GoogleSignIn.getClient(this, gso)
//    signIn.setOnClickListener {
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, 50)
//
//    }
//}
//
//override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    super.onActivityResult(requestCode, resultCode, data)
//
//    // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//    if (requestCode == 50) {
//        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//        try {
//            // Google Sign In was successful, authenticate with Firebase
//            val account = task.getResult(ApiException::class.java)!!
////                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
//            firebaseAuthWithGoogle(account.idToken!!)
//        } catch (e: ApiException) {
//            // Google Sign In failed, update UI appropriately
////                Log.w(TAG, "Google sign in failed", e)
//        }
//    }
//}
//private fun firebaseAuthWithGoogle(idToken: String) {
//    val credential = GoogleAuthProvider.getCredential(idToken, null)
//    auth.signInWithCredential(credential)
//        .addOnCompleteListener(this) { task ->
//            if (task.isSuccessful) {
//                // Sign in success, update UI with the signed-in user's information
////                    Log.d(TAG, "signInWithCredential:success")
//                val user = auth.currentUser
//                Log.i("infoo",user!!.displayName.toString())
//                Log.i("infoo",user!!.email.toString())
//                Toast.makeText(this,"Sign in Successful\n"+user!!.displayName.toString(), Toast.LENGTH_LONG).show()
////                    updateUI(user)
//            } else {
//                // If sign in fails, display a message to the user.
////                    Log.w(TAG, "signInWithCredential:failure", task.exception)
////                    updateUI(null)
//            }
//        }
//}