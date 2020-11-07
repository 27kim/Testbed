package com.deliveryhero.githubuser.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.deliveryhero.githubuser.ApiFragment
import com.deliveryhero.githubuser.LocalFragment
import java.lang.IllegalArgumentException


class CategoryPagerAdapter(
    fragmentManager: FragmentManager,
    private val categories: MutableList<String>
) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> ApiFragment.newInstance()
            1 -> LocalFragment.newInstance()
            else -> throw IllegalArgumentException("IllegalArgumentException")
        }
    }

    override fun getCount(): Int {
        return categories.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return categories[position % categories.size]
    }
}