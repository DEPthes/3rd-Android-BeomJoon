package com.example.android_basic_study_04

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_basic_study_04.databinding.ItemMemoBinding

class MemoRvAdapter(private val data: MutableList<String>) :
    RecyclerView.Adapter<MemoRvAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMemoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.tvMemo.text = item
        }

        fun delMemo(position: Int) {
            binding.btnDelMemo.setOnClickListener {
                data.removeAt(position)
                notifyDataSetChanged() // 데이터 변경 후 RecyclerView 갱신
            }
        }

//        fun updateMemo() {
//            binding.btnUpdateMemo.setOnClickListener {
//                val position = adapterPosition
//                showUpdateDialog(position)
//            }
//        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
        holder.delMemo(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}