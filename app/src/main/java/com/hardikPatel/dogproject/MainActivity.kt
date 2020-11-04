package com.hardikPatel.dogproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hardikPatel.dogproject.repository.DogRepository
import com.hardikPatel.dogproject.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.StringBuilder

//Name :: Hardik Patel
//Student ID::A00218526
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    private val adapter = Adapter(DogClickListener {
        Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ImageActivity::class.java)
        intent.putExtra(Constants.DOG_DATA, it)
        startActivity(intent)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        recyclerViewFun()
        initObserver()

    }

    private fun initObserver() {
        viewModel.breeds.observe(this, Observer {
            it?.let {
                adapter.data = it.parseData()
                adapter.notifyDataSetChanged()
            }
        })
        viewModel.getBreeds()

    }

    private fun initViewModel() {
        val apiClient = (application as DogProject).serviceLocator.apiClient
        viewModel = ViewModelProvider(
            this,
            viewModelFactory { MainViewModel(DogRepository(apiClient)) }
        ).get(MainViewModel::class.java)
    }

    private fun recyclerViewFun() {
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }
}