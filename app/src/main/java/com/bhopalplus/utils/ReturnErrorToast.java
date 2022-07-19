package com.bhopalplus.utils;


import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bhopalplus.R;

public  abstract class ReturnErrorToast {


        public static void showToast( Activity context) {

            LayoutInflater infl = context.getLayoutInflater();
            View layout = infl.inflate(R.layout.toast_layout , context.findViewById(R.id.toast_layout_root));

            TextView text = layout.findViewById(R.id.text);
            text.setText("Something went wrong");

            Toast toast = new Toast(context);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }



        public static void showWarningToast(Activity context,String msg) {

            LayoutInflater infl = context.getLayoutInflater();
            View layout = infl.inflate(R.layout.warning_toast, context.findViewById(R.id.toast_layout_root));

            TextView text = layout.findViewById(R.id.text);
            text.setText(msg);

            Toast toast = new Toast(context);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
    }

