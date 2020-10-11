package io.lab27.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewPager.adapter = ViewPagerAdapter(this, 2)
        mainViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        TabLayoutMediator(tabLayout, mainViewPager) { tab, position ->
            //To get the first name of doppelganger celebrities
            var tabTitle = when(position){
                0 -> "API"
                1 -> "LOCAL"
                else -> ""
            }
            tab.text = tabTitle
        }.attach()
    }
}
