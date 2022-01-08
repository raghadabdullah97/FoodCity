package com.example.foodcity.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodcity.model.*
import com.example.foodcity.util.MySharedPref
import com.example.foodcity.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// data from Firebase web

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



    fun fetchProductsByRetName(
        firestore: FirebaseFirestore,
        restaurantId : String?
    ): LiveData<Resource<List<Products>>> {
        return liveData {
            emit(Resource.loading(null))
            try {

                val data =
                    firestore.collection("Products").whereEqualTo("restaurantId",restaurantId).get().await().toObjects(Products::class.java)
                emit(Resource.success(data))
            } catch (e: Exception) {
                Log.e("TAG", "fetchCities: ${e.message}")
                emit(Resource.error(e.localizedMessage, null))
            }
        }
    }




    fun fetchProductsCityName(
        firestore: FirebaseFirestore,
        cityName: String,
        catType: String,
    ): LiveData<Resource<List<Products>>> {
        return liveData {
            emit(Resource.loading(null))
            try {

                val productCat = ProductCategory(title = catType)
                val data =
                    firestore.collection("Products")
                        .whereEqualTo("cityName", cityName)
                        .whereEqualTo("catType", productCat)
                        .get().await().toObjects(Products::class.java)

                emit(Resource.success(data))
            } catch (e: Exception) {
                Log.e("TAG", "fetchProductsCityName: ${e.message}")
                emit(Resource.error(e.localizedMessage, null))
            }

        }
    }


    fun fetchRestaurantsByCityName(
        firestore: FirebaseFirestore,
        cityName: String,
    ): LiveData<Resource<List<Restaurants>>> {
        return liveData {
            emit(Resource.loading(null))
            try {

                val data =
                    firestore.collection("Restaurants")
                        .whereEqualTo("cityName", cityName)
                        .get().await().toObjects(Restaurants::class.java)

                emit(Resource.success(data))
            } catch (e: Exception) {
                Log.e("TAG", "fetchRestaurantsByCityName: ${e.message}")
                emit(Resource.error(e.localizedMessage, null))
            }

        }
    }



    fun fetchRestaurantByProduct(
        firestore: FirebaseFirestore,
        restaurantId: String,
    ): LiveData<Resource<Restaurants>> {
        return liveData {
            emit(Resource.loading(null))
            try {

                val data =
                    firestore.collection("Restaurants").document(restaurantId)
                        .get().await().toObject(Restaurants::class.java)

                emit(Resource.success(data))
            } catch (e: Exception) {
                Log.e("TAG", "fetchRestaurantByProduct: ${e.message}")
                emit(Resource.error(e.localizedMessage, null))
            }

        }
    }

//
//    fun addToFavorite(
//        firestore: FirebaseFirestore,
//        userId: String,
//        products: Products
//    ) {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val favorite = Favorite(userId, products)
//                firestore.collection("Favorite").document(products.id!!).set(favorite).await()
//            } catch (e: Exception) {
//                Log.e("TAG", "addToFavoriteEx: ${e.message}")
//            }
//        }
//
//    }
//






}
