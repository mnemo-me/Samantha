package com.mnemo.samantha.ui.clients.client_edit

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ClientEditViewModelFactory(val clientId: Long, val appointmentId: Long, val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientEditVewModel::class.java)){
            return ClientEditVewModel(clientId, appointmentId, context) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}