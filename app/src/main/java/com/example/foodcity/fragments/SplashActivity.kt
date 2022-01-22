package com.example.foodcity.fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.foodcity.MainActivity
import com.example.foodcity.R
import com.example.foodcity.databinding.FragmentHomeBinding

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({

            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },2500);


    }
}