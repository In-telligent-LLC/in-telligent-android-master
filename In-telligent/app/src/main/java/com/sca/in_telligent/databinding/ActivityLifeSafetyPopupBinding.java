package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityLifeSafetyPopupBinding implements ViewBinding {
    public final TextView lifeSafetyAlertBody;
    public final Button lifeSafetyAlertButtonOk;
    public final TextView lifeSafetyAlertTitle;
    public final ConstraintLayout lifeSafetyMain;
    private final ConstraintLayout rootView;

    private ActivityLifeSafetyPopupBinding(ConstraintLayout constraintLayout, TextView textView, Button button, TextView textView2, ConstraintLayout constraintLayout2) {
        this.rootView = constraintLayout;
        this.lifeSafetyAlertBody = textView;
        this.lifeSafetyAlertButtonOk = button;
        this.lifeSafetyAlertTitle = textView2;
        this.lifeSafetyMain = constraintLayout2;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityLifeSafetyPopupBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityLifeSafetyPopupBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.activity_life_safety_popup, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ActivityLifeSafetyPopupBinding bind(View view) {
        int i = R.id.life_safety_alert_body;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.life_safety_alert_body);
        if (textView != null) {
            i = R.id.life_safety_alert_button_ok;
            Button button = (Button) ViewBindings.findChildViewById(view, (int) R.id.life_safety_alert_button_ok);
            if (button != null) {
                i = R.id.life_safety_alert_title;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.life_safety_alert_title);
                if (textView2 != null) {
                    ConstraintLayout constraintLayout = (ConstraintLayout) view;
                    return new ActivityLifeSafetyPopupBinding(constraintLayout, textView, button, textView2, constraintLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
