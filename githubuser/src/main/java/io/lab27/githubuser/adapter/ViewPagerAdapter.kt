package io.lab27.githubuser.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.lab27.githubuser.ui.*
import io.lab27.githubuser.ui.main.MainFragment
import java.lang.IllegalArgumentException

class ViewPagerAdapter(activity: AppCompatActivity, private val itemsCount: Int) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MainFragment.getInstance(position)
            1 -> LocalFragment.getInstance(position)
            2 -> PagingFragment.getInstance(position)
            3 -> AuthFragment.getInstance(position)
            4 -> EventFragment.getInstance(position)
            else -> throw IllegalArgumentException("IllegalArgumentException createFragment")
        }
    }
}