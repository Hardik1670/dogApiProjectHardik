package com.hardikPatel.dogproject.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hardikPatel.dogproject.network.DataModel
import com.hardikPatel.dogproject.network.DogData
import com.hardikPatel.dogproject.network.DogJson
import com.hardikPatel.dogproject.network.ImageModel

class DogRepository (private val apiClient: DogJson){
    private val dogBreeds = MutableLiveData<DataModel>()
    private val imageResponse = MutableLiveData<ImageModel>()
    suspend fun getBreeds(): DataModel{
        val response = apiClient.breeds()
        return response
    }

    suspend fun getImage(dog: DogData): ImageModel{
        return if(dog.subBreed.isNotEmpty()){
            val response = apiClient.getRandomImage(dog.breed, dog.subBreed)
            response
        }else {
            val response = apiClient.getRandomImage(dog.breed)
            response
        }
    }


}