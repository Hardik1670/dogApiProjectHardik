package com.hardikPatel.dogproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hardikPatel.dogproject.network.DogData
import com.hardikPatel.dogproject.network.ImageModel
import com.hardikPatel.dogproject.repository.DogRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImageViewModel(private val repository: DogRepository):ViewModel() {

    var imageLiveData = MutableLiveData<ImageModel>()

    fun getImage(dog: DogData){
        GlobalScope.launch {
            imageLiveData.postValue(repository.getImage(dog))
        }
    }
}