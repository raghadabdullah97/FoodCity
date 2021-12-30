package com.example.foodcity.fragments
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodcity.MainActivity
import com.example.foodcity.R
import com.example.foodcity.databinding.FragmentRegisterBinding
import com.example.foodcity.util.Status
import com.example.foodcity.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import isEmailValid


class RegisterFragment : Fragment(R.layout.fragment_register) {
   private val TAG = "RegisterFragment"
    lateinit var binding: FragmentRegisterBinding
    lateinit var viewModel: AuthViewModel
    lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding = FragmentRegisterBinding.bind(view)
      (requireActivity() as MainActivity).setToolbarTitle(getString(R.string.register))

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
       firebaseAuth = FirebaseAuth.getInstance()
      binding.apply {


            btnRegister.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val confirmPassword = etPasswordConfirm.text.toString()

                if (email.isEmpty()) {
                    etEmail.error = "Empty!"
                    return@setOnClickListener
                }

                if (password.isEmpty()) {
                    etPassword.error = "Empty!"
                    return@setOnClickListener
                }

                if (confirmPassword.isEmpty()) {
                    etPasswordConfirm.error = "Empty!"
                    return@setOnClickListener
                }

                if (!isEmailValid(email)) {
                    etEmail.error = "Invalid email!"
                    return@setOnClickListener
                }

                if (password != confirmPassword) {
                    etPassword.error = "Invalid"
                    etPasswordConfirm.error = "Invalid"
                    return@setOnClickListener
                }

                viewModel.signup(firebaseAuth, email, password).observe(viewLifecycleOwner, {
                    when (it.status) {
                        Status.LOADING -> {

                            Log.e(TAG, "LOADING: ")

                        }
                        Status.SUCCESS -> {
                            val action =
                                RegisterFragmentDirections.actionRegisterFragmentToSignInFragment()
                            findNavController().navigate(action)

                            Log.e(TAG, "SUCCESS: ")
                        }
                        Status.ERROR -> {

                            Log.e(TAG, "ERROR: ")
                        }
                    }
                })
            }
        }
    }
}