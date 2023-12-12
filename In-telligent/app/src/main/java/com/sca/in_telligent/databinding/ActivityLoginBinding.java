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
public final class ActivityLoginBinding implements ViewBinding {
    public final TextView btnForgotPassword;
    public final TextView btnGoToSignup;
    public final Button btnLogin;
    public final ImageView btnLoginFacebookCustom;
    public final ImageView btnLoginGooglePlus;
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    public final EditText inputPassword;
    public final EditText inputUsername;
    public final ImageView logo;
    public final Guideline logoGuidelineLeft;
    public final Guideline logoGuidelineRight;
    public final TextView orLabel;
    private final ConstraintLayout rootView;
    public final TextView textViewSignIn;

    private ActivityLoginBinding(ConstraintLayout constraintLayout, TextView textView, TextView textView2, Button button, ImageView imageView, ImageView imageView2, Guideline guideline, Guideline guideline2, EditText editText, EditText editText2, ImageView imageView3, Guideline guideline3, Guideline guideline4, TextView textView3, TextView textView4) {
        this.rootView = constraintLayout;
        this.btnForgotPassword = textView;
        this.btnGoToSignup = textView2;
        this.btnLogin = button;
        this.btnLoginFacebookCustom = imageView;
        this.btnLoginGooglePlus = imageView2;
        this.guidelineLeft = guideline;
        this.guidelineRight = guideline2;
        this.inputPassword = editText;
        this.inputUsername = editText2;
        this.logo = imageView3;
        this.logoGuidelineLeft = guideline3;
        this.logoGuidelineRight = guideline4;
        this.orLabel = textView3;
        this.textViewSignIn = textView4;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityLoginBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityLoginBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.activity_login, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ActivityLoginBinding bind(View view) {
        int i = R.id.btnForgotPassword;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.btnForgotPassword);
        if (textView != null) {
            i = R.id.btnGoToSignup;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.btnGoToSignup);
            if (textView2 != null) {
                i = R.id.btnLogin;
                Button button = (Button) ViewBindings.findChildViewById(view, (int) R.id.btnLogin);
                if (button != null) {
                    i = R.id.btnLoginFacebookCustom;
                    ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.btnLoginFacebookCustom);
                    if (imageView != null) {
                        i = R.id.btnLoginGooglePlus;
                        ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.btnLoginGooglePlus);
                        if (imageView2 != null) {
                            i = R.id.guideline_left;
                            Guideline findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.guideline_left);
                            if (findChildViewById != null) {
                                i = R.id.guideline_right;
                                Guideline findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.guideline_right);
                                if (findChildViewById2 != null) {
                                    i = R.id.inputPassword;
                                    EditText editText = (EditText) ViewBindings.findChildViewById(view, (int) R.id.inputPassword);
                                    if (editText != null) {
                                        i = R.id.inputUsername;
                                        EditText editText2 = (EditText) ViewBindings.findChildViewById(view, (int) R.id.inputUsername);
                                        if (editText2 != null) {
                                            i = R.id.logo;
                                            ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.logo);
                                            if (imageView3 != null) {
                                                i = R.id.logo_guideline_left;
                                                Guideline findChildViewById3 = ViewBindings.findChildViewById(view, (int) R.id.logo_guideline_left);
                                                if (findChildViewById3 != null) {
                                                    i = R.id.logo_guideline_right;
                                                    Guideline findChildViewById4 = ViewBindings.findChildViewById(view, (int) R.id.logo_guideline_right);
                                                    if (findChildViewById4 != null) {
                                                        i = R.id.orLabel;
                                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.orLabel);
                                                        if (textView3 != null) {
                                                            i = R.id.textViewSignIn;
                                                            TextView textView4 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.textViewSignIn);
                                                            if (textView4 != null) {
                                                                return new ActivityLoginBinding((ConstraintLayout) view, textView, textView2, button, imageView, imageView2, findChildViewById, findChildViewById2, editText, editText2, imageView3, findChildViewById3, findChildViewById4, textView3, textView4);
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
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
