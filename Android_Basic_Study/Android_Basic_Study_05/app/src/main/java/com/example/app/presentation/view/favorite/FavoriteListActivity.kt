package com.example.app.presentation.view.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.data.model.Item
import com.example.app.databinding.ActivityFavoriteListBinding

class FavoriteListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteListBinding
    private val favoriteList = ArrayList<Item>()
    private lateinit var favoriteItemRvAdapter: FavoriteItemRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 데이터 추가
        favoriteList.add(Item("https://cdn.dummyjson.com/product-images/7/1.jpg", "옷", 120))
        favoriteList.add(Item("https://cdn.dummyjson.com/product-images/7/thumbnail.jpg", "바지", 9999))

        // recyclerview adapter
        favoriteItemRvAdapter = FavoriteItemRvAdapter()

        // set up recyclerview
        binding.rvFavoriteList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FavoriteListActivity)
            adapter = favoriteItemRvAdapter
        }
        // set data
        favoriteItemRvAdapter.setData(favoriteList)

    }
}