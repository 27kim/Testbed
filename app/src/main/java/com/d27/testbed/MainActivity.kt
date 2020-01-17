package com.d27.testbed

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.text.format.DateUtils
import android.util.Log
import android.widget.Toast
import com.d27.testbed.list.RoomListFragment
import java.lang.AssertionError
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity()
, RoomListFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, RoomListFragment.newInstance(), "listFragment")
            .commit()

        readCalendarEvent(this)
    }

    override fun onItemClicked(uuid: UUID) {
        Toast.makeText(this, uuid.toString(), Toast.LENGTH_SHORT).show()
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, TestListFragment.newInstance(), "detailFragment")
//            .commit()
    }

    fun readCalendarEvent(context: Context){
        val contentResolver = context.contentResolver

        val startTime = Calendar.getInstance()
        startTime.set(2020,1,1)

        val endTime = Calendar.getInstance()
        endTime.set(2020,3,1)

        val selection = "(( " + CalendarContract.Events.DTSTART + " >= " + startTime.timeInMillis + " ) AND ( " + CalendarContract.Events.DTSTART + " <= " + endTime.timeInMillis + " ))"

        val cursor = contentResolver.query(
            Uri.parse("content://com.android.calendar/events")
            , arrayOf<String>
                ("_id"
                , CalendarContract.Events.TITLE
                , CalendarContract.Events.ORGANIZER
                , CalendarContract.Events.DTSTART
                , CalendarContract.Events.DTEND
                , CalendarContract.Events.EVENT_LOCATION
            )
            , null //selection
            , null
            , CalendarContract.Events.DTSTART + " DESC")

        var gCalendar = mutableListOf<GoogleCalendar>()

        try{
            cursor?.let {
                if(cursor?.count > 0){
                    while(cursor.moveToNext()) {
                        var id = cursor.getInt(0).toString()
                        var title = cursor.getString(1)
                        var organizer = cursor.getString(2)
                        var dtstart = cursor.getString(3)
                        var dtend = cursor.getString(4)
//                        var dtstart = DateUtils.formatDateTime(this, cursor.getString(3).toLong(),DateUtils.FORMAT_SHOW_DATE)
//                        var dtend = DateUtils.formatDateTime(this, cursor.getString(4).toLong(),DateUtils.FORMAT_SHOW_DATE)
                        var location : String? = cursor.getString(5)
                        var item = GoogleCalendar(
                            id,
                            title,
                            organizer,
                            dtstart,
                            dtend,
                            location?:""
                        )
                        Log.i("tbtbtb",item.toString())
                        gCalendar.add(
                            item
                        )
                    }
                }
            }

        }catch (e : AssertionError){
            Log.e("tbtbtb", e.message)
        }catch (e : Exception){
            Log.e("tbtbtb", e.message)
        }
    }

    data class GoogleCalendar(val _id : String, val title : String, val organizer : String, val dtstart : String, val dtend : String, val location : String)
}
