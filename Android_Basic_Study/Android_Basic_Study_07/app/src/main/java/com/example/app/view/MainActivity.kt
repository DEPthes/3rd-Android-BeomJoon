package com.example.app.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.app.databinding.ActivityMainBinding
import com.example.app.view.home.HomeFragment
import com.example.app.view.home.HomeViewModel
import com.example.app.view.utils.UiState

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(binding.flMain.id, HomeFragment())
            .commit()
    }
}