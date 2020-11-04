package com.hardikPatel.dogproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.hardikPatel.dogproject.network.DogData
import com.hardikPatel.dogproject.repository.DogRepository
import com.hardikPatel.dogproject.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.activity_image.*


class ImageActivity : AppCompatActivity() {
    lateinit var dogData: DogData
    lateinit var viewModel: ImageViewModel
    lateinit var  url: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dogData = intent.getSerializableExtra(Constants.DOG_DATA) as DogData
        initViewModel()
        initObservers()
    }

    private fun initObservers() {
        viewModel.imageLiveData.observe(this, Observer {
            it?.let {
                Glide.with(this).load(it.message).into(image)
                url = it.message
            }
        })
        viewModel.getImage(dogData)

    }

    private fun initViewModel() {
        val apiClient = (application as DogProject).serviceLocator.apiClient
        viewModel = ViewModelProvider(
            this,
            viewModelFactory { ImageViewModel(DogRepository(apiClient)) }

        ).get(ImageViewModel::class.java)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if(item.itemId == R.id.share) {
            val shareIntent= Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share Image File")
            shareIntent.putExtra(Intent.EXTRA_TEXT, url)
            startActivity(Intent.createChooser(shareIntent, "Share Image via Chooser"))

        } else if(item.itemId == R.id.browse) {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }
}