package com.example.android_basic_study_04

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_basic_study_04.databinding.DialogUpdateMemoBinding
import com.example.android_basic_study_04.databinding.ItemMemoBinding

class MemoRvAdapter(private val context: Context, private val data: MutableList<String>) :
    RecyclerView.Adapter<MemoRvAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMemoBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        init {
//            binding.btnUpdateMemo.setOnClickListener {
//                showUpdateDialog(adapterPosition)
//            }
//        }

        fun bind(item: String) {
            binding.tvMemo.text = item
        }

        fun delMemo(position: Int) {
            binding.btnDelMemo.setOnClickListener {
                data.removeAt(position)
                notifyDataSetChanged() // 데이터 변경 후 RecyclerView 갱신
            }
        }

        fun updateMemo() {
            binding.btnUpdateMemo.setOnClickListener {
                showUpdateDialog(adapterPosition)
            }
        }

        private fun showUpdateDialog(position: Int) {
            val dialogBinding = DialogUpdateMemoBinding.inflate(LayoutInflater.from(context))

            val dialog = AlertDialog.Builder(context).apply {
                setTitle("메모 수정")
                setView(dialogBinding.root)
                setPositiveButton("확인") { dialog, _ ->
                    val newMemo = dialogBinding.etUpdateMemo.text.toString()
                    data[position] = newMemo // 해당 메모를 새로운 내용으로 수정
                    notifyItemChanged(position) // 해당 위치의 아이템만 업데이트
                    dialog.dismiss()
                }
                setNegativeButton("취소") { dialog, _ ->
                    dialog.dismiss()
                }
            }.create()

            dialog.show()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
        holder.delMemo(position)
        holder.updateMemo()
    }

    override fun getItemCount(): Int {
        return data.size
    }

}