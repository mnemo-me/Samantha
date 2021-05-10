package com.mnemo.samantha.ui

import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.Repository

class MainActivityViewModel(val repository: Repository) : ViewModel(){

    fun isProfileCreated() = repository.checkProfile()

}