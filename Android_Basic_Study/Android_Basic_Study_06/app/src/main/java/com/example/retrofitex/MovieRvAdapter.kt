package com.example.retrofitex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitex.data.DailyBoxOfficeList
import com.example.retrofitex.databinding.ItemMovieBinding

class MovieRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var movieList = listOf<DailyBoxOfficeList>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieHolder) {
            holder.bind(movieList[position])
        }
    }

    fun setData(list: List<DailyBoxOfficeList>) {
        movieList = list
    }

    inner class MovieHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DailyBoxOfficeList) {
            binding.tvMovieRank.text = "순위 : " + item.rank
            binding.tvMovieTitle.text = "영화 제목 : " + item.movieNm
            binding.tvMovieOpen.text = "영화 개봉일 : " + item.openDt
            binding.tvMovieAud.text = "누적 관객 수 : " + item.audiAcc
        }
    }

}