package com.example.app.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.presentation.view.favorite.FavoriteListActivity
import com.example.app.data.model.Item
import com.example.app.databinding.ActivityMainBinding
import com.example.app.domain.model.item.ItemEntity
import com.example.app.presentation.utils.UiState
import com.example.app.presentation.view.Item.ItemRvAdapter
import com.example.app.presentation.view.Item.ItemViewModel

class MainActivity : AppCompatActivity() {
    private val itemViewModel: ItemViewModel by viewModels()
    private var itemList = listOf<ItemEntity>()
    private lateinit var itemRvAdapter: ItemRvAdapter
    private lateinit var binding: ActivityMainBinding
    //    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observer()


        binding.btnSearchItem.setOnClickListener {
            val searchItem = binding.etSearchItem.text.toString()

            // recyclerview adapter
            itemRvAdapter = ItemRvAdapter()

            // set up recyclerview
            binding.rvItemList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = itemRvAdapter
            }
            // set data
            itemViewModel.fetchData(searchItem)
        }

        binding.btnFavoriteList.setOnClickListener {
            val intent = Intent(this, FavoriteListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observer() {
        itemViewModel.uiState.observe(this) {
            when (it) {
                is UiState.Failure -> {
                    Log.d("TAG", "API 호출 실패")
                    Toast.makeText(applicationContext, "API 호출 실패", Toast.LENGTH_SHORT).show()
                }

                is UiState.Loading -> {}
                is UiState.Success -> {
                    Log.d("TAG", "API 호출성공")
                    itemRvAdapter.setData(it.data)
                }

                else -> {}
            }
        }
    }

}
