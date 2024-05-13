package com.example.retrofitex.presentation.dialog

import com.example.retrofitex.data.DailyBoxOfficeList

interface MovieAddDialogInterface {
    fun onSendBtnClicked(newItem: DailyBoxOfficeList)
    fun onCancelBtnClicked()
}