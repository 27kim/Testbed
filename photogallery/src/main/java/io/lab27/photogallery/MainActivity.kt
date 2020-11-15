package io.lab27.photogallery

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import io.lab27.photogallery.adapter.PhotoAdapter
import io.lab27.photogallery.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var perms = arrayOf(
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE"
    )
    private val photoViewModel: PhotoViewModel by viewModel()
    lateinit var tracker: SelectionTracker<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binding.apply {
            lifecycleOwner = lifecycleOwner
        }

        var photoAdapter = PhotoAdapter()

        photoRecyclerView.adapter = photoAdapter
        tracker = SelectionTracker.Builder<Long>(
            "photoSelection",
            photoRecyclerView,
            StableIdKeyProvider(photoRecyclerView),
            PhotoAdapter.PhotoDetailsLookup(photoRecyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything()
        ).build()

        tracker.addObserver( object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                Log.i("activity ", "clicked?")
//                val nItems: Int? = tracker?.selection?.size()
//
//                if (nItems != null && nItems > 0) {
//
//                    // Change title and color of action bar
//
//                    title = "$nItems items selected"
//                    supportActionBar?.setBackgroundDrawable(
//                        ColorDrawable(Color.parseColor("#ef6c00"))
//                    )
//                } else {
//
//                    // Reset color and title to default values
//
//                    title = "RVSelection"
//                    supportActionBar?.setBackgroundDrawable(
//                        ColorDrawable(Color.parseColor("#126c00"))
//                    )
//                }
            }
        })
        photoAdapter.tracker = tracker
        photoViewModel.retrievePhotoList()
        photoViewModel.photoList.observe(this, Observer { list ->
            Log.i("list", "list ? $list")
            photoAdapter.submitList(list)
        })

    }

    fun checkPerms() {
        // Checking if device version > 22 and we need to use new permission model
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            // Checking if we can draw window overlay
            if (!Settings.canDrawOverlays(this)) {
                // Requesting permission for window overlay(needed for all react-native apps)
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, 123)
            }
            for (perm in perms) {
                // Checking each persmission and if denied then requesting permissions
                if (checkSelfPermission(perm!!) === PackageManager.PERMISSION_DENIED) {
                    requestPermissions(perms, 123)
                    break
                }
            }
        }
    }

    // Window overlay permission intent result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            checkPerms()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            123 ->             // example how to get result of permissions requests (there can be more then one permission dialog)
                // boolean readAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                // boolean writeAccepted = grantResults[1]==PackageManager.PERMISSION_GRANTED;
                // checking permissions to prevent situation when user denied some permission
                checkPerms()
        }
    }

}