package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityVoiceCallNotificationBinding implements ViewBinding {
    public final FloatingActionButton acceptButton;
    public final TextView acceptText;
    public final FloatingActionButton cancelButton;
    public final TextView notificationDetails;
    private final ConstraintLayout rootView;

    private ActivityVoiceCallNotificationBinding(ConstraintLayout constraintLayout, FloatingActionButton floatingActionButton, TextView textView, FloatingActionButton floatingActionButton2, TextView textView2) {
        this.rootView = constraintLayout;
        this.acceptButton = floatingActionButton;
        this.acceptText = textView;
        this.cancelButton = floatingActionButton2;
        this.notificationDetails = textView2;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityVoiceCallNotificationBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityVoiceCallNotificationBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.activity_voice_call_notification, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ActivityVoiceCallNotificationBinding bind(View view) {
        int i = R.id.accept_button;
        FloatingActionButton floatingActionButton = (FloatingActionButton) ViewBindings.findChildViewById(view, (int) R.id.accept_button);
        if (floatingActionButton != null) {
            i = R.id.accept_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.accept_text);
            if (textView != null) {
                i = 2131230865;
                FloatingActionButton floatingActionButton2 = (FloatingActionButton) ViewBindings.findChildViewById(view, 2131230865);
                if (floatingActionButton2 != null) {
                    i = R.id.notification_details;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_details);
                    if (textView2 != null) {
                        return new ActivityVoiceCallNotificationBinding((ConstraintLayout) view, floatingActionButton, textView, floatingActionButton2, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
