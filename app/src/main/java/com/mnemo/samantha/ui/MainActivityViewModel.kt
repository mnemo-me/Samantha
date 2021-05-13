package com.mnemo.samantha.ui

import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.repository.Repository
import javax.inject.Inject

class MainActivityViewModel : ViewModel(){

    @Inject
    lateinit var repository: Repository

    init {
        DaggerAppComponent.create().inject(this)
    }

    fun isProfileCreated() = repository.checkProfile()

}