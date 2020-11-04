package com.hardikPatel.dogproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hardikPatel.dogproject.network.DogData
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_layout.*

class Adapter(val breedClickListener : DogClickListener) : RecyclerView.Adapter<ItemHolder>(){
    var data: List<DogData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) =
        holder.bindItem(data[position], breedClickListener)

    override fun getItemCount(): Int {
        return data.size
    }
}

class ItemHolder(override val containerView : View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer{

    fun bindItem(breed : DogData, breedClickListener: DogClickListener){
        dogItem.text = breed.title
        containerView.setOnClickListener {
            breedClickListener.onClickBreed(breed)
        }
    }
}

class DogClickListener(private val listener: (breedData: DogData) -> Unit) {
    fun onClickBreed(breedData: DogData) = listener(breedData)
}