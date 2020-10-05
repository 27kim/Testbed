package io.lab27.fragment

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class Fragment2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_layout_2, container, false)

        view.findViewById<Button>(R.id.btn2).setOnClickListener {
            activity?.let{
                it.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_2, Fragment1.newInstance("jane", 6))
                    .addToBackStack(null)
                    .commit()
            }
        }

        return view
    }

    companion object {
        fun newInstance(): Fragment2 {
            return Fragment2()
        }
    }
}