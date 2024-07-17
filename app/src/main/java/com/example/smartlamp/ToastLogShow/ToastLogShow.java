package com.example.smartlamp.ToastLogShow;

import android.content.Context;
import android.widget.Toast;

public class ToastLogShow {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
