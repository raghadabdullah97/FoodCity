package com.example.foodcity.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodcity.MainActivity
import com.example.foodcity.R
import com.example.foodcity.databinding.FragmentSignInBinding
import com.example.foodcity.util.MySharedPref
import com.example.foodcity.util.Status
import com.example.foodcity.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import isEmailValid


class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private val TAG = "SignInFragment"
    lateinit var binding: FragmentSignInBinding
    lateinit var viewModel: AuthViewModel
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var pref: MySharedPref

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)
        (requireActivity() as MainActivity).setToolbarTitle(getString(R.string.sign_in))
        pref = MySharedPref(requireContext())
        isRememberMe()
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        firebaseAuth = FirebaseAuth.getInstance()


        //AsGuest :
        binding.apply {


            btnLoginAsGuest.setOnClickListener {
                val action =
                    SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                findNavController().navigate(action)
            }

            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (email.isEmpty()) {
                    etEmail.error = "Empty!"
                    return@setOnClickListener
                }

                if (password.isEmpty()) {
                    etPassword.error = "Empty!"
                    return@setOnClickListener
                }
                if (!isEmailValid(email)) {
                    etEmail.error = "Invalid email!"
                    return@setOnClickListener
                }


                viewModel.logIn(firebaseAuth, email, password).observe(viewLifecycleOwner, {
                    when (it.status) {
                        Status.LOADING -> {

                            Log.e(TAG, "LOADING: ")

                        }
                        Status.SUCCESS -> {
                            // store " UID " in Fav :
                            pref.setString("userId",it.data?.user?.uid)
                            if (rb.isChecked){
                                pref.setBoolean("isRegister",true)
                            }

                            pref.setString("email",it.data?.user?.email)
                            val action =
                                SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                            findNavController().navigate(action)

                            Log.e(TAG, "SUCCESS: ")
                        }
                        Status.ERROR -> {

                            Log.e(TAG, "ERROR: ")
                        }
                    }
                })
            }

            btnGoogle.setOnClickListener {
                loginByGoogle()
            }

            btnSignUp.setOnClickListener {
                val action = SignInFragmentDirections.actionSignInFragmentToRegisterFragment()
                findNavController().navigate(action)
            }
        }

    }

    private fun loginByGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)

    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.e(TAG, "${account}: ", )
//  Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.e(TAG, "Google sign in failed ${e.message}", e)
            }


        }

    private fun firebaseAuthWithGoogle(idToken: String) {
        viewModel.firebaseAuthWithGoogle(firebaseAuth, idToken)
            .observe(this, {
                when (it.status) {
                    Status.LOADING -> {
                        Log.e(TAG, "LOADING: ")

                    }
                    Status.SUCCESS -> {
                        Log.e(TAG, "SUCCESS: ")
                        // store " UID " in Fav :
                        pref.setString("userId",it.data?.user?.uid)
                        pref.setString("email",it.data?.user?.email)
                        if (binding.rb.isChecked){
                            pref.setBoolean("isRegister",true)
                        }
                        val action =
                            SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                        findNavController().navigate(action)

                    }
                    Status.ERROR -> {
                        Log.e(TAG, "ERROR: ")
                    }
                }
            })
    }

    private fun isRememberMe(){
        if (pref.getBoolean("isRegister")){
            val action =
                SignInFragmentDirections.actionSignInFragmentToHomeFragment()
            findNavController().navigate(action)
            return
        }
    }

}