package com.android.streammedia.picture;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.streammedia.R;
import com.android.streammedia.tools.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by litonghui on 2016/5/15.
 */
public class LoadPhotoActivity extends Activity {

    public static final String ImageAvatarPath = "/sdcard/streammedia/";

    private final static String EXTRA_RESTORE_PHOTO = "extra_restore_photo";


    public static final int PHOTO_REQUEST_TAKEPHOTO = 0;

    public static final int PHOTO_REQUEST_GALLERY = 1;

    public static final int PHOTO_REQUEST_CUT = 3;

    public static final int  SCALE = 5;

    private ImageView mImageView;

    private CheckBox mCheckBox;

    private String mImageName;

    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        init();
    }

    private void init() {
        mActivity = this;
        mImageView = (ImageView) findViewById(R.id.iv_photo);
        mCheckBox = (CheckBox) findViewById(R.id.check_crop);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCamera();
            }
        });
    }

    private void showCamera() {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.alertdialog);
        // 为确认按钮添加事件,执行退出应用操作
        TextView tv_paizhao = (TextView) window.findViewById(R.id.tv_content1);
        tv_paizhao.setText("拍照");
        tv_paizhao.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SdCardPath")
            public void onClick(View v) {
                if (Utils.hasCamera(mActivity)) {
                    mImageName = Utils.getNowTime() + ".png";
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // 指定调用相机拍照后照片的储存路径
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(Utils.createOrOpen(ImageAvatarPath, mImageName)));
                    startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
                } else {
                    Toast.makeText(mActivity, "No Find Camera !", Toast.LENGTH_SHORT).show();
                }
                dlg.cancel();
            }
        });
        TextView tv_xiangce = (TextView) window.findViewById(R.id.tv_content2);
        tv_xiangce.setText("相册");
        tv_xiangce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mImageName = Utils.getNowTime() + ".png";
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);

                dlg.cancel();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_REQUEST_TAKEPHOTO:
                    if (mCheckBox.isChecked()) {
                        startPhotoZoom(
                                Uri.fromFile(Utils.createOrOpen(ImageAvatarPath, mImageName)),
                                480);
                    } else {
                        startPhotoSave(Uri.fromFile(Utils.createOrOpen(ImageAvatarPath, mImageName)));
                    }
                    break;
                case PHOTO_REQUEST_GALLERY:
                    if(data != null) {
                        if (mCheckBox.isChecked()) {
                            startPhotoZoom(data.getData(), 480);
                        } else {
                            startPhotoSave(data.getData());
                        }
                    }
                    break;
                case PHOTO_REQUEST_CUT:
                    BitmapFactory.Options options = new BitmapFactory.Options();

                    /**
                     * 最关键在此，把options.inJustDecodeBounds = true;
                     * 这里再decodeFile()，返回的bitmap为空
                     * ，但此时调用options.outHeight时，已经包含了图片的高了
                     // */
                    options.inJustDecodeBounds = true;
                    Bitmap bitmap = BitmapFactory.decodeFile(ImageAvatarPath
                            + mImageName);
                    mImageView.setImageBitmap(bitmap);
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("SdCardPath")
    private void startPhotoZoom(Uri uri1, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri1, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", false);

        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(Utils.createOrOpen(ImageAvatarPath, mImageName)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
    private void  startPhotoSave(Uri uri){
        ContentResolver resolver = getContentResolver();
        //照片的原始资源地址
        try {
            //使用ContentProvider通过URI获取原始图片
            Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, uri);
            if (photo != null) {
                Bitmap smallBitmap = Utils.zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
                photo.recycle();  //释放原始图片占用的内存，防止out of memory异常发生
                Utils.save(smallBitmap, Bitmap.CompressFormat.PNG, 1, ImageAvatarPath,mImageName);
                mImageView.setImageBitmap(smallBitmap);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mImageName != null) {
            outState.putSerializable(EXTRA_RESTORE_PHOTO, mImageName);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mImageName = (String) savedInstanceState.getSerializable(EXTRA_RESTORE_PHOTO);
    }
}
