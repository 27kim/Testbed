package io.lab27.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class Fragment1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_layout_1, container, false)

        view.findViewById<Button>(R.id.btn1).setOnClickListener {
            Toast.makeText(activity, "btn1 clicked", Toast.LENGTH_SHORT).show()
        }

        arguments?.let {
            view.findViewById<Button>(R.id.btn1).text = it.get("name").toString()
            view.findViewById<TextView>(R.id.tv1).text = it.get("age").toString()
        }
        return view
    }

    companion object {
        fun newInstance(name: String, age: Int): Fragment1 {

            val bundle = Bundle().apply {
                putString("name", name)
                putInt("age", age)
            }
            return Fragment1().apply {
                arguments = bundle
            }
        }
    }

}