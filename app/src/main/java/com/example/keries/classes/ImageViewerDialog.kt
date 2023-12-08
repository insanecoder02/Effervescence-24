package com.example.keries.classes

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.keries.R

class ImageViewerDialog(context: Context, private val imageUrl: String) : Dialog(context) {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.image_viewer_layout)

        val rootView = findViewById<RelativeLayout>(R.id.rootView)
        val imageView = findViewById<ImageView>(R.id.enlargedImageView)

        window?.setBackgroundDrawable(ContextCompat.getDrawable(context,android.R.color.transparent))

        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.image_svgrepo_com)
            .error(R.drawable.image_svgrepo_com)
            .transform(CircleCrop())
            .into(imageView)

        rootView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                dismiss()
                return@setOnTouchListener true
            }
            return@setOnTouchListener false
        }
    }
}
