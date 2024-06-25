package com.example.app.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.app.R
import com.example.app.databinding.ActivityMainBinding
import com.example.app.view.card.CardFragment
import com.example.app.view.home.HomeFragment
import com.example.app.view.home.HomeViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 초기 HomeFragment로 설정
        supportFragmentManager.beginTransaction().add(binding.flMain.id, HomeFragment()).commit()

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

    // 프래그먼트 replace
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.flMain.id, fragment).commit()
    }

    fun replaceFragmentWithBackstack(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            .add(binding.flMain.id, fragment) // add를 사용하여 프래그먼트를 겹치도록 함
            .addToBackStack(null)
            .commit()
    }

    fun hideNavBar() {
        binding.bn.visibility = View.GONE
    }

    fun showNavBar() {
        binding.bn.visibility = View.VISIBLE
    }
}