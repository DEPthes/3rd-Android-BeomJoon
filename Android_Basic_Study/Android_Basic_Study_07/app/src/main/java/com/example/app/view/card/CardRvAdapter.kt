package com.example.app.view.card

import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.databinding.ItemCardBinding
import com.example.app.domain.model.PhotoEntity

class CardRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var randomList: List<PhotoEntity> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CardHolder(binding)
    }

    override fun getItemCount(): Int {
        return randomList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CardHolder) {
            val item = randomList[position]
            holder.bind(item)
        }
    }

    inner class CardHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PhotoEntity) {
            Glide.with(binding.ivRandomPhoto.context)
                .load(item.thumb)
                .into(binding.ivRandomPhoto)

            // 북마크 상태에 따라 버튼 배경 설정
            setBookmarkState(item.isBookmark)

            binding.ivBookmark.setOnClickListener {
                item.isBookmark = !item.isBookmark
                setBookmarkState(item.isBookmark)
                Log.d("TAG2", "북마크 ${if (item.isBookmark) "추가" else "제거"}")
            }
        }

        private fun setBookmarkState(isBookmarked: Boolean) {
            if (isBookmarked) {
                binding.ivBookmark.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.blue
                    ), PorterDuff.Mode.SRC_IN
                )
            } else {
                binding.ivBookmark.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.gray
                    ), PorterDuff.Mode.SRC_IN
                )
            }
        }
    }

    fun setData(newPhotoList: List<PhotoEntity>) {
        randomList = newPhotoList
        notifyDataSetChanged()
    }
}