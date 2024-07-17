package com.example.smartlamp.MyDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smartlamp.ColorView.ColorPickView;
import com.example.smartlamp.R;
import com.example.smartlamp.ToastLogShow.ToastLogShow;

public class MyDialog extends Dialog {
    Button cancelBtn, confirmBtn;
    TextView colordemoTv;
    String ColorValueStr = "-65535";
    ColorPickView colorPickView;

    public MyDialog(@NonNull Context context,MydialogInterface mydialogInterface) {
        super(context);
        this.setContentView(R.layout.customdialogframelayout);
        this.setTitle("Custom Dialog");
        cancelBtn = findViewById(R.id.cancelBtn);
        confirmBtn = findViewById(R.id.confirmBtn);
        colordemoTv = findViewById(R.id.colordemoTv);
        colorPickView = findViewById(R.id.colorPickView);
        cancelBtn.setOnClickListener(view -> {
            this.dismiss();
        });
        confirmBtn.setOnClickListener(view -> {
            mydialogInterface.ConfirmClickHandeler(this.ColorValueStr);
        });
        colorPickView.setBarListener(color -> {
            colordemoTv.setBackgroundColor(color);
            colordemoTv.setText(String.valueOf(color));
            int alpha = (color >> 24) & 0xFF;   // 右移24位获取alpha值
            int red = (color >> 16) & 0xFF;     // 右移16位获取red值
            int green = (color >> 8) & 0xFF;    // 右移8位获取green值
            int blue = color & 0xFF;            // 获取blue值
            ColorValueStr = alpha + "," + red + "," + green + ","+ blue;
        });
        // Calculate dialog width as 85% of screen width
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int screenWidth = display.getWidth(); // deprecated, but still valid for width in pixels
        int dialogWidth = (int) (screenWidth * 0.85);

        getWindow().setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        int cornerRadius = 20; // 20px 圆角
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(
                new float[] { cornerRadius, cornerRadius, cornerRadius, cornerRadius,
                        cornerRadius, cornerRadius, cornerRadius, cornerRadius },
                null,
                null));
        shapeDrawable.getPaint().setColor(Color.WHITE); // 设置背景颜色
        getWindow().setBackgroundDrawable(shapeDrawable);
    }
    public MyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
