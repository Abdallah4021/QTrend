package com.neu.trend.interfaces


interface PopupDialogListener {

    fun onDialogClose(dialogTag: String)

    fun onDialogOk(callerTag: String, dialogTag: String, dialogResult: Any? = null)

}