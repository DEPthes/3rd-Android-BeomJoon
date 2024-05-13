package com.example.retrofitex.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.retrofitex.data.DailyBoxOfficeList
import com.example.retrofitex.databinding.DialogAddMovieBinding

@Suppress("DEPRECATION")
class MovieAddDialog(context: Context, dialogInterface: MovieAddDialogInterface) : Dialog(context) {
    private var mBinding: DialogAddMovieBinding? = null
    private val binding get() = mBinding!!
    private var dialogInterface: MovieAddDialogInterface? = null

    // 인터페이스 연결
    init {
        this.dialogInterface = dialogInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogAddMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 다이얼로그의 크기 조절
        val windowParams = window!!.attributes
        val display = window!!.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        windowParams.width = (width * 0.8).toInt() // 다이얼로그의 폭을 화면 너비의 80%로 설정
        window!!.attributes = windowParams


        // 배경 투명하게
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 추가 버튼 눌렀을 때
        binding.btnSend.setOnClickListener {

            val movieRank = binding.etRank.text.toString()
            val movieTitle = binding.etMovieTitle.text.toString()
            val movieOpenDt = binding.etOpenDate.text.toString()
            val movieAudAcc = binding.etAudiAcc.text.toString()

            // 새로운 DailyBoxOfficeList 생성
            val newItem = DailyBoxOfficeList(
                rank = movieRank,
                movieNm = movieTitle,
                openDt = movieOpenDt,
                audiAcc = movieAudAcc
            )
            this.dialogInterface?.onSendBtnClicked(newItem)
            dismiss()
        }
        // 취소 버튼 눌렀을 때
        binding.btnCancel.setOnClickListener {
            this.dialogInterface?.onCancelBtnClicked()
            dismiss()
        }
    }

}