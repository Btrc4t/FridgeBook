package com.buttercat.fridgebook.view.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

/**
 * {@link Transformation} used to create a circular image
 */
public class CircleTransform implements Transformation {

    /**
     * Constant representing a float value of 2
     */
    private static final float TWO_FLOAT = 2f;
    /**
     * Constant representing a value of 2
     */
    private static final float TWO = 2;

    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (int) ((source.getWidth() - size) / TWO);
        int y = (int) ((source.getHeight() - size) / TWO);

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / TWO_FLOAT;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();

        return bitmap;
    }

    @Override
    public String key() {
        return CircleTransform.class.getSimpleName();
    }
}
