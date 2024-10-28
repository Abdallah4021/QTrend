package com.neu.trend.dialogs

import android.content.res.Resources
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager


open class BaseDialogFragment : DialogFragment() {

    companion object {
        private val TAG = BaseDialogFragment::class.java.simpleName
    }

    override fun onResume() {
        if (dialog != null && dialog!!.window != null) {
            val params = dialog!!.window!!.attributes
            params.width = Resources.getSystem().displayMetrics.widthPixels - 250
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog!!.window!!.attributes = params as WindowManager.LayoutParams
        }
        super.onResume()
    }

    fun show(fragmentManager: FragmentManager) {
        this.show(fragmentManager, tag)
    }

}