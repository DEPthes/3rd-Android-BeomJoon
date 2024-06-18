package com.example.app.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.databinding.ItemBookmarkBinding

class BookmarkRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val bookmarkList = mutableListOf<String>("1", "2", "3", "4", "5")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBookmarkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookmarkHolder(binding)
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BookmarkHolder) {
            holder.bind()
        }
    }

    inner class BookmarkHolder(private val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            Glide.with(binding.ivBookmarkImage)
                .load("https://hatrabbits.com/wp-content/uploads/2017/01/random.jpg")
                .into(binding.ivBookmarkImage)

//            itemView.setOnClickListener {
//                itemClick.onClick(item.id)
//            }
        }
    }

    lateinit var itemClick: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClick = onItemClickListener
    }

    fun setData() {
        notifyDataSetChanged()
    }
}