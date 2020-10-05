package io.lab27.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class Fragment0 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_layout_1, container, false)

        return view
    }

    companion object {
        fun newInstance(name: String, age: Int): Fragment0 {
            return Fragment0().apply {
            }
        }
    }

}