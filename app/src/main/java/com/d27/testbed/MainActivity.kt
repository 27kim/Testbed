package com.d27.testbed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.d27.testbed.list.RoomListFragment
import java.util.*

class MainActivity : AppCompatActivity()
, RoomListFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, RoomListFragment.newInstance(), "listFragment")
            .commit()
    }

    override fun onItemClicked(uuid: UUID) {
        Toast.makeText(this, uuid.toString(), Toast.LENGTH_SHORT).show()
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, TestListFragment.newInstance(), "detailFragment")
//            .commit()
    }
}
