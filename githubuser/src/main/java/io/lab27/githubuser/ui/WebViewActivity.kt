package io.lab27.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import io.lab27.githubuser.R
import io.lab27.githubuser.databinding.ActivityWebViewBinding
import org.koin.android.ext.android.bind

class WebViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)

        val args = intent.getStringExtra(ARG_ID)

        binding.eventWebView.loadUrl("https://test.happ.hyundai.com/menu/event/detail?id=$args")
    }

    companion object {
        const val ARG_ID = "arg_id"
    }
}