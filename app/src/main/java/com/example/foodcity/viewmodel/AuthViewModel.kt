package com.example.foodcity.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodcity.util.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import java.lang.Exception



class AuthViewModel : ViewModel() {


    fun logIn(
        firebaseAuth: FirebaseAuth,
        email: String,
        password: String
    ): LiveData<Resource<AuthResult>> {
        return liveData {
            emit(Resource.loading(null))
            try {
                val data = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                emit(Resource.success(data))
            } catch (e: Exception) {
                emit(Resource.error(e.localizedMessage, null))
            }
        }

    }


    fun signup(
        firebaseAuth: FirebaseAuth,
        email: String,
        password: String
    ): LiveData<Resource<AuthResult>> {
        return liveData {
            emit(Resource.loading(null))
            try {
                val data = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                emit(Resource.success(data))
            } catch (e: Exception) {
                Log.e("TAG", "signup: ${e.message}")
                emit(Resource.error(e.localizedMessage, null))
            }
        }

    }


    fun firebaseAuthWithGoogle(
        firebaseAuth: FirebaseAuth,
        idToken: String
    ): LiveData<Resource<AuthResult>> {
        return liveData {
            emit(Resource.loading(null))
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                val data = firebaseAuth.signInWithCredential(credential).await()
                emit(Resource.success(data))
            } catch (e: Exception) {
                Log.e("TAG", "signup: ${e.message}")
                emit(Resource.error(e.localizedMessage, null))
            }

        }
    }


}