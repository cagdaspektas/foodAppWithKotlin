package com.example.kotlinfoodapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.kotlinfoodapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

//TODO 9.video izlenecek 8e bak geçmeden
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation=findViewById<BottomNavigationView>(R.id.btm_nav)
        val navController=Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(bottomNavigation,navController)
    }
}