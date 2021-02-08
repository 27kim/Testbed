package io.lab27.githubuser.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import io.lab27.githubuser.adapter.HeaderAdapter
import io.lab27.githubuser.databinding.FragmentMoreMainBinding
import io.lab27.githubuser.ui.more.adapter.MenuAdapter
import io.lab27.githubuser.ui.more.adapter.SettingAdapter
import io.lab27.githubuser.ui.more.adapter.UsageHistoryAdapter
import io.lab27.githubuser.ui.more.adapter.UserAdapter
import org.koin.android.ext.android.inject

class MoreMainFragment : Fragment() {
    private lateinit var binding: FragmentMoreMainBinding
    private val viewModel: MoreViewModel by inject()

    private val settingAdapter by lazy {
        SettingAdapter(viewLifecycleOwner)
    }

    private val usageHistoryAdapter by lazy {
        UsageHistoryAdapter(viewLifecycleOwner)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentMoreMainBinding.inflate(inflater, container, false).apply {
        binding = this
        lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ConcatAdapter(ConcatAdapter.Config.Builder().build()).apply {
            addAdapter(UserAdapter(viewLifecycleOwner))
            addAdapter(MenuAdapter(viewLifecycleOwner))
            addAdapter(HeaderAdapter(viewLifecycleOwner, "설정"))
            addAdapter(settingAdapter)
            addAdapter(HeaderAdapter(viewLifecycleOwner, "이용 내역"))
            addAdapter(usageHistoryAdapter)
            addAdapter(HeaderAdapter(viewLifecycleOwner, "연결 앱"))
            addAdapter(MenuAdapter(viewLifecycleOwner))


        }.also {
            binding.moreRecyclerView.apply {
                adapter = it
            }
        }

        viewModel.init()
        viewModel.settingList.observe(viewLifecycleOwner, Observer { list ->
            settingAdapter.submitList(list)
        })
        viewModel.usageList.observe(viewLifecycleOwner, Observer { list ->
            usageHistoryAdapter.submitList(list)
        })
    }
}
//
//class HorizontalItemDecoration(
//    color: Int,
//    private val heightInPixels: Int
//) : RecyclerView.ItemDecoration() {
//
//    private val paint = Paint()
//
//    init {
//        paint.color = color
//        paint.isAntiAlias = true
//    }
//
//    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        super.onDraw(c, parent, state)
//
//        val left = parent.paddingLeft
//        val right = parent.width - parent.paddingRight
//
//        val childCount = parent.childCount
//        for (i in 0 until childCount) {
//            val child = parent.getChildAt(i)
//
//            val params = child.layoutParams as RecyclerView.LayoutParams
//
//            val top = child.bottom + params.bottomMargin
//            val bottom = top + heightInPixels
//            val adapterPosition = parent.getChildAdapterPosition(child)
//            val viewType = parent.adapter?.getItemViewType(adapterPosition)
//            if (viewType == RowType.ITEM.ordinal) { // here you make check before drawing the divider where row type determine if this item is header or normal item
//                c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
//            }
//        }
//    }
//}
//
//enum class RowType {
//    ITEM,
//    HEADER;
//}