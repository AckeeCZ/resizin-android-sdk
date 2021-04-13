package io.github.ackeecz.resizin.sample

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import com.squareup.picasso.Picasso
import io.github.ackeecz.resizin.sdk.Crop
import io.github.ackeecz.resizin.sdk.Resizin

class MainActivity : AppCompatActivity() {

    var resizin = Resizin("ackee")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val img = findViewById<ImageView>(R.id.image)

        /*
         * If we want to get view dimensions we must wait until its drawn
         */
        img.doOnPreDraw {
            val url = resizin.url()
                .crop(Crop.FACE)
                .width(img.width)
                .height(img.height)
                .generate("player")
            Picasso.get()
                .load(url)
                .into(img)
        }
    }
}