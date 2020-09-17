package com.example.simple_image_gallery

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var picIntent: Intent
    private val PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
    private val MY_PERMISSIONS_READ_STORAGE_PERMISSIONS = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        picIntent = Intent(this@MainActivity, PictureActivity::class.java)

        val mPicButton = findViewById<Button>(R.id.btn_get_image)
        mPicButton.setOnClickListener {

            checkPermissions()

        }
    }

    private fun checkPermissions() {

        val permissions = arrayOf(PERMISSION)

        if (ContextCompat.checkSelfPermission(this@MainActivity, PERMISSION)
                        == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this@MainActivity,
                                                permissions, MY_PERMISSIONS_READ_STORAGE_PERMISSIONS)
        } else {
            startActivity(picIntent)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != MY_PERMISSIONS_READ_STORAGE_PERMISSIONS) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startActivity(picIntent)
        } else {
            Toast.makeText(this, "You must Grants Storage Permission to continue", Toast.LENGTH_LONG).show()
        }
    }

}