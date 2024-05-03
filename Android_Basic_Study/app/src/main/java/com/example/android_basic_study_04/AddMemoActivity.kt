package com.example.android_basic_study_04

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_basic_study_04.databinding.ActivityAddMemoBinding

class AddMemoActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddMemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveMemo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("text", binding.etMemo.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }

}