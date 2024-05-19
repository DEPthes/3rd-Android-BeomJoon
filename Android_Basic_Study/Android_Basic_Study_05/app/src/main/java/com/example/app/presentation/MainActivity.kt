package com.example.app.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.presentation.favorite.FavoriteListActivity
import com.example.app.data.model.Item
import com.example.app.databinding.ActivityMainBinding
import com.example.app.data.repository.ItemRepositoryImpl
import com.example.app.presentation.Item.ItemRvAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var itemList = ArrayList<Item>()
    private lateinit var itemRvAdapter: ItemRvAdapter
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //    private lateinit var db: AppDatabase

//    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSearchItem.setOnClickListener {
            val searchItem = binding.etSearchItem.text.toString()
            lifecycleScope.launch {
                val movieList = ItemRepositoryImpl().getItem(searchItem)

                // recyclerview adapter
                itemRvAdapter = ItemRvAdapter()

                // set up recyclerview
                binding.rvItemList.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = itemRvAdapter
                }
                // set data
                itemRvAdapter.setData(itemList)

            }
        }

// 데이터 추가
        itemList.add(Item("https://cdn.dummyjson.com/product-images/7/1.jpg", "옷", 120))
        itemList.add(Item("https://cdn.dummyjson.com/product-images/7/thumbnail.jpg", "바지", 9999))


        binding.btnFavoriteList.setOnClickListener {
            val intent = Intent(this, FavoriteListActivity::class.java)
            startActivity(intent)
        }
    }
}
