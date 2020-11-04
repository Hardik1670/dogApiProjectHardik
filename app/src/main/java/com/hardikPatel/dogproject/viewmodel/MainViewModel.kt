package com.hardikPatel.dogproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hardikPatel.dogproject.network.DataModel
import com.hardikPatel.dogproject.network.DogJson
import com.hardikPatel.dogproject.repository.DogRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DogRepository): ViewModel() {

     var breeds = MutableLiveData<DataModel>()



    fun getBreeds(){
        GlobalScope.launch {
            val response = repository.getBreeds()
            breeds.postValue(response)
        }
    }


}