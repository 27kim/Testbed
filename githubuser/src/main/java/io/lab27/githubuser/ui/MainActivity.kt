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

        /**
         * keyStore sample
         * */
        val keyStore = java.security.KeyStore.getInstance("AndroidKeyStore")

        val sharedPreferences = getSharedPreferences("myHyundai", Context.MODE_PRIVATE)
        val securedStore = SecureSharedPreferences.wrap(sharedPreferences)

        securedStore.put("title", "제목")
        securedStore.put("flagaa", false)
        val title = securedStore.get("title", "default")
        val flag = securedStore.get("flagaa", false)

        L.i("testing title ? $title")
        L.i("testing flag ? $flag")

        /**
         * keyStore sample
         * */

        mainViewPager.apply {
            adapter = ViewPagerAdapter(this@MainActivity, 5)
        }

        TabLayoutMediator(tabLayout, mainViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "API"
                1 -> tab.text = "LOCAL"
                2 -> tab.text = "PAGING"
                3 -> tab.text = "AUTH"
                4 -> tab.text = "EVENT"
            }
        }.attach()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent != null) {
            val uri = getIntent().data
            val data = uri?.getQueryParameter("code")?:"empty!"
            L.i("onNewIntent ? $data")
        }
    }
}
