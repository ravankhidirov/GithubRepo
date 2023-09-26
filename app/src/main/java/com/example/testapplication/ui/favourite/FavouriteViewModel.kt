package com.example.testapplication.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapplication.room.repository.RepositoryDao
import com.example.testapplication.room.repository.RepositoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel: ViewModel() {
    private var favs = MutableLiveData<List<RepositoryEntity>>()

    fun getFaves(favDao: RepositoryDao) {
        CoroutineScope(Dispatchers.Default).launch {
            val result = favDao.getAll()
            favs.postValue(result)
        }
    }

    fun observeFaves(): LiveData<List<RepositoryEntity>> {
        return favs
    }
}