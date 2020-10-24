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

class BaseDialog : DialogFragment() {
    lateinit var binding: DialogBaseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_base, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val args = arguments
        args?.let {
            binding.apply {
                tvTitle.text = args.getString("title")
                tvMessage.text = args.getString("message")
                dialogCancel.text = getText(R.string.dialog_cancel)
                dialogOkay.text = getText(R.string.dialog_okay)
                dialogOkay.setOnClickListener {
                    val intent = Intent(requireActivity(), UserDetailActivity::class.java)
                    intent.apply {
                        putExtra("user", args.getString("title"))
                    }.also {
                        startActivity(it)
                    }
                    dismiss()
                }
            }

        } ?: run {
            binding.apply {
                tvTitle.text = getText(R.string.dialog_title)
                tvMessage.text = getText(R.string.dialog_message)
                dialogCancel.text = getText(R.string.dialog_cancel)
                dialogOkay.text = getText(R.string.dialog_okay)
            }
        }

        binding.apply {
            dialogCancel.setOnClickListener {
                dismiss()
            }
        }

        return binding.root
    }

    //    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            // Use the Builder class for convenient dialog construction
//            val builder = AlertDialog.Builder(it)
////            builder.apply {
////                setTitle(R.string.dialog_title)
////                setMessage(R.string.dialog_message)
////                setPositiveButton(R.string.dialog_okay) { dialog, id ->
////                    // FIRE ZE MISSILES!
////                }
////                setNegativeButton(R.string.dialog_cancel) { dialog, id ->
////                    // User cancelled the dialog
////                }
////            }
//            // Create the AlertDialog object and return it
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//    }
    companion object {
        fun getInstance(bundle: Bundle): BaseDialog {
            val fragment = BaseDialog()
            fragment.arguments = bundle
            return fragment

        }
    }
}