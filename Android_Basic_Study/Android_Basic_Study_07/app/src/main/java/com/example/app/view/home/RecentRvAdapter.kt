package com.example.app.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.databinding.ItemBookmarkBinding
import com.example.app.databinding.ItemRecentImageBinding
import com.example.app.domain.model.PhotoEntity

class RecentRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var recentList: List<PhotoEntity> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemRecentImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecentHolder(binding)
    }

    override fun getItemCount(): Int {
        return recentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecentHolder) {
            val item = recentList[position]
            holder.bind(item)
        }
    }

    inner class RecentHolder(private val binding: ItemRecentImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PhotoEntity) {
            // 이미지를 로드
            Glide.with(binding.ivRecentImage.context)
                .load(item.thumb)
                .into(binding.ivRecentImage)

            binding.tvImageDesc.text = item.description

            // 클릭 이벤트 설정
            itemView.setOnClickListener {
                itemClick.onItemClick(item.id)
            }
        }
    }

    lateinit var itemClick: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClick = onItemClickListener
    }

    fun setData(newPhotoList: List<PhotoEntity>) {
        recentList = newPhotoList
        notifyDataSetChanged()
    }
}