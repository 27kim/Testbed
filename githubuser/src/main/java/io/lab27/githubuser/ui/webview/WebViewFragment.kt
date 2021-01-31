package io.lab27.githubuser.ui.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import io.lab27.githubuser.base.BaseFragment
import io.lab27.githubuser.databinding.FragmentWebviewBinding
import kotlinx.android.synthetic.main.fragment_webview.*

class WebViewFragment : BaseFragment() {
    private val args : WebViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentWebviewBinding
        .inflate(inflater).apply { lifecycleOwner = viewLifecycleOwner }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsWebView.apply {
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    return false
                }
            }
            loadUrl(args.newsUrl)
        }

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