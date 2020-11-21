package io.lab27.githubuser.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import io.lab27.githubuser.R
import io.lab27.githubuser.adapter.ViewPagerAdapter
import io.lab27.githubuser.util.L
import io.lab27.githubuser.util.SecureSharedPreferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }


}
