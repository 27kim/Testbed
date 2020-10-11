package io.lab27.base

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import io.lab27.fragment.R

open class BaseFragment : Fragment() {
    var current : Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        current = activity!!.window.decorView.systemUiVisibility
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        activity!!.window.statusBarColor = resources.getColor(R.color.white)
    }

    override fun onDetach() {
        super.onDetach()
        activity!!.window.statusBarColor = resources.getColor(R.color.green)
        activity!!.window.decorView.systemUiVisibility = current
    }
}