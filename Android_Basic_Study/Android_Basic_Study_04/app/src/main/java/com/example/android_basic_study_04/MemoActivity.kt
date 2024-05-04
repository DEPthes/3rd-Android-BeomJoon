package com.example.android_basic_study_04

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_basic_study_04.databinding.ActivityMemoBinding

class MemoActivity:AppCompatActivity() {
    private lateinit var binding: ActivityMemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddMemo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("text", binding.etMemo.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}