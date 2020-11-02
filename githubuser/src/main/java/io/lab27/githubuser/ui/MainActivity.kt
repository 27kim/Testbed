package io.lab27.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import io.lab27.githubuser.R
import io.lab27.githubuser.adapter.ViewPagerAdapter
import io.lab27.githubuser.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mainViewPager.apply {
            adapter = ViewPagerAdapter(
                this@MainActivity,
                2
            )
        }

        TabLayoutMediator(tabLayout, mainViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "API"
                1 -> tab.text = "LOCAL"
            }
        }.attach()
    }
}
