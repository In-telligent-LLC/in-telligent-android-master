package com.sca.in_telligent.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.StringRes;

/**
 * Created by Marcos Ambrosi on 1/24/19.
 */
public class AlertUtil {

    public static void showConfirmationAlert(Context context,
                                             CharSequence title,
                                             CharSequence message,
                                             DialogInterface.OnClickListener okClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, okClickListener);
        builder.setNegativeButton(android.R.string.cancel, null);
        Dialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public static void showConfirmationAlert(Context context, @StringRes int message, DialogInterface.OnClickListener onPositiveClickListener) {
        showConfirmationAlert(context, null, context.getString(message), onPositiveClickListener);
    }
}
