package com.example.garbagecollection

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Image_upload : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_upload)

        val image=findViewById<ImageView>(R.id.img1)

//        val uri:Uri =intent.getParcelableExtra("img")!!
//        image.setImageURI(uri)
        val bit=intent.getParcelableExtra<Bitmap>("bit")
        image.setImageBitmap(bit)
    }
}