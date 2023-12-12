package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityResetPasswordBinding implements ViewBinding {
    public final Button buttonResetPasswordSubmit;
    public final EditText editTextEnterPasswordFirst;
    public final EditText editTextEnterPasswordSecond;
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    public final ImageView logo;
    public final Guideline logoGuidelineLeft;
    public final Guideline logoGuidelineRight;
    private final ConstraintLayout rootView;
    public final TextView textViewPasswordFirst;
    public final TextView textViewPasswordSecond;
    public final TextView textViewResetYourPassword;

    private ActivityResetPasswordBinding(ConstraintLayout constraintLayout, Button button, EditText editText, EditText editText2, Guideline guideline, Guideline guideline2, ImageView imageView, Guideline guideline3, Guideline guideline4, TextView textView, TextView textView2, TextView textView3) {
        this.rootView = constraintLayout;
        this.buttonResetPasswordSubmit = button;
        this.editTextEnterPasswordFirst = editText;
        this.editTextEnterPasswordSecond = editText2;
        this.guidelineLeft = guideline;
        this.guidelineRight = guideline2;
        this.logo = imageView;
        this.logoGuidelineLeft = guideline3;
        this.logoGuidelineRight = guideline4;
        this.textViewPasswordFirst = textView;
        this.textViewPasswordSecond = textView2;
        this.textViewResetYourPassword = textView3;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityResetPasswordBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityResetPasswordBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.activity_reset_password, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ActivityResetPasswordBinding bind(View view) {
        int i = R.id.buttonResetPasswordSubmit;
        Button button = (Button) ViewBindings.findChildViewById(view, (int) R.id.buttonResetPasswordSubmit);
        if (button != null) {
            i = R.id.editTextEnterPasswordFirst;
            EditText editText = (EditText) ViewBindings.findChildViewById(view, (int) R.id.editTextEnterPasswordFirst);
            if (editText != null) {
                i = R.id.editTextEnterPasswordSecond;
                EditText editText2 = (EditText) ViewBindings.findChildViewById(view, (int) R.id.editTextEnterPasswordSecond);
                if (editText2 != null) {
                    i = R.id.guideline_left;
                    Guideline findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.guideline_left);
                    if (findChildViewById != null) {
                        i = R.id.guideline_right;
                        Guideline findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.guideline_right);
                        if (findChildViewById2 != null) {
                            i = R.id.logo;
                            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.logo);
                            if (imageView != null) {
                                i = R.id.logo_guideline_left;
                                Guideline findChildViewById3 = ViewBindings.findChildViewById(view, (int) R.id.logo_guideline_left);
                                if (findChildViewById3 != null) {
                                    i = R.id.logo_guideline_right;
                                    Guideline findChildViewById4 = ViewBindings.findChildViewById(view, (int) R.id.logo_guideline_right);
                                    if (findChildViewById4 != null) {
                                        i = R.id.textViewPasswordFirst;
                                        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.textViewPasswordFirst);
                                        if (textView != null) {
                                            i = R.id.textViewPasswordSecond;
                                            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.textViewPasswordSecond);
                                            if (textView2 != null) {
                                                i = R.id.textViewResetYourPassword;
                                                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.textViewResetYourPassword);
                                                if (textView3 != null) {
                                                    return new ActivityResetPasswordBinding((ConstraintLayout) view, button, editText, editText2, findChildViewById, findChildViewById2, imageView, findChildViewById3, findChildViewById4, textView, textView2, textView3);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
