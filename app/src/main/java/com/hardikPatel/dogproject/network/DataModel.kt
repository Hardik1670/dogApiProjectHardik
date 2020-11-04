package com.hardikPatel.dogproject.network

import com.squareup.moshi.JsonClass
import java.io.Serializable
import java.util.*

@JsonClass(generateAdapter = true)
data class DataModel(
    val message: Map<String, List<String>>
) {
    fun parseData(): List<DogData> {
        val result = mutableListOf<DogData>()

        for (entry in message) {
            if (entry.value.isNullOrEmpty()) {
                result.add(DogData(entry.key))
                continue
            }

            entry.value.forEach {
                result.add(DogData(entry.key, it))
            }
        }

        result.sort()
        return result
    }
}

data class DogData(
    val breed: String,
    val subBreed: String = ""
) : Comparable<DogData> , Serializable{
    val title: String get() = if (subBreed.isEmpty()) breed.capitalize(Locale.CANADA)
    else "${subBreed.capitalize(Locale.CANADA)} ${breed.capitalize(Locale.CANADA)}"

    override fun compareTo(other: DogData): Int {
        val compareBreed = breed.compareTo(other.breed)
        return if (compareBreed == 0) title.compareTo(other.title) else compareBreed
    }
}