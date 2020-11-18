package io.lab27.githubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.lab27.githubuser.base.BaseFragment
import io.lab27.githubuser.databinding.FragmentWebviewBinding

class WebViewFragment : BaseFragment() {
    private lateinit var binding: FragmentWebviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebviewBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.getString(ARG_STR)
    }

    companion object {
        private const val ARG_STR = "args_string"

        fun getInstance(args: String) =
            WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_STR, args)
                }
            }
    }
}