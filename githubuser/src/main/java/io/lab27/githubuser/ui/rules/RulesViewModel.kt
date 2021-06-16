package io.lab27.githubuser.ui.rules

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil

class RulesViewModel : ViewModel() {
    val _items = MutableLiveData<List<RvTest>>()
    val items: LiveData<List<RvTest>> get() = _items

    val _details = MutableLiveData<List<RvTest>>()
    val details: LiveData<List<RvTest>> get() = _details

    init {
        load()
    }

    fun load() {
        _items.value = listOf(
            RvTest("item1-1 title", "item desc1", false),
            RvTest("item1-2 title", "item desc2", false),
            RvTest("item1-3 title", "item desc3", false),
            RvTest("item1-4 title", "item desc4", false),
            RvTest("item1-5 title", "item desc5", false),
            RvTest("item1-6 title", "item desc6", false),
        )
        _details.value = listOf(
            RvTest("details1-1 title", "details desc1", false),
            RvTest("details1-2 title", "details desc2", false),
            RvTest("details1-3 title", "details desc3", false),
            RvTest("details1-4 title", "details desc4", false),
            RvTest("details1-5 title", "details desc5", false),
            RvTest("details1-6 title", "details desc6", false),
        )
    }

    fun changeData(i: Int) {
        when (i) {
            1 -> {
                _items.value = listOf(
                    RvTest("item1-1 title", "item desc1", false),
                    RvTest("item1-2 title", "item desc2", false),
                    RvTest("item1-3 title", "item desc3", false),
                    RvTest("item1-4 title", "item desc4", false),
                    RvTest("item1-5 title", "item desc5", false),
                    RvTest("item1-6 title", "item desc6", false),
                )
                _details.value = listOf(
                    RvTest("details1-1 title", "details desc1", false),
                    RvTest("details1-2 title", "details desc2", false),
                    RvTest("details1-3 title", "details desc3", false),
                    RvTest("details1-4 title", "details desc4", false),
                    RvTest("details1-5 title", "details desc5", false),
                    RvTest("details1-6 title", "details desc6", false),
                )
            }
            2 -> {
                _items.value = listOf(
                    RvTest("details2-1 title", "details desc1", false),
                    RvTest("details2-2 title", "details desc2", false),
                    RvTest("details2-3 title", "details desc3", false),
                    RvTest("details2-4 title", "details desc4", false),
                    RvTest("details2-5 title", "details desc5", false),
                    RvTest("details2-6 title", "details desc6", false),
                )

                _details.value = listOf(
                    RvTest("details2-1 title", "details desc1", false),
                    RvTest("details2-2 title", "details desc2", false),
                    RvTest("details2-3 title", "details desc3", false),
                    RvTest("details2-4 title", "details desc4", false),
                    RvTest("details2-5 title", "details desc5", false),
                    RvTest("details2-6 title", "details desc6", false),
                )
            }
        }
    }

    fun update(rvTest: RvTest) {
        Log.i("logging", "viewmodel: $rvTest")
        _items.value?.find { it == rvTest }?.isChecked = !rvTest.isChecked
    }
}

data class RvTest(
    val title: String = "",
    val desc: String = "",
    var isChecked: Boolean = false
) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<RvTest>() {
            override fun areItemsTheSame(oldItem: RvTest, newItem: RvTest) = oldItem == newItem
            override fun areContentsTheSame(oldItem: RvTest, newItem: RvTest) = oldItem == newItem
        }
    }
}