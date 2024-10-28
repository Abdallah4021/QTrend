package com.neu.trend.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.neu.trend.R
import com.neu.trend.databinding.DialogProgressBinding
import com.neu.trend.utils.DIALOG_TAG_PROGRESS


class ProgressDialogFragment : DialogFragment() {

    private var _binding: DialogProgressBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogProgressBinding.inflate(LayoutInflater.from(context))
        return Dialog(requireContext(), R.style.ProgressDialog).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        return binding.root //inflater.inflate(R.layout.dialog_progress, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun show(fragmentManager: FragmentManager) {
            ProgressDialogFragment().apply {
                this.isCancelable = false
            }.also {
                it.show(fragmentManager, DIALOG_TAG_PROGRESS)
            }
        }

        fun hide(fragmentManager: FragmentManager) {
            val progressDialog = fragmentManager.findFragmentByTag(DIALOG_TAG_PROGRESS)
            progressDialog?.let {
                (progressDialog as DialogFragment).dismiss()
            }
        }
    }
}
