package com.schokoladenbrown;

import android.graphics.Bitmap;

public class Smooth {

    public static Bitmap rescale(Bitmap src, int dstWidth, int dstHeight) {
        Bitmap dst = Bitmap.createBitmap(dstWidth, dstHeight, src.getConfig());
        native_rescale(src, dst);
        return dst;
    }

    static {
        System.loadLibrary("smoothrescale");
    }

    private static native void native_rescale(Bitmap src, Bitmap dst);

}
