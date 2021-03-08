package com.eunidev.edlibrary

import android.Manifest
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.eunidev.edlibrary.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var gifView: EDGifView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        gifView = binding.gifView
//        gifView.setVideoUri(Uri.parse("android.resource://$packageName/${resources.getIdentifier("badi", "raw", packageName)}"))
//        binding.button1.setOnClickListener {
//            gifView.start()
//            Toast.makeText(this, gifView.getDruation().toString(), Toast.LENGTH_SHORT).show();
//        }
//
//        binding.button2.setOnClickListener {
//            Toast.makeText(this, gifView.getCurrentPosition().toString(), Toast.LENGTH_SHORT).show();
//            gifView.pause()
//        }

        val list = ArrayList<ContentModel.EDGifViewModel>()
        list.add(ContentModel.EDGifViewModel("1", 0))
        list.add(ContentModel.EDGifViewModel("2", 0))
        list.add(ContentModel.EDGifViewModel("3", 0))
        list.add(ContentModel.EDGifViewModel("4", 0))
        list.add(ContentModel.EDGifViewModel("5", 0))
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = RVAdapter1(this, list)

    }

    override fun onBackPressed() {
    }
}