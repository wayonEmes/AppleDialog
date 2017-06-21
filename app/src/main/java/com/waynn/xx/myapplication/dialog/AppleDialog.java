package com.waynn.xx.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.waynn.xx.myapplication.R;

/**
 * Created by apple on 2017/6/21.
 */

public class AppleDialog {

    private final Dialog dialog;
    private final LinearLayoutCompat ll_content;
    private final Builder builder;

    private AppleDialog(Builder builder) {
        this.builder = builder;
        dialog = config();
        View contentView = LayoutInflater.from(builder.context).inflate(R.layout.layout_dialog, null);
        dialog.setContentView(contentView);
        MyOnClickListener myOnClickListener = new MyOnClickListener();
        //至少两条时显示
        ll_content = (LinearLayoutCompat) contentView.findViewById(R.id.ll_content);
        for (int i = 0; i < builder.items.length; i++) {
            TextView textView = (TextView) LayoutInflater.from(builder.context).inflate(R.layout.item_dialog, null);
            textView.setText(builder.items[i]);
            textView.setBackgroundResource(getItemBgResId(builder.items.length, i));
            textView.setOnClickListener(myOnClickListener);
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 2, 0, 0);
            ll_content.addView(textView, params);
        }
        TextView bt_dismiss = (TextView) contentView.findViewById(R.id.bt_dismiss);//只有一条时显示
        bt_dismiss.setOnClickListener(myOnClickListener);
        dialog.show();
    }

    private int getItemBgResId(int length, int index) {
        int itemBgResId;
        if (length == 1) {
            itemBgResId = R.drawable.btn_dialog;
        } else {
            if (index == 0) {
                itemBgResId = R.drawable.btn_dialog_top;
            } else if (index == length - 1) {
                itemBgResId = R.drawable.btn_dialog_bottom;
            } else {
                itemBgResId = R.drawable.btn_dialog_center;
            }
        }
        return itemBgResId;
    }

    private Dialog config() {
        Dialog dialog;
        dialog = new Dialog(builder.context);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        //去除Holo主题下Dialog上面的蓝色线条
        try {
            int dividerID = builder.context.getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = dialog.findViewById(dividerID);
            divider.setBackgroundColor(Color.TRANSPARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            window.setWindowAnimations(R.style.AnimPopup);  //添加动画
        }
        return dialog;
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (ll_content != null && builder.listener != null) {
                int index = ll_content.indexOfChild(v);
                if (builder.listener != null) {
                    builder.listener.onClick(index);
                }
            }
            hide();
        }
    }

    private void initDialog() {

    }

    public void hide() {
        if (dialog != null) {
            dialog.hide();
        }
    }

    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public interface ItemOnClickListener {
        void onClick(int index);
    }

    public static class Builder {
        private final Context context;
        private CharSequence[] items;
        private ItemOnClickListener listener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setItems(CharSequence[] items, final ItemOnClickListener listener) {
            this.items = items;
            this.listener = listener;
            return this;
        }

        public AppleDialog create() {
            return new AppleDialog(this);
        }

    }
}
