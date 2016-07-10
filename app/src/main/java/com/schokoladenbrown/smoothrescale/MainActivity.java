package com.schokoladenbrown.smoothrescale;

import com.schokoladenbrown.Smooth;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri uri = getIntent().getData();

        if(uri == null) {
            return;
        }

        ImageView iv = (ImageView) findViewById(R.id.image_view);
        ImageView sciv = (ImageView) findViewById(R.id.scaled_image_view);

        try {
            final Bitmap orig = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            final int dstWidth = orig.getWidth() / 13;
            final int dstHeight = orig.getHeight() / 13;

            measure(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run system no filter");
                    Bitmap.createScaledBitmap(orig, dstWidth, dstHeight, false);
                }
            });

            measure(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run system filter");
                    Bitmap.createScaledBitmap(orig, dstWidth, dstHeight, true);
                }
            });

            for(final Smooth.Algo a : Smooth.Algo.values()) {
                measure(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run " + a.name());
                        Smooth.rescale(orig, dstWidth, dstHeight, a);
                    }
                });
            }



            iv.setImageBitmap(orig);
            sciv.setImageBitmap(Smooth.rescale(orig, dstWidth, dstHeight, Smooth.AlgoParametrized1.LANCZOS, 1.0));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void measure(Runnable r) {
        long notch = SystemClock.uptimeMillis();
        r.run();
        Log.d(TAG, "measure: " + (SystemClock.uptimeMillis() - notch) + "ms");
    }

}
