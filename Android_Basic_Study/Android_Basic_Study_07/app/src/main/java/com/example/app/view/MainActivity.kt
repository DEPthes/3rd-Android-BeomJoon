package com.example.app.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.app.R
import com.example.app.databinding.ActivityMainBinding
import com.example.app.view.card.CardFragment
import com.example.app.view.home.HomeFragment
import com.example.app.view.home.HomeViewModel
import com.example.app.view.utils.UiState

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 초기 HomeFragment로 설정
        supportFragmentManager.beginTransaction()
            .add(binding.flMain.id, HomeFragment())
            .commit()

        // BottomNavigationView 아이템 클릭 리스너 설정
        binding.bn.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.random -> {
                    replaceFragment(CardFragment())
                    true
                }

                else -> false
            }
        }
    }

    // 프래그먼트 교체하는 함수
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.flMain.id, fragment)
            .commit()
    }
}