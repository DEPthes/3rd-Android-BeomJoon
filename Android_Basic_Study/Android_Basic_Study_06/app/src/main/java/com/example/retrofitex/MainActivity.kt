package com.example.retrofitex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitex.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieRvAdapter: MovieRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnView.setOnClickListener {
            val selectedDate = binding.etSelectData.text.toString()
            binding.tvDate.text = "선택한 날짜 : " + selectedDate

            lifecycleScope.launch {
                val movieList = BoxOfficeRepositoryImpl().getMovie(selectedDate)

                // recyclerview adapter
                movieRvAdapter = MovieRvAdapter()

                // set up recyclerview
                binding.rvMovieList.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    adapter = movieRvAdapter
                }
                // set data
                movieRvAdapter.setData(movieList)

            }

        }

//        lifecycleScope.launch {
//            val movieList = BoxOfficeRepositoryImpl().getMovie("20240510")
//
//            // recyclerview adapter
//            movieRvAdapter = MovieRvAdapter()
//
//            // set up recyclerview
//            binding.rvMovieList.apply {
//                setHasFixedSize(true)
//                layoutManager = LinearLayoutManager(context)
//                adapter = movieRvAdapter
//            }
//            // set data
//            movieRvAdapter.setData(movieList)
//
//        }
    }
}