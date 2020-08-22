package com.example.simple_image_gallery.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.CodeBoy.MediaFacer.mediaHolders.pictureContent
import com.bumptech.glide.Glide
import com.example.simple_image_gallery.R
import kotlinx.android.synthetic.main.picture_item.view.*
import java.util.ArrayList

class ImageRecycleAdapter(private val pictureActivity: Context, private val pictureList: ArrayList<pictureContent>): RecyclerView.Adapter<ImageRecycleAdapter.PictureViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageRecycleAdapter.PictureViewHolder {
        return PictureViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.picture_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return pictureList.size
    }

    override fun onBindViewHolder(holder: ImageRecycleAdapter.PictureViewHolder, position: Int) {
        holder.position = position
        holder.bind()
    }
    
    
    inner class PictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var picture: ImageView = itemView.picture
        private var pos: Int = 0

        fun setPosition(pos: Int){
            this.pos = pos
        }

        fun bind(){

            val pic: pictureContent = pictureList[pos]
            Glide.with(pictureActivity)
                .load(Uri.parse(pic.assertFileStringUri))
                .into(picture)

        }

    }
}