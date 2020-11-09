package io.lab27.githubuser.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.lab27.githubuser.ui.LocalFragment
import io.lab27.githubuser.ui.PagingFragment
import io.lab27.githubuser.ui.RemoteFragment
import java.lang.IllegalArgumentException

class ViewPagerAdapter(activity: AppCompatActivity, private val itemsCount: Int) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RemoteFragment.getInstance(position)
            1 -> LocalFragment.getInstance(position)
            2 -> PagingFragment.getInstance(position)
            else -> throw IllegalArgumentException("IllegalArgumentException createFragment")
        }
    }
}