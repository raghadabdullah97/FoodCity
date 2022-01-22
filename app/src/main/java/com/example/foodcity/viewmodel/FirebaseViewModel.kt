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

    //Show me all the cities:
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

    //Show me all the restaurantNearBy:
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
                    //"reversed"= Places from closest to farthest:
                emit(Resource.success(data.reversed()))
            } catch (e: Exception) {
                Log.e("TAG", "fetchResNearby: ${e.message}")
                emit(Resource.error(e.localizedMessage, null))
            }

        }
    }

    //Show me all the Offers:
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


    // RestaurantDetails fragment :
    fun fetchProductsByRetName(
        firestore: FirebaseFirestore,
        restaurantId : String?
    ): LiveData<Resource<List<Products>>> {
        return liveData {
            emit(Resource.loading(null))
            try {

                val data =
                    //".whereEqualTo"=filter from product as Restaurant special Id:
                    firestore.collection("Products").whereEqualTo("restaurantId",restaurantId).get().await().toObjects(Products::class.java)
                emit(Resource.success(data))
            } catch (e: Exception) {
                Log.e("TAG", "fetchCities: ${e.message}")
                emit(Resource.error(e.localizedMessage, null))
            }
        }
    }


    //used in "ProductFragment":
    //get The meal depends on the city, tow arg(cityName,catType):
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

    // get Restaurants by city name :
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


    //get "Restaurant data"
    fun fetchRestaurantByProduct(
        firestore: FirebaseFirestore,
        restaurantId: String,
    ): LiveData<Resource<Restaurants>> {
        return liveData {
            emit(Resource.loading(null))
            try {
        //return single object "id restaurant":
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
        //variables from file "model favorite ":
    fun addToFavorite(firestore: FirebaseFirestore,userId:String, product: Products){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val favorite = Favorite(userId,product)
                //"document("${product.id!!}_$userId")"= Each userId can be added to favourites
                firestore.collection("Favorite").document("${product.id!!}_$userId").set(favorite).await()

            } catch (ex: Exception) {
                Log.e("TAG", "addToFavoriteEx: ${ex.message}")
                ex.printStackTrace()
            }
        }

    }





    fun fetchFavoriteProducts(
        firestore: FirebaseFirestore,
        userId: String
    ): LiveData<Resource<List<Favorite>>> {
        return liveData {
            emit(Resource.loading(null))
            try {

                val data =
                    //don't get all data  just get my data fav :
                    firestore.collection("Favorite").whereEqualTo("userId",userId)
                        .get().await().toObjects(Favorite::class.java)

                emit(Resource.success(data))
            } catch (e: Exception) {
                Log.e("TAG", "fetchFavoriteProductsEx: ${e.message}")
                emit(Resource.error(e.localizedMessage, null))
            }

        }
    }







}
