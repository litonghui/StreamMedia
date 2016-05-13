package com.android.streammedia.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.android.streammedia.R;
import com.android.streammedia.view.ActionMenu;

public class MainActivity extends Activity implements View.OnClickListener{

    private ActionMenu mActionMenu;

    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout  = (LinearLayout) findViewById(R.id.ly_main);
        initView();
    }
    private void initView(){
        mActionMenu = new ActionMenu.Builder(this)
                .addMenuItem(R.color.photo, R.mipmap.ic_messaging_posttype_photo, "Photo", R.color.text_color,this)
                .addMenuItem(R.color.chat, R.mipmap.ic_messaging_posttype_chat, "Chat", R.color.text_color,this)
                .addMenuItem(R.color.quote, R.mipmap.ic_messaging_posttype_quote, "Quote", R.color.text_color,this)
                .addMenuItem(R.color.link, R.mipmap.ic_messaging_posttype_link, "Link", R.color.text_color,this)
                .addMenuItem(R.color.audio, R.mipmap.ic_messaging_posttype_audio, "Audio", R.color.text_color,this)
                .addMenuItem(R.color.text, R.mipmap.ic_messaging_posttype_text, "Text", R.color.text_color,this)
                .addMenuItem(R.color.video, R.mipmap.ic_messaging_posttype_video, "Video", R.color.text_color,this)
                .build();
        LinearLayout.LayoutParams labelLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        labelLp.gravity = Gravity.CENTER_HORIZONTAL;
        mLayout.addView(mActionMenu,labelLp);
    }

    @Override
    public void onClick(View v) {

    }

}