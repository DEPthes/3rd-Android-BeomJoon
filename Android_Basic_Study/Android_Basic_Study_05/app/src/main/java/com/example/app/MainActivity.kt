package com.example.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.data.Item
import com.example.app.databinding.ActivityMainBinding
import com.example.app.presentation.ItemRvAdapter

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
// 데이터 추가
        itemList.add(Item("https://cdn.dummyjson.com/product-images/7/1.jpg", "옷", 120))
        itemList.add(Item("https://cdn.dummyjson.com/product-images/7/thumbnail.jpg", "바지", 9999))

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

        binding.btnFavoriteList.setOnClickListener {
            val intent = Intent(this, FavoriteListActivity::class.java)
            startActivity(intent)
        }
    }
}
