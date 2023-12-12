package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

public final class ActivityIncomingCallPopupBinding implements ViewBinding {
    public final Chronometer chronometer;
    public final ConstraintLayout incomingCallAcceptCall;
    public final ImageView incomingCallAcceptImage;
    public final TextView incomingCallCenterText;
    public final ConstraintLayout incomingCallRejectCall;
    public final ImageView incomingCallRejectImage;
    private final ConstraintLayout rootView;

    private ActivityIncomingCallPopupBinding(ConstraintLayout constraintLayout, Chronometer chronometer, ConstraintLayout constraintLayout2, ImageView imageView, TextView textView, ConstraintLayout constraintLayout3, ImageView imageView2) {
        this.rootView = constraintLayout;
        this.chronometer = chronometer;
        this.incomingCallAcceptCall = constraintLayout2;
        this.incomingCallAcceptImage = imageView;
        this.incomingCallCenterText = textView;
        this.incomingCallRejectCall = constraintLayout3;
        this.incomingCallRejectImage = imageView2;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityIncomingCallPopupBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityIncomingCallPopupBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.activity_incoming_call_popup, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ActivityIncomingCallPopupBinding bind(View view) {
        int i = 2131230881;
        Chronometer chronometer = (Chronometer) ViewBindings.findChildViewById(view, 2131230881);
        if (chronometer != null) {
            i = R.id.incoming_call_accept_call;
            ConstraintLayout findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.incoming_call_accept_call);
            if (findChildViewById != null) {
                i = R.id.incoming_call_accept_image;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.incoming_call_accept_image);
                if (imageView != null) {
                    i = R.id.incoming_call_center_text;
                    TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.incoming_call_center_text);
                    if (textView != null) {
                        i = R.id.incoming_call_reject_call;
                        ConstraintLayout findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.incoming_call_reject_call);
                        if (findChildViewById2 != null) {
                            i = R.id.incoming_call_reject_image;
                            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.incoming_call_reject_image);
                            if (imageView2 != null) {
                                return new ActivityIncomingCallPopupBinding((ConstraintLayout) view, chronometer, findChildViewById, imageView, textView, findChildViewById2, imageView2);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
