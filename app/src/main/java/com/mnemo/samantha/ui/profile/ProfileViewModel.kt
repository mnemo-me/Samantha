package com.mnemo.samantha.ui.profile

import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.Repository

class ProfileViewModel(val repository: Repository) : ViewModel() {

    val master = repository.getMaster()

    val services = repository.getServiceList()
}