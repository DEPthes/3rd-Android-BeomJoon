package com.example.android_basic_study_04

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.android_basic_study_04.data.Memo
import com.example.android_basic_study_04.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var memoList = mutableListOf<Memo>()
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MemoRvAdapter(memoList)
        binding.rvMemo.layoutManager = LinearLayoutManager(this)
        binding.rvMemo.adapter = adapter

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val newMemo = result.data?.getStringExtra("text")
                    if (newMemo != null) {
                        adapter.addMemo(Memo(newMemo))
                    }
                }
            }

        binding.btnAddMemo.setOnClickListener {
            val intent = Intent(this, AddMemoActivity::class.java)
            launcher.launch(intent)
        }

        val decoration = DividerItemDecoration(this, VERTICAL)
        binding.rvMemo.addItemDecoration(decoration)
    }
}