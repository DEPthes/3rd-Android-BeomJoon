package com.example.retrofitex

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitex.data.DailyBoxOfficeList
import com.example.retrofitex.databinding.ActivityMainBinding
import com.example.retrofitex.presentation.boxoffice.BoxOfficeRepositoryImpl
import com.example.retrofitex.presentation.dialog.MovieAddDialog
import com.example.retrofitex.presentation.dialog.MovieAddDialogInterface
import com.example.retrofitex.presentation.movielist.MovieRvAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MovieAddDialogInterface {
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

        binding.btnMovieAdd.setOnClickListener {
            val myCustomDialog = MovieAddDialog(this, this)
            myCustomDialog.show()
        }
    }

    override fun onSendBtnClicked(newItem: DailyBoxOfficeList) {
        movieRvAdapter.addItem(newItem)
        Toast.makeText(this, "영화 추가 완료", Toast.LENGTH_SHORT).show()
    }

    override fun onCancelBtnClicked() {
        Toast.makeText(this, "취소 버튼 클릭", Toast.LENGTH_SHORT).show()
    }
}