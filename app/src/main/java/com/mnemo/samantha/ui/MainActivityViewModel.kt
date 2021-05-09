package com.mnemo.samantha.ui

import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.Repository

class MainActivityViewModel(repository: Repository) : ViewModel(){

    val isProfileCreated = repository.checkProfile()
}