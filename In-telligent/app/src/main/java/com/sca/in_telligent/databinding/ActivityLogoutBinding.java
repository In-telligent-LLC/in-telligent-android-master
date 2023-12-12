package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityLogoutBinding implements ViewBinding {
    private final ConstraintLayout rootView;
    public final Button testLogoutButton;

    private ActivityLogoutBinding(ConstraintLayout constraintLayout, Button button) {
        this.rootView = constraintLayout;
        this.testLogoutButton = button;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityLogoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityLogoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.activity_logout, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ActivityLogoutBinding bind(View view) {
        Button button = (Button) ViewBindings.findChildViewById(view, (int) R.id.testLogoutButton);
        if (button != null) {
            return new ActivityLogoutBinding((ConstraintLayout) view, button);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(R.id.testLogoutButton)));
    }
}
