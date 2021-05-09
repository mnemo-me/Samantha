package com.mnemo.samantha.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.ActivityMainBinding
import com.mnemo.samantha.ui.create_profile.CreateProfileActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigation_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationBar.setupWithNavController(navController)

        createProfile()
    }


    // Start create profile activity
    fun createProfile(){
        val intent = Intent(this, CreateProfileActivity::class.java)
        startActivity(intent)
    }
}