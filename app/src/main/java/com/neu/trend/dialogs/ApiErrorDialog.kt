package com.neu.trend.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.neu.trend.R
import com.neu.trend.databinding.DialogApiErrorBinding
import com.neu.trend.interfaces.PopupDialogListener


class ApiErrorDialog  : BaseDialogFragment() {

    private var _binding: DialogApiErrorBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val EXTRA_DIALOG_MESSAGE = "EXTRA_DIALOG_MESSAGE"
        private const val EXTRA_DIALOG_TAG = "EXTRA_DIALOG_TAG"
        private const val EXTRA_DIALOG_CALLER_TAG = "EXTRA_DIALOG_CALLER_TAG"
        private const val EXTRA_DIALOG_CANCEL_BUTTON = "EXTRA_DIALOG_CANCEL_BUTTON"

        @JvmStatic
        fun newInstance(callerTag: String, dialogTag: String, message: String, cancelButton: String) =
            ApiErrorDialog().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_DIALOG_CALLER_TAG, callerTag)
                    putString(EXTRA_DIALOG_TAG, dialogTag)
                    putString(EXTRA_DIALOG_MESSAGE, message)
                    putString(EXTRA_DIALOG_CANCEL_BUTTON, cancelButton)
                }
            }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogApiErrorBinding.inflate(LayoutInflater.from(context))
        val dialog = Dialog(requireContext(), R.style.MindDialogStyle)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {

            it.getString(EXTRA_DIALOG_MESSAGE)?.let { message -> binding.tvMessage.text = message }
            it.getString(EXTRA_DIALOG_CANCEL_BUTTON)?.let { message -> binding.btnCancel.text = message }

            binding.btnCancel.setOnClickListener { dismissDialog() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun dismissDialog(){
        if (activity is PopupDialogListener) {
            arguments?.let {
                (activity as PopupDialogListener).onDialogClose(
                        it.getString(EXTRA_DIALOG_CALLER_TAG)!!
                )
            }
        }
        dismiss()
    }
}