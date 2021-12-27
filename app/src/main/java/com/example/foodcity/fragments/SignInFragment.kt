package com.example.foodcity.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodcity.MainActivity
import com.example.foodcity.R
import com.example.foodcity.databinding.FragmentSignInBinding
import com.example.foodcity.util.Status
import com.example.foodcity.util.isEmailValid
import com.example.foodcity.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

private const val ARG_PARAM1 = "param1"
class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private val TAG = "SignInFragment"
    lateinit var binding: FragmentSignInBinding
    lateinit var viewModel: AuthViewModel
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)
        (requireActivity() as MainActivity).setToolbarTitle(getString(R.string.sign_in))

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        firebaseAuth = FirebaseAuth.getInstance()
        binding.apply {

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
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
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
                    }
                    Status.ERROR -> {
                        Log.e(TAG, "ERROR: ")
                    }
                }
            })
    }

}