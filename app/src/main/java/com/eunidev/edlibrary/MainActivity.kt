package com.eunidev.edlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.eunidev.edlibrary.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val edsnack = EDLibrary.EDSnackbar.failed(this, binding.root, "Failed", Snackbar.LENGTH_SHORT)
        edsnack.setAction("Retry") {
            Toast.makeText(this, "hahhhhh!", Toast.LENGTH_SHORT).show();
        }
        binding.button1.setOnClickListener {
            edsnack.show()
        }

    }

    override fun onBackPressed() {
    }
}