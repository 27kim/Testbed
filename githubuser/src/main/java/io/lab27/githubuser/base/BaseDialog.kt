package io.lab27.githubuser.base

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import io.lab27.githubuser.R
import io.lab27.githubuser.UserDetailActivity
import io.lab27.githubuser.databinding.DialogBaseBinding
import kotlinx.android.synthetic.main.dialog_base.*

class BaseDialog : DialogFragment() {
    lateinit var binding: DialogBaseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_base, container, false)
        //배경 투명 및 하위 호환성
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val args = arguments

        binding.apply {
            tvTitle.text = args?.getString("title") ?: getString(R.string.dialog_title)
            tvMessage.text = args?.getString("message") ?: getString(R.string.dialog_message)
            dialogBtnCancel.text =
                args?.getString("cancel") ?: getText(R.string.dialog_cancel)
            dialogBtnOkay.text = args?.getString("okay") ?: getText(R.string.dialog_okay)
            dialogBtnCancel.setOnClickListener {
                dismiss()
            }
            dialogBtnOkay.setOnClickListener {
                args?.let {
                    val intent = Intent(requireActivity(), UserDetailActivity::class.java)
                    intent.apply {
                        putExtra("user", args.getString("title"))
                    }.also {
                        startActivity(it)
                    }
                }
                dismiss()
            }
        }
        return binding.root
    }

    companion object {
        fun getInstance(bundle: Bundle): BaseDialog {
            val fragment = BaseDialog()
            fragment.arguments = bundle
            return fragment

        }
    }
}