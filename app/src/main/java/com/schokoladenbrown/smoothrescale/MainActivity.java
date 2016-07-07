package com.schokoladenbrown.smoothrescale;

import com.schokoladenbrown.Smooth;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap bm = Bitmap.createBitmap(51, 113, Bitmap.Config.ARGB_8888);
        bm = Smooth.rescale(bm, 25, 25);

        bm = Bitmap.createBitmap(51, 113, Bitmap.Config.ARGB_4444);
        bm = Smooth.rescale(bm, 25, 25);

        bm = Bitmap.createBitmap(51, 113, Bitmap.Config.RGB_565);
        bm = Smooth.rescale(bm, 25, 25);

        bm = Bitmap.createBitmap(51, 113, Bitmap.Config.ALPHA_8);
        bm = Smooth.rescale(bm, 25, 25);

        ImageView iv = (ImageView) findViewById(R.id.image_view);
        iv.setImageBitmap(bm);
    }
}
