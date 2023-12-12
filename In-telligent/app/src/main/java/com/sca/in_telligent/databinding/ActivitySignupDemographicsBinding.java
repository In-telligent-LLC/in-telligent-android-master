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
public final class ActivitySignupDemographicsBinding implements ViewBinding {
    public final TextView btnGoToLogin;
    public final Button btnGoToSignupPassword;
    public final ImageView btnLoginFacebookCustom;
    public final ImageView btnLoginGooglePlus;
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    public final EditText inputEmail;
    public final EditText inputName;
    public final ImageView logo;
    public final Guideline logoGuidelineLeft;
    public final Guideline logoGuidelineRight;
    private final ConstraintLayout rootView;
    public final TextView textViewAlready;
    public final TextView textViewCreateAccount;

    private ActivitySignupDemographicsBinding(ConstraintLayout constraintLayout, TextView textView, Button button, ImageView imageView, ImageView imageView2, Guideline guideline, Guideline guideline2, EditText editText, EditText editText2, ImageView imageView3, Guideline guideline3, Guideline guideline4, TextView textView2, TextView textView3) {
        this.rootView = constraintLayout;
        this.btnGoToLogin = textView;
        this.btnGoToSignupPassword = button;
        this.btnLoginFacebookCustom = imageView;
        this.btnLoginGooglePlus = imageView2;
        this.guidelineLeft = guideline;
        this.guidelineRight = guideline2;
        this.inputEmail = editText;
        this.inputName = editText2;
        this.logo = imageView3;
        this.logoGuidelineLeft = guideline3;
        this.logoGuidelineRight = guideline4;
        this.textViewAlready = textView2;
        this.textViewCreateAccount = textView3;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivitySignupDemographicsBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivitySignupDemographicsBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.activity_signup_demographics, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ActivitySignupDemographicsBinding bind(View view) {
        int i = R.id.btnGoToLogin;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.btnGoToLogin);
        if (textView != null) {
            i = R.id.btnGoToSignupPassword;
            Button button = (Button) ViewBindings.findChildViewById(view, (int) R.id.btnGoToSignupPassword);
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
                                i = R.id.inputEmail;
                                EditText editText = (EditText) ViewBindings.findChildViewById(view, (int) R.id.inputEmail);
                                if (editText != null) {
                                    i = R.id.inputName;
                                    EditText editText2 = (EditText) ViewBindings.findChildViewById(view, (int) R.id.inputName);
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
                                                    i = R.id.textViewAlready;
                                                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.textViewAlready);
                                                    if (textView2 != null) {
                                                        i = R.id.textViewCreateAccount;
                                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.textViewCreateAccount);
                                                        if (textView3 != null) {
                                                            return new ActivitySignupDemographicsBinding((ConstraintLayout) view, textView, button, imageView, imageView2, findChildViewById, findChildViewById2, editText, editText2, imageView3, findChildViewById3, findChildViewById4, textView2, textView3);
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
