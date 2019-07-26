package cz.ackee.resizin;

import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Resizin resizin = new Resizin("ackee");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView img = findViewById(R.id.image);

        /*
         * If we want to get view dimensions we must wait until its drawn
         */
        img.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                img.getViewTreeObserver().removeOnPreDrawListener(this);

                String url = resizin.url()
                    .crop(Crop.FACE)
                    .width(img.getWidth())
                    .height(img.getHeight())
                    .generate("player");

                Picasso.get()
                    .load(url)
                    .into(img);
                return false;
            }
        });
    }
}
