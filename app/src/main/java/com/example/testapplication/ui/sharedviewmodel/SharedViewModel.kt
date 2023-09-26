package com.example.testapplication.ui.sharedviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapplication.room.repository.RepositoryEntity

class SharedViewModel:ViewModel() {
    var sharedData = MutableLiveData<RepositoryEntity>()
}


