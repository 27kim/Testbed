package io.lab27.githubuser.ui.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.lab27.githubuser.databinding.FragmentAboutBinding
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = FragmentAboutBinding.inflate(inflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let{
            var args = it["arg"].toString()
            rulesText.text = args
        }

        super.onViewCreated(view, savedInstanceState)
    }
}
