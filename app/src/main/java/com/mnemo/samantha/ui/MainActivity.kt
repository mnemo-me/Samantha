package com.mnemo.samantha.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.ActivityMainBinding
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.ui.create_profile.CreateProfileActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Bind View
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        // Create ViewModel and bind it to View
        val application = this.application

        val repository = Repository.getInstance(application)

        val viewModelFactory = MainActivityViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)


        // Setup Bottom Navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigation_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationBar.setupWithNavController(navController)


        // Create profile for new user
        CoroutineScope(Dispatchers.IO).launch{
            val isProfileCreated = viewModel.isProfileCreated()
            withContext(Dispatchers.Main){
                if (!isProfileCreated) createProfile()
            }
        }
    }


    // Start create profile activity
    private fun createProfile(){
        val intent = Intent(this, CreateProfileActivity::class.java)
        startActivity(intent)
    }
}