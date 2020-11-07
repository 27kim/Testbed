package com.deliveryhero.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.deliveryhero.githubuser.adapter.CategoryPagerAdapter
import com.deliveryhero.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryPagerAdapter: CategoryPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val categories = mutableListOf(
            "API",
            "LOCAL"
        )
        categoryPagerAdapter = CategoryPagerAdapter(supportFragmentManager, categories)

        val viewPager = binding.viewPager.apply {
            adapter = categoryPagerAdapter
        }

        binding.tabLayout.apply {
            setUpWithViewPager(viewPager)
        }
    }
}