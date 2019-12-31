package com.d27.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.mainRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = MainAdapter()

    }

    inner class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_recycler_item, parent, false)
            return MainViewHolder(view)
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

                val title = "title $position"
                val date = "date $position"

                holder.bind(title, date)
        }

    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init{
            itemView.setOnClickListener{
                Toast.makeText(applicationContext, "clicked", Toast.LENGTH_SHORT).show()
                recyclerView.adapter?.notifyItemMoved(0,3)
            }
        }
        val textView : TextView = itemView.findViewById(R.id.main_item_title)
        val textDate : TextView = itemView.findViewById(R.id.main_item_date)

        fun bind(title: String, date : String){
            textView.text = title
            textDate.text = date
        }
    }
}
