package com.example.app.view.card

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.databinding.ItemCardBinding
import com.example.app.domain.model.PhotoEntity
import com.example.app.view.detail.DetailViewModel

class ViewPagerAdapter(
    private val viewModel: DetailViewModel
) : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {
    private var randomList: List<PhotoEntity> = emptyList()
    var onBookmarkClicked: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PagerViewHolder(binding)
    }

    override fun getItemCount(): Int = randomList.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val item = randomList[position]
        holder.bind(item)
    }

    inner class PagerViewHolder(private val binding: ItemCardBinding) :
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

                // ViewModel에 북마크 상태 변경 알리기
                if (item.isBookmark) {
                    viewModel.addBookmark(item.id, item.thumb)
                    Toast.makeText(
                        binding.root.context,
                        "북마크에 추가 되었습니다",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.deleteBookmark(item.id)
                    Toast.makeText(
                        binding.root.context,
                        "북마크에서 삭제 되었습니다",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // 북마크 클릭 이벤트를 프래그먼트에 알림
                onBookmarkClicked?.invoke(adapterPosition)
            }
        }

        private fun setBookmarkState(isBookmarked: Boolean) {
            if (isBookmarked) {
                binding.ivBookmark.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.blue
                    )
                )
            } else {
                binding.ivBookmark.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.gray
                    )
                )
            }
        }
    }

    fun setData(newPhotoList: List<PhotoEntity>) {
        randomList = newPhotoList
        notifyDataSetChanged()
    }
}
