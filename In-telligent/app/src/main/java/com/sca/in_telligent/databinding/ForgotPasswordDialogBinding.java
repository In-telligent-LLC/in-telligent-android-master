package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ForgotPasswordDialogBinding implements ViewBinding {
    public final TextView forgotPasswordCancel;
    public final EditText forgotPasswordEmail;
    public final TextView forgotPasswordMessage;
    public final TextView forgotPasswordSubmit;
    public final TextView forgotPasswordTitle;
    private final ConstraintLayout rootView;

    private ForgotPasswordDialogBinding(ConstraintLayout constraintLayout, TextView textView, EditText editText, TextView textView2, TextView textView3, TextView textView4) {
        this.rootView = constraintLayout;
        this.forgotPasswordCancel = textView;
        this.forgotPasswordEmail = editText;
        this.forgotPasswordMessage = textView2;
        this.forgotPasswordSubmit = textView3;
        this.forgotPasswordTitle = textView4;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ForgotPasswordDialogBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ForgotPasswordDialogBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.forgot_password_dialog, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ForgotPasswordDialogBinding bind(View view) {
        int i = R.id.forgotPasswordCancel;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.forgotPasswordCancel);
        if (textView != null) {
            i = R.id.forgotPasswordEmail;
            EditText editText = (EditText) ViewBindings.findChildViewById(view, (int) R.id.forgotPasswordEmail);
            if (editText != null) {
                i = R.id.forgotPasswordMessage;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.forgotPasswordMessage);
                if (textView2 != null) {
                    i = R.id.forgotPasswordSubmit;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.forgotPasswordSubmit);
                    if (textView3 != null) {
                        i = R.id.forgotPasswordTitle;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.forgotPasswordTitle);
                        if (textView4 != null) {
                            return new ForgotPasswordDialogBinding((ConstraintLayout) view, textView, editText, textView2, textView3, textView4);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
