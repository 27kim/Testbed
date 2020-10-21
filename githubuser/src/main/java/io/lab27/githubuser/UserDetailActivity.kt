package io.lab27.githubuser

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UserDetailActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        intent.getStringExtra("user")?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

//    companion object{
//        fun newInstance() : UserDetailActivity{
//            val intent = Intent(this, UserDetailActivity::class.java)
//            return  intent
//        }
//    }
}