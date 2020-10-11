package io.lab27.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(),
    FragmentListener {
    private val ft by lazy {
        supportFragmentManager.beginTransaction()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = resources.getColor(R.color.green)

        ft.add(R.id.mainContainer, FragmentBase())
            .commit()
    }

    override fun onChangeFragment(fragmentName: String) {
        val ft = supportFragmentManager.beginTransaction()
        when (fragmentName) {
            "f1" -> {
                ft
                    .replace(R.id.mainContainer, Fragment0.newInstance("jane", 6))
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

}

interface FragmentListener {
    fun onChangeFragment(fragmentName: String)
}
