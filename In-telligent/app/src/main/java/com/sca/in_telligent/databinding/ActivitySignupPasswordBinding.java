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
public final class ActivitySignupPasswordBinding implements ViewBinding {
    public final TextView btnGoToLogin;
    public final Button btnSignup;
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    public final EditText inputPasswordFirst;
    public final EditText inputPasswordSecond;
    public final ImageView logo;
    public final Guideline logoGuidelineLeft;
    public final Guideline logoGuidelineRight;
    private final ConstraintLayout rootView;
    public final TextView textViewAlready;
    public final TextView textViewCreateAccount;

    private ActivitySignupPasswordBinding(ConstraintLayout constraintLayout, TextView textView, Button button, Guideline guideline, Guideline guideline2, EditText editText, EditText editText2, ImageView imageView, Guideline guideline3, Guideline guideline4, TextView textView2, TextView textView3) {
        this.rootView = constraintLayout;
        this.btnGoToLogin = textView;
        this.btnSignup = button;
        this.guidelineLeft = guideline;
        this.guidelineRight = guideline2;
        this.inputPasswordFirst = editText;
        this.inputPasswordSecond = editText2;
        this.logo = imageView;
        this.logoGuidelineLeft = guideline3;
        this.logoGuidelineRight = guideline4;
        this.textViewAlready = textView2;
        this.textViewCreateAccount = textView3;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivitySignupPasswordBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivitySignupPasswordBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.activity_signup_password, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ActivitySignupPasswordBinding bind(View view) {
        int i = R.id.btnGoToLogin;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.btnGoToLogin);
        if (textView != null) {
            i = R.id.btnSignup;
            Button button = (Button) ViewBindings.findChildViewById(view, (int) R.id.btnSignup);
            if (button != null) {
                i = R.id.guideline_left;
                Guideline findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.guideline_left);
                if (findChildViewById != null) {
                    i = R.id.guideline_right;
                    Guideline findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.guideline_right);
                    if (findChildViewById2 != null) {
                        i = R.id.inputPasswordFirst;
                        EditText editText = (EditText) ViewBindings.findChildViewById(view, (int) R.id.inputPasswordFirst);
                        if (editText != null) {
                            i = R.id.inputPasswordSecond;
                            EditText editText2 = (EditText) ViewBindings.findChildViewById(view, (int) R.id.inputPasswordSecond);
                            if (editText2 != null) {
                                i = R.id.logo;
                                ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.logo);
                                if (imageView != null) {
                                    i = R.id.logo_guideline_left;
                                    Guideline findChildViewById3 = ViewBindings.findChildViewById(view, (int) R.id.logo_guideline_left);
                                    if (findChildViewById3 != null) {
                                        i = R.id.logo_guideline_right;
                                        Guideline findChildViewById4 = ViewBindings.findChildViewById(view, (int) R.id.logo_guideline_right);
                                        if (findChildViewById4 != null) {
                                            i = R.id.textViewAlready;
                                            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.textViewAlready);
                                            if (textView2 != null) {
                                                i = R.id.textViewCreateAccount;
                                                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.textViewCreateAccount);
                                                if (textView3 != null) {
                                                    return new ActivitySignupPasswordBinding((ConstraintLayout) view, textView, button, findChildViewById, findChildViewById2, editText, editText2, imageView, findChildViewById3, findChildViewById4, textView2, textView3);
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
