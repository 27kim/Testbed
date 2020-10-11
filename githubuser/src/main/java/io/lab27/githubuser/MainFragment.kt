package io.lab27.githubuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argument = requireArguments().getInt(ARG_POSITION)

        Toast.makeText(activity, "$argument", Toast.LENGTH_SHORT).show()

    }

    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(position: Int): MainFragment {
            val mainFragment = MainFragment()
            var bundle = Bundle()
            bundle.apply {
                putInt(ARG_POSITION, position)
            }
            mainFragment.arguments = bundle
            return mainFragment

        }
    }
}