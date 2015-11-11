package com.softtanck.imagehead.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by winterfell on 11/11/2015.
 */
public class CameraHelper {


    /**
     * 从相机获取图片
     *
     * @param requestCode
     * @param localrequestCode
     * @param data
     */
    public static Bitmap getImageFormCamera(int requestCode, int localrequestCode, Intent data, Context context) {

        if (requestCode != localrequestCode) {
            throw new IllegalArgumentException("requestCode not equal localrequestCode");
        }

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
//            Log.d("Tanck", "---->" + bitmap);
//            String fileName = context.getExternalCacheDir().getPath() + File.separator + "test.jpg";
//            Log.d("Tanck", "fileName:" + fileName);
//            File file = new File(fileName);
//            FileOutputStream fos = null;
//            try {
//                fos = new FileOutputStream(file);
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    fos.flush();
//                    fos.close();
//                    if (!bitmap.isRecycled()) {
//                        bitmap.recycle();
//                        bitmap = null;
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return fileName;
//        }
//
//        return null;

            return bitmap;
        }
        return null;

    }
}
