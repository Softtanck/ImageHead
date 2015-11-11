package com.softtanck.imagehead;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.softtanck.imagehead.utils.CameraHelper;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mCamera, mGallery;

    private ImageView mHead;

    /**
     * 执行相机
     */
    private static final int CAMERA_REQUEST_CODE = 0x1;
    /**
     * 图片裁剪
     */
    private static final int PHOTO_REQUEST_CUT = 0x2;
    /**
     * 执行图库
     */
    private static final int GALLERY_REQUEST_CODE = 0x3;
    /**
     * 图片的操作
     */
    private static final int IMG_OPTION = 0x4;


    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        mHead = (ImageView) findViewById(R.id.iv_head);


        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filename = getExternalCacheDir().getPath() + File.separator + "test.jpg";
            uri = Uri.fromFile(new File(filename));
        } else {
            Log.d("Tanck", "---->" + getCacheDir().getPath());
//            getCacheDir().getPath();
        }
    }


    public void changeHead(View view) {
        // TODO 分为相机.图库
        Intent dialog = new Intent(MainActivity.this, SelectPicPopupWindow.class);
        startActivityForResult(dialog, IMG_OPTION);
        overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);


//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        View dialogView = View.inflate(MainActivity.this, R.layout.dialog_chose, null);
//        mCamera = (Button) dialogView.findViewById(R.id.btn_camera);
//        mGallery = (Button) dialogView.findViewById(R.id.btn_gallery);
//        mCamera.setOnClickListener(this);
//        mGallery.setOnClickListener(this);
//        builder.setView(dialogView);
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera: // 相机
                Log.d("Tanck", "onclick--->camera");
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(camera, CAMERA_REQUEST_CODE);
                break;
            case R.id.btn_gallery://图库
                Log.d("Tanck", "onclick---->gallery");
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                gallery.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("Tanck", "requestCode:" + requestCode + "----" + "resultCode:" + resultCode);

        //响应成功
        if (MainActivity.RESULT_OK != resultCode)
            return;

        switch (requestCode) {

            case IMG_OPTION:// option
                int code = data.getIntExtra(SelectPicPopupWindow.IMG_KEY, 0);
                switch (code) {
                    case SelectPicPopupWindow.TAKE_PHONE: // 照相机
                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(camera, CAMERA_REQUEST_CODE);
                        return;

                    case SelectPicPopupWindow.PICK_PHONE:// 图库
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        gallery.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(gallery, GALLERY_REQUEST_CODE);
                        return;
                }
                break;

            case CAMERA_REQUEST_CODE:
                startPhotoZoom(uri);
                break;

            case PHOTO_REQUEST_CUT:
                Bitmap head = data.getParcelableExtra("data");
                Log.d("Tanck", "head:" + head);
                mHead.setImageBitmap(head);
                break;

            case GALLERY_REQUEST_CODE:
                Uri galleryUri = data.getData();
                startPhotoZoom(galleryUri);
                break;
        }

    }


    /**
     * 图片处理
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        //设置是否裁剪
        intent.putExtra("crop", "true");

        //设置宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        //设置裁剪图片的宽高：影响效果
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        intent.putExtra("output", uri);
        intent.putExtra("outputFormat", "JPEG");
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
}
