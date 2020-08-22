package com.example.simple_image_gallery

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private var continueAfterPermission = 0
    private lateinit var picIntent: Intent
    private var TAG = "MainActivity"
    private val MY_PERMISSIONS_REQUEST_STORAGE_PERMISSIONS = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        picIntent = Intent(this@MainActivity, PictureActivity::class.java)

        val mPicButton = findViewById<Button>(R.id.btn_get_image)
        mPicButton.setOnClickListener {
            if (isStoragePermissionGranted()) {
                startActivity(picIntent)
            } else {
                continueAfterPermission = 1
                requestStoragePermission()
            }
        }
    }

    private fun requestStoragePermission() {
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        ActivityCompat.requestPermissions(
            this,
            permissions,
            MY_PERMISSIONS_REQUEST_STORAGE_PERMISSIONS
        )
    }

    private fun isStoragePermissionGranted(): Boolean
    {
        val granted: Boolean
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("outer if", "executed")
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            ) {
                granted = true
                Log.d("inner if", "executed")
            } else {
                granted = false
                Log.d("inner else", "executed")
            }
        } else {
            granted = true
            Log.d("outer else", "executed")
        }
        return granted
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != MY_PERMISSIONS_REQUEST_STORAGE_PERMISSIONS) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Storage permission granted")
            continueAfterPermissionGrant()
        } else {
            Toast.makeText(this, "You must Grants Storage Permission to continue", Toast.LENGTH_LONG).show()
        }
    }

    private fun continueAfterPermissionGrant() {
        when (continueAfterPermission) {
            1 -> startActivity(picIntent)
        }
        continueAfterPermission = 0
    }
}