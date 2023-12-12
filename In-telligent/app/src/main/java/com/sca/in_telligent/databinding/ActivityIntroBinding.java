package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityIntroBinding implements ViewBinding {
    public final TextView firstTimeUserBtn;
    public final ImageView imageAnimated;
    public final ImageView introLogoItem;
    public final TextView returningSubscriberBtn;
    private final ConstraintLayout rootView;

    private ActivityIntroBinding(ConstraintLayout constraintLayout, TextView textView, ImageView imageView, ImageView imageView2, TextView textView2) {
        this.rootView = constraintLayout;
        this.firstTimeUserBtn = textView;
        this.imageAnimated = imageView;
        this.introLogoItem = imageView2;
        this.returningSubscriberBtn = textView2;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityIntroBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityIntroBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.activity_intro, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ActivityIntroBinding bind(View view) {
        int i = R.id.first_time_user_btn;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.first_time_user_btn);
        if (textView != null) {
            i = R.id.imageAnimated;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.imageAnimated);
            if (imageView != null) {
                i = R.id.intro_logo_item;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.intro_logo_item);
                if (imageView2 != null) {
                    i = R.id.returning_subscriber_btn;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.returning_subscriber_btn);
                    if (textView2 != null) {
                        return new ActivityIntroBinding((ConstraintLayout) view, textView, imageView, imageView2, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
