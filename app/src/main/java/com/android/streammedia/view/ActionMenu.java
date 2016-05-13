package com.android.streammedia.view;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;


import com.android.streammedia.tools.Utils;

import java.util.ArrayList;

/**
 * Created by litonghui on 2016/5/13.
 */
public class ActionMenu extends FrameLayout {

    private Context mContext;

    private ArrayList<MenuItem> mMenuItems;

    private ArrayList<MenuItemView> mMenuItemViews;


    public ActionMenu(Builder builder){
        super(builder.context);
        this.mContext = builder.context;
        this.mMenuItems = builder.menuItems;
        init();
    }

    public ActionMenu(Context context) {
        this(context,null);
    }

    public ActionMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(){
        //generate and add menuItemViews
        mMenuItemViews = generateMenuItemViews();
        for (MenuItemView menuItemView : mMenuItemViews) {
            addView(menuItemView);
        }
        mMenuItemViews.get(0).bringToFront();
    }

    private ArrayList<MenuItemView> generateMenuItemViews() {
        ArrayList<MenuItemView> menuItemViews = new ArrayList<>(mMenuItems.size());
        for (MenuItem item : mMenuItems) {
            MenuItemView menuItemView = new MenuItemView(mContext, item);
            menuItemView.setLayoutParams(Utils.createWrapParams());
            menuItemView.setOnClickListener(item.getOnClickListener());
            menuItemViews.add(menuItemView);
        }
        return menuItemViews;
    }

    public static class Builder {

        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        private ArrayList<MenuItem> menuItems = new ArrayList<>();

        public ActionMenu build() {
            return new ActionMenu(this);
        }

        public Builder addMenuItem(@ColorRes int bgColor, int icon, String label,
                                   @ColorRes int textColor, OnClickListener onClickListener) {
            menuItems.add(new MenuItem(bgColor, icon, label, textColor, onClickListener));
            return this;
        }
    }

}
