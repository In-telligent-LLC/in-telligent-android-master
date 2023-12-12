package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityPersonalSafetyPopupBinding implements ViewBinding {
    public final TextView personalSafetyBody;
    public final ImageView personalSafetyLogo;
    public final Button personalSafetyNoButton;
    public final TextView personalSafetyTitle;
    public final Button personalSafetyYesButton;
    private final RelativeLayout rootView;

    private ActivityPersonalSafetyPopupBinding(RelativeLayout relativeLayout, TextView textView, ImageView imageView, Button button, TextView textView2, Button button2) {
        this.rootView = relativeLayout;
        this.personalSafetyBody = textView;
        this.personalSafetyLogo = imageView;
        this.personalSafetyNoButton = button;
        this.personalSafetyTitle = textView2;
        this.personalSafetyYesButton = button2;
    }

    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static ActivityPersonalSafetyPopupBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityPersonalSafetyPopupBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.activity_personal_safety_popup, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ActivityPersonalSafetyPopupBinding bind(View view) {
        int i = R.id.personal_safety_body;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.personal_safety_body);
        if (textView != null) {
            i = R.id.personal_safety_logo;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.personal_safety_logo);
            if (imageView != null) {
                i = R.id.personal_safety_no_button;
                Button button = (Button) ViewBindings.findChildViewById(view, (int) R.id.personal_safety_no_button);
                if (button != null) {
                    i = R.id.personal_safety_title;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.personal_safety_title);
                    if (textView2 != null) {
                        i = R.id.personal_safety_yes_button;
                        Button button2 = (Button) ViewBindings.findChildViewById(view, (int) R.id.personal_safety_yes_button);
                        if (button2 != null) {
                            return new ActivityPersonalSafetyPopupBinding((RelativeLayout) view, textView, imageView, button, textView2, button2);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
