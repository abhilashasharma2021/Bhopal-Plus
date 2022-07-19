package com.bhopalplus.utils.MyDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.WindowManager;

public class CustomDialog  implements DialogInterface {
    public Dialog dialog;

    @Override
    public void showDialog(int view, Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        try {
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            Log.e("dskjdsjd", "xsx" + e.getMessage(), e);
            if ((this.dialog != null) && this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
        }

    }

    @Override
    public void hideDialog() {
        if ((this.dialog != null) && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }
}