package com.example.simple_image_gallery

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.CodeBoy.MediaFacer.MediaFacer
import com.CodeBoy.MediaFacer.PictureGet
import com.CodeBoy.MediaFacer.mediaHolders.pictureContent
import com.CodeBoy.MediaFacer.mediaHolders.pictureFolderContent
import com.example.simple_image_gallery.adapter.ImageRecycleAdapter
import kotlinx.android.synthetic.main.activity_picture.*

class PictureActivity : AppCompatActivity() {

    private lateinit var allphotos : ArrayList<pictureContent>
    private lateinit var folderSpinner: Spinner
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)

        fun calculateNoOfColumns(context: Context, columnWidthDp: Float): Int {

            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            val screenWidthDp: Float = displayMetrics.widthPixels / displayMetrics.density
            return  (screenWidthDp / columnWidthDp + 0.5).toInt()

        }

        recyclerView = findViewById(R.id.recycler_view)               //1. Initialize Recycler View
        recyclerView.hasFixedSize()
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        val numbsOfColumns: Int = calculateNoOfColumns(this, 90F)
        recyclerView.layoutManager = GridLayoutManager(this, numbsOfColumns)    //2.Set Layout for Recycler View

//        allphotos = MediaFacer                                                          //3.Populating List with items
//            .withPictureContex(this)
//            .getAllPictureContents(PictureGet.externalContentUri)

        allphotos = ArrayList()
        setupFolderSelector();


    }

    private fun setUpAndDisplayPictures(){

        val pictureAdapter = ImageRecycleAdapter(this, allphotos)         //4.Calling adapter constructor and pass the list
        recyclerView.adapter = pictureAdapter                                          //5.Set adapter for recycler view


    }

    private fun setupFolderSelector(){

        folderSpinner = folder_spinner

        val pictureFolder: ArrayList<pictureFolderContent> = ArrayList()
        pictureFolder.add(pictureFolderContent("all", "*All"))
        pictureFolder.addAll(MediaFacer.withPictureContex(this).absolutePictureFolders)

        val folders: ArrayList<String> = ArrayList();
        for (i in 0 until pictureFolder.size){

            folders.add(pictureFolder[i].folderName)

        }

        val selectorAdapter: ArrayAdapter<String> = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, folders)
        folderSpinner.adapter = selectorAdapter

        folderSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (folders[position] == "*All*") {
                    allphotos = MediaFacer
                        .withPictureContex(this@PictureActivity)
                        .getAllPictureContents(PictureGet.externalContentUri)
                    Toast.makeText(
                        this@PictureActivity,
                        allphotos.size.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                    setUpAndDisplayPictures()
                } else {
                    allphotos = pictureFolder.get(position).getPhotos()
                    /* allPhotos = MediaFacer
                                .withPictureContex(pictureActivity.this)
                                .getAllPictureContentByBucket_id(pictureFolders.get(position).getBucket_id());*/Toast.makeText(
                        this@PictureActivity,
                        allphotos.size.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                    setUpAndDisplayPictures()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }


}