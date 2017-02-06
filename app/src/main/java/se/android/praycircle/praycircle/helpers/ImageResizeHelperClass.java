package se.android.praycircle.praycircle.helpers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/** * Created by Paulo Vila Nova on 2017-02-06.
 */

public class ImageResizeHelperClass {

    public static enum ScalingLogic {
        CROP, FIT
    }

    public static Bitmap decodeFile(String path, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth, dstHeight, scalingLogic);

        Bitmap unscaledBitmap = BitmapFactory.decodeFile(path, options);

        ExifInterface ei = null;
        try {
            ei = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                Log.d("rotation", "90");
                unscaledBitmap = rotateImage(unscaledBitmap, 90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                Log.d("rotation", "180");
                unscaledBitmap = rotateImage(unscaledBitmap, 180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                Log.d("rotation", "270");
                unscaledBitmap = rotateImage(unscaledBitmap, 270);
                break;
        }

        return unscaledBitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {

        Bitmap bitmap = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                    matrix, true);
        } catch (OutOfMemoryError err) {
            err.printStackTrace();
        }
        return bitmap;
    }

    public static int calculateSampleSize(int srcWidth, int srcHeight, int dstWidth, int dstHeight,
                                          ScalingLogic scalingLogic) {
        if (scalingLogic == ScalingLogic.FIT) {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstHeight;

            if (srcAspect > dstAspect) {
                return srcWidth / dstWidth;
            } else {
                return srcHeight / dstHeight;
            }
        } else {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstHeight;

            if (srcAspect > dstAspect) {
                return srcHeight / dstHeight;
            } else {
                return srcWidth / dstWidth;
            }
        }
    }

    public static String decodeFilePath(String path,int DESIREDWIDTH, int DESIREDHEIGHT) {
        String strMyImagePath = null;
        Bitmap scaledBitmap = null;

        try {
            // Part 1: Decode image
            Bitmap unscaledBitmap = decodeFile(path, DESIREDWIDTH, DESIREDHEIGHT, ScalingLogic.FIT);

            if (!(unscaledBitmap.getWidth() <= DESIREDWIDTH && unscaledBitmap.getHeight() <= DESIREDHEIGHT)) {
                // Part 2: Scale image
                scaledBitmap = createScaledBitmap(unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT, ScalingLogic.FIT);
            } else {
                unscaledBitmap.recycle();
                return path;
            }

            // Store to tmp file

//            String extr = Environment.getExternalStorageDirectory().toString();
//            File mFolder = new File(extr + UtilImage.TEMP_FILE_JPG);
            File tempDir = Environment.getExternalStorageDirectory();
            File tempImg = new File(tempDir.getAbsolutePath() , HelperClass.TEMP_FILE_JPG);
            if (!tempImg.exists()) {
                tempImg.createNewFile();
            }

//            String s = "tmp.png";
//
//
//            File f = new File(mFolder.getAbsolutePath(),s);

           /* File tempDir = Environment.getExternalStorageDirectory();
            File tempImg = new File(tempDir.getAbsolutePath() + UtilImage.TEMP_FILE_JPG);
            if (tempImg.exists())
                strMyImagePath =  Uri.fromFile(tempImg).getPath();*/

            strMyImagePath = tempImg.getAbsolutePath();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(tempImg);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (Exception e) {

                e.printStackTrace();
            }

            scaledBitmap.recycle();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        if (strMyImagePath == null) {
            return path;
        }
        return strMyImagePath;

    }

    public static Uri getCameraTempFile() {
        File tempDir = Environment.getExternalStorageDirectory();
        File tempImg = new File(tempDir.getAbsolutePath() , HelperClass.TEMP_FILE_JPG);
        if (tempImg.exists())
            return Uri.fromFile(tempImg);
        return null;
    }

    public static String decodeInputStream(Activity activity, int drawableresource){
        File f = null;

        try {
            Drawable drawable = activity.getResources().getDrawable(drawableresource);
            BitmapDrawable bitmapDrawable = ((BitmapDrawable) drawable);
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream); //use the compression format of your need

            f = new File(activity.getCacheDir(), HelperClass.TEMP_FILE_JPG);
            f.createNewFile();


            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(stream.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f.getAbsolutePath();
    }

    public static String decodeFilePathCamera(String path,int DESIREDWIDTH, int DESIREDHEIGHT) {
        String strMyImagePath = null;
        Bitmap scaledBitmap = null;

        try {
            // Part 1: Decode image
            Bitmap unscaledBitmap = decodeFile(path, DESIREDWIDTH, DESIREDHEIGHT, ScalingLogic.FIT);

            if(unscaledBitmap == null){
                Log.d("uploadimage", "ITS NULL MOFOOO!");
            }

            if (!(unscaledBitmap.getWidth() <= DESIREDWIDTH && unscaledBitmap.getHeight() <= DESIREDHEIGHT)) {
                // Part 2: Scale image
                scaledBitmap = createScaledBitmap(unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT, ScalingLogic.FIT);
            } else {
                unscaledBitmap.recycle();
                return path;
            }

            // Store to tmp file
          /*  File tempDir = Environment.getExternalStorageDirectory();
            File tempImg = new File(tempDir.getAbsolutePath() + UtilImage.TEMP_FILE_JPG);
            if (tempImg.exists())
                strMyImagePath =  tempImg.getAbsolutePath();*/

            File tempDir = Environment.getExternalStorageDirectory();
            File tempImg = new File(tempDir.getAbsolutePath() , HelperClass.TEMP_FILE_JPG);
            if (!tempImg.exists()) {
                tempImg.createNewFile();
            }

            /*String extr = Environment.getExternalStorageDirectory().toString();
            File mFolder = new File(extr + UtilImage.TEMP_FILE_JPG);
            if (!mFolder.exists()) {
                mFolder.mkdir();
            }

            File f = new File(mFolder.getAbsolutePath());*/

            strMyImagePath = tempImg.getAbsolutePath();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(tempImg);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (Exception e) {

                e.printStackTrace();
            }

            scaledBitmap.recycle();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        if (strMyImagePath == null) {
            return path;
        }
        return strMyImagePath;

    }

    public static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight,
                                        ScalingLogic scalingLogic) {
        if (scalingLogic == ScalingLogic.CROP) {
            final float srcAspect = (float)srcWidth / (float)srcHeight;
            final float dstAspect = (float)dstWidth / (float)dstHeight;

            if (srcAspect > dstAspect) {
                final int srcRectWidth = (int)(srcHeight * dstAspect);
                final int srcRectLeft = (srcWidth - srcRectWidth) / 2;
                return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight);
            } else {
                final int srcRectHeight = (int)(srcWidth / dstAspect);
                final int scrRectTop = (int)(srcHeight - srcRectHeight) / 2;
                return new Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight);
            }
        } else {
            return new Rect(0, 0, srcWidth, srcHeight);
        }
    }

    public static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight,
                                        ScalingLogic scalingLogic) {
        if (scalingLogic == ScalingLogic.FIT) {
            final float srcAspect = (float)srcWidth / (float)srcHeight;
            final float dstAspect = (float)dstWidth / (float)dstHeight;

            if (srcAspect > dstAspect) {
                return new Rect(0, 0, dstWidth, (int)(dstWidth / srcAspect));
            } else {
                return new Rect(0, 0, (int)(dstHeight * srcAspect), dstHeight);
            }
        } else {
            return new Rect(0, 0, dstWidth, dstHeight);
        }
    }
    public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight,
                                            ScalingLogic scalingLogic) {
        Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(),
                dstWidth, dstHeight, scalingLogic);
        Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(),
                dstWidth, dstHeight, scalingLogic);
        Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }
}
