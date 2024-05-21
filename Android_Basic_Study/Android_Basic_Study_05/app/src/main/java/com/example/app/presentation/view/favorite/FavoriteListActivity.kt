package com.example.app.presentation.view.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.databinding.ActivityFavoriteListBinding
import com.example.app.presentation.MainActivity
import com.example.app.presentation.utils.UiState

class FavoriteListActivity : AppCompatActivity() {
    private val favoriteItemViewModel: FavoriteItemViewModel by viewModels()
    private lateinit var binding: ActivityFavoriteListBinding
    private lateinit var favoriteItemRvAdapter: FavoriteItemRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observer()


        // recyclerview adapter
        favoriteItemRvAdapter = FavoriteItemRvAdapter()

        // set up recyclerview
        binding.rvFavoriteList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FavoriteListActivity)
            adapter = favoriteItemRvAdapter
        }
        // set data
        favoriteItemViewModel.fetchData(this)

        binding.ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observer() {
        favoriteItemViewModel.uiState.observe(this) {
            when (it) {
                is UiState.Failure -> {
                    Log.d("TAG", it.error.toString())
                    Toast.makeText(applicationContext, it.error.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                is UiState.Loading -> {}
                is UiState.Success -> {
                    favoriteItemRvAdapter.setData(it.data)
                }

                else -> {}
            }
        }
    }
}