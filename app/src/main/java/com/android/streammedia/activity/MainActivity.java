package com.android.streammedia.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.streammedia.R;
import com.android.streammedia.picture.LoadPhotoActivity;

public class MainActivity extends Activity implements View.OnClickListener{

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, LoadPhotoActivity.class));
    }

}
