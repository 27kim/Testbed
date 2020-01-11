package com.d27.testbed.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.d27.testbed.R
import com.d27.testbed.TestViewModel
import com.d27.testbed.database.TestEntity
import java.util.*

class RoomListFragment : Fragment() {

    lateinit var recyclerView : RecyclerView
    private val testViewModel : TestViewModel by lazy {
        ViewModelProviders.of(this).get(TestViewModel::class.java)
    }
    private var clickListener : Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        clickListener = context as Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        recyclerView = view.findViewById(R.id.main_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = TestAdapter(emptyList())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        testViewModel.getAllData?.observe(this,
            Observer { list -> updateUI(list) }
            )
    }

    private fun updateUI(list: List<TestEntity>?) {
        recyclerView.adapter = list?.let{
            TestAdapter(list)
        }
    }

    inner class TestViewHolder(view : View) : RecyclerView.ViewHolder(view)
    , View.OnClickListener{
        lateinit var testEntity: TestEntity

        init {
            view.setOnClickListener(this)
        }

        var tvTitle = view.findViewById(R.id.tvTitle) as TextView
        var tvDesc = view.findViewById(R.id.tvDesc) as TextView
        var tvDate = view.findViewById(R.id.tvDate) as TextView

        fun onBind(textEntity: TestEntity){
            testEntity = textEntity
            tvTitle.text = textEntity.title
            tvDesc.text = textEntity.desc
            tvDate.text = textEntity.date.toString()
        }

        override fun onClick(p0: View?) {
            clickListener?.onItemClicked(testEntity.id)
        }
    }

    inner class TestAdapter(val list : List<TestEntity>) : RecyclerView.Adapter<TestViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_list_item, parent, false)
            return TestViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
            holder.onBind(list[position])
        }

    }

    companion object{
        fun newInstance() : RoomListFragment{
            return RoomListFragment()
        }
    }

    interface Callbacks{
        fun onItemClicked(uuid: UUID)
    }
}