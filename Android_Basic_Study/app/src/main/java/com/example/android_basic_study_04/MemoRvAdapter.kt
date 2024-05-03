package com.example.android_basic_study_04

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_basic_study_04.data.Memo
import com.example.android_basic_study_04.databinding.ItemMemoBinding

class MemoRvAdapter(private val data: MutableList<Memo>) :
    RecyclerView.Adapter<MemoRvAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMemoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(content: Memo) {
            binding.tvMemo.text = content.text
            binding.btnDeleteMemo.setOnClickListener {
                deleteMemo(content, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addMemo(memo: Memo){
        data.add(memo)
        notifyItemChanged(itemCount)
    }

    fun deleteMemo(content: Memo, position: Int) {
        // position에 해당하는 메모를 삭제합니다.
        data.removeAt(position)
        // RecyclerView에 삭제가 반영되었음을 알립니다.
        notifyItemRemoved(position)
    }
}