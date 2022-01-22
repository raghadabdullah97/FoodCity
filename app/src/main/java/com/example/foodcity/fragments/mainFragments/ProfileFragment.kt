package com.example.foodcity.fragments.mainFragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodcity.MainActivity
import com.example.foodcity.R
import com.example.foodcity.adapters.NearbyAdapter
import com.example.foodcity.databinding.FragmentHomeBinding
import com.example.foodcity.databinding.FragmentPrfileBinding
import com.example.foodcity.util.MySharedPref
import com.example.foodcity.util.Status
import com.example.foodcity.viewmodel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment(R.layout.fragment_prfile) {

    lateinit var binding: FragmentPrfileBinding
    lateinit var firebaseDb: FirebaseFirestore //From website firebaseDb
    lateinit var viewModel: FirebaseViewModel
    lateinit var pref: MySharedPref
    lateinit var firebaseAuth: FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPrfileBinding.bind(view)
        //ToolPar Title:
        (requireActivity() as MainActivity).setToolbarTitle(getString(R.string.profile))
        pref = MySharedPref(requireContext())
        firebaseDb = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        viewModel = ViewModelProvider(this)[FirebaseViewModel::class.java]
    //concatenate with"getString(R.string.your_email_is)" and display "Email":
        binding.tvProfile.text =
            "${getString(R.string.your_email_is)} \n ${pref.getString("email")}"

        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            // store " UID " in Fav , "clearByKey" from MySharedPref because remove "id and email":
            pref.clearByKey("userId")
            pref.clearByKey("email")
            //"false " because of logging out :
            pref.setBoolean("isRegister", false)
            val action = ProfileFragmentDirections.actionProfileFragmentToSignInFragment()
            findNavController().navigate(action)
        }

    }


}


//private fun fetchCNearby() {
//    viewModel.fetchResNearby(requireContext(),firebaseDb).observe(viewLifecycleOwner, {
//        when (it.status) {
//            Status.LOADING -> {
//
//                Log.e(TAG, "LOADING: ")
//
//            }
//            Status.SUCCESS -> {
//                it.data?.let { res ->
//                    val adapter = NearbyAdapter(res)
//                    binding.rvRestuarant.adapter = adapter
//                    adapter.onItemClick = {
//                        val action =
//                            HomeFragmentDirections.actionHomeFragmentToRestaurantDetailsFragment(
//                                it
//                            )
//                        findNavController().navigate(action)
//                    }
//                }
//                Log.e(TAG, "SUCCESS: ")
//            }
//            Status.ERROR -> {
//
//                Log.e(TAG, "ERROR: ")
//            }
//        }
//    })
//}
