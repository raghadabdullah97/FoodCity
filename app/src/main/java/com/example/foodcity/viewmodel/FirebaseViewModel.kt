package com.example.foodcity.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodcity.model.Cities
import com.example.foodcity.model.Offers
import com.example.foodcity.model.Restaurants
import com.example.foodcity.util.MySharedPref
import com.example.foodcity.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class FirebaseViewModel : ViewModel() {


    fun fetchCities(
        firestore: FirebaseFirestore,
    ): LiveData<Resource<List<Cities>>> {
        return liveData {
            emit(Resource.loading(null))
            try {

                val data =
                    firestore.collection("Cities").get().await().toObjects(Cities::class.java)
                emit(Resource.success(data))
            } catch (e: Exception) {
                Log.e("TAG", "fetchCities: ${e.message}")
                emit(Resource.error(e.localizedMessage, null))
            }
        }
    }

    fun fetchResNearby(
        context: Context,
        firestore: FirebaseFirestore,
    ): LiveData<Resource<List<Restaurants>>> {
        val pref = MySharedPref(context)
        return liveData {
            emit(Resource.loading(null))
            try {

                val data = firestore.collection("Restaurants").get().await()
                    .toObjects(Restaurants::class.java)

                emit(Resource.success(data.reversed()))
            } catch (e: Exception) {
                Log.e("TAG", "fetchResNearby: ${e.message}")
                emit(Resource.error(e.localizedMessage, null))
            }

        }
    }

    fun fetchOffers(
        firestore: FirebaseFirestore,
    ): LiveData<Resource<List<Offers>>> {
        return liveData {
            emit(Resource.loading(null))
            try {

                val data =
                    firestore.collection("Offers").get().await().toObjects(Offers::class.java)
                emit(Resource.success(data))
            } catch (e: Exception) {
                Log.e("TAG", "fetchCities: ${e.message}")
                emit(Resource.error(e.localizedMessage, null))
            }
        }
    }



}
