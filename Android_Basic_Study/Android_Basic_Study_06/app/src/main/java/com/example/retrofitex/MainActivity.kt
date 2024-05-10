package com.example.retrofitex

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.retrofitex.data.DailyBoxOfficeList
import com.example.retrofitex.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    var movieList: List<DailyBoxOfficeList> = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            val movieList = BoxOfficeRepositoryImpl().getMovie("20240507")
            Log.d("TAG", movieList.toString())
            // 여기에서 movieList를 사용하여 UI 업데이트 또는 다른 작업을 수행할 수 있습니다.

            val mList = movieList.listIterator()
            if (mList != null) {
//                Log.d("TAG",postList.toString())
                while (mList.hasNext()) {
                    val movies = mList.next()
                    Log.i("MYTAG", mList.toString())
                    val result = " " + "영화 제목 : ${movies.movieNm}" + "\n" +
                            " " + "영화 순위 : ${movies.rank}" + "\n" +
                            " " + "개봉일 : ${movies.openDt}" + "\n" +
                            " " + "누적 관객 수 : ${movies.audiAcc}" + "\n\n\n"
                    binding.tvMovie.append(result)
                }
            }
        }

    }
}