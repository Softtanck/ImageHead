package com.softtanck.imagehead;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author : Tanck
 * @version : 1.0
 * @Description : // TODO 特殊的界面
 * @date : 2015/2/5 13:36
 * @name : lb-enduser-android
 */
public class SelectPicPopupWindow extends Activity implements View.OnClickListener {

    private LinearLayout btn_take_photo, btn_pick_photo, btn_cancel;

    private LinearLayout lyt_desc;

    private Intent backData;//返回数据
    /**
     * 拍照
     */
    public static final int TAKE_PHONE = 101;

    /**
     * key
     */
    public static final String IMG_KEY = "IMG_KEY";

    /**
     * 图库选择
     */
    public static final int PICK_PHONE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_selector);
        btn_take_photo = (LinearLayout) this.findViewById(R.id.btn_take_photo);
        btn_pick_photo = (LinearLayout) this.findViewById(R.id.btn_pick_photo);
        btn_cancel = (LinearLayout) this.findViewById(R.id.btn_cancel);
        lyt_desc = (LinearLayout) this.findViewById(R.id.pic_selector_title_desc);

        // 添加按钮监听
        btn_cancel.setOnClickListener(this);
        btn_pick_photo.setOnClickListener(this);
        btn_take_photo.setOnClickListener(this);
    }

    // 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_take_photo:
                backData = new Intent();
                backData.putExtra(IMG_KEY, TAKE_PHONE);
                setResult(RESULT_OK, backData);
                break;
            case R.id.btn_pick_photo:
                backData = new Intent();
                backData.putExtra(IMG_KEY, PICK_PHONE);
                setResult(RESULT_OK, backData);
                break;
            case R.id.btn_cancel:
                break;
        }
        finish();
        overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
    }
}
