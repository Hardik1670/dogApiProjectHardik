package com.hardikPatel.dogproject.network


import retrofit2.http.GET
import retrofit2.http.Path

interface DogJson {
    @GET("/api/breeds/list/all")
    suspend fun breeds(): DataModel

    @GET("/api/breed/{breed_name}/{sub_breed_name}/images/random")
    suspend fun getRandomImage(
        @Path("breed_name") breedName: String,
        @Path("sub_breed_name") subBreed: String
    ): ImageModel


    @GET("/api/breed/{breed_name}/images/random")
    suspend fun getRandomImage(
        @Path("breed_name") breedName: String
    ): ImageModel
}