package com.mmhocr.www.mmhocr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mmh on 8/24/15.
 */
public class TextRegionSelectActivity {

    File image;
    public TextRegionSelectActivity( ) {

    }

    public File createImageFile() throws IOException {

        if(image == null) {
            String imageFileName = "image";
            File storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        }

        return image;
    }

    public void setImage(final DrawView imageview) {
        Bitmap photo = BitmapFactory.decodeFile("/sdcard/Pictures/image.jpg");
        imageview.setImageBitmap(photo);
    }


}
