package com.mmhocr.www.mmhocr;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;

/**
 * Created by mmh on 8/25/15.
 */
public class CallOcrHandler extends Activity{
    private Button callButton;
    private Camera mCamera;
    private Context mContext;


    public void performCrop(Context context) {
        mContext = context;
        try {
            Bitmap image = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() +"/image.jpg");
            DrawView selectionView = (DrawView)((Activity)context).findViewById(R.id.imageView);
            Rect frame = selectionView.getSelection();

            ((Activity)context).setContentView(R.layout.result_layout);
            ImageView customView = (ImageView)((Activity)context).findViewById(R.id.resultImageView);


            //Bitmap resizedbitmap = Bitmap.createBitmap(image, frame.width()-frame.left, frame.height()-frame.top, frame.right-frame.left, frame.bottom-frame.top);
            Bitmap resizedbitmap =  Bitmap.createBitmap(image,100 + frame.left*2, 350 + frame.top*2,(frame.right-frame.left)*2, 2*(frame.bottom-frame.top));



            customView.setImageBitmap(image);
            RecognizeText(resizedbitmap);
            customView.setAlpha(0XFF);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
        }
    }

    private void RecognizeText(Bitmap bitmap) {
        TessBaseAPI baseAPI = new TessBaseAPI();
        String characterWhitelist = "1234567890";
        TessDataManager.initTessTrainedData(mContext);
        baseAPI.init(TessDataManager.getTesseractFolder()+"/", "eng", TessBaseAPI.OEM_TESSERACT_ONLY);
        baseAPI.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);
//        baseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, characterBlacklist);
        baseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, characterWhitelist);
        baseAPI.setImage(ReadFile.readBitmap(bitmap));
        String textResult = baseAPI.getUTF8Text();
        String phoneNumber = textResult.split(" ")[0];
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phoneNumber));// + phoneNumber));
        ((Activity)mContext).startActivity(callIntent);
//        Log.d("Recognized as", textResult);
    }

}
