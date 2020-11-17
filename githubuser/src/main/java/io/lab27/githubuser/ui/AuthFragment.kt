package io.lab27.githubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.Observer
import io.lab27.githubuser.base.BaseFragment
import io.lab27.githubuser.databinding.FragmentAuthBinding
import io.lab27.githubuser.util.L
import io.lab27.githubuser.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_auth.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : BaseFragment() {
    private lateinit var binding: FragmentAuthBinding
    private val viewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWebView()

        binding.viewModel = viewModel
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE

            }
        })
    }

    private fun initWebView() {
        authWebView.apply {
            this.webViewClient = AuthWebViewClient() // 클릭시 새창 안뜨게
            val mWebSettings = this.settings //세부 세팅 등록

            mWebSettings.apply {
                javaScriptEnabled = true // 웹페이지 자바스클비트 허용 여부
                setSupportMultipleWindows(false) // 새창 띄우기 허용 여부
                javaScriptCanOpenWindowsAutomatically = true // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
                loadWithOverviewMode = true // 메타태그 허용 여부
                useWideViewPort = true // 화면 사이즈 맞추기 허용 여부
                setSupportZoom(false) // 화면 줌 허용 여부
                builtInZoomControls = false // 화면 확대 축소 허용 여부
                layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN // 컨텐츠 사이즈 맞추기
                cacheMode = WebSettings.LOAD_NO_CACHE // 브라우저 캐시 허용 여부
                domStorageEnabled = true // 로컬저장소 허용 여부
            }
        }
        authWebView.loadUrl("http://35.216.37.218:21549/oauth/authorize?response_type=code&client_id=SkKblE82jiT6y6e356dCVy3q&scope=profile")
    }

    inner class AuthWebViewClient() : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
            L.e("url? $url")
            if (url?.startsWith("oauth2://")) {
                val idx = url?.indexOf("?")
                val code = url?.substring(idx)
                viewModel.fetchAuthCode(code.split("=")[1])
                return true
            }

            return false
        }
    }

    companion object {
        private const val ARG_POSITION = "position"

        fun getInstance(position: Int) =
            AuthFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                }
            }
    }
}