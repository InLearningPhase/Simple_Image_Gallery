package com.example.simple_image_gallery

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.CodeBoy.MediaFacer.MediaFacer
import com.CodeBoy.MediaFacer.PictureGet
import com.CodeBoy.MediaFacer.mediaHolders.pictureContent
import com.example.simple_image_gallery.adapter.ImageRecycleAdapter

class PictureActivity : AppCompatActivity() {

    private var allphotos = ArrayList<pictureContent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)

        fun calculateNoOfColumns(context: Context, columnWidthDp: Float): Int {

            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            val screenWidthDp: Float = displayMetrics.widthPixels / displayMetrics.density
            return  (screenWidthDp / columnWidthDp + 0.5).toInt()

        }

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.hasFixedSize()
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        val numbsOfColumns: Int = calculateNoOfColumns(this, 90F)
        recyclerView.layoutManager = GridLayoutManager(this, numbsOfColumns)

        allphotos = MediaFacer
            .withPictureContex(this)
            .getAllPictureContents(PictureGet.externalContentUri)


        val pictureAdapter = ImageRecycleAdapter(this, allphotos)
        recyclerView.adapter = pictureAdapter

    }
}