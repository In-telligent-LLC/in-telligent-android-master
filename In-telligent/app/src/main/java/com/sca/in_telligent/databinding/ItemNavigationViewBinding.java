package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ItemNavigationViewBinding implements ViewBinding {
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    public final View itemNavigationDivider;
    public final ImageView itemNavigationImage;
    public final TextView itemNavigationText;
    private final ConstraintLayout rootView;

    private ItemNavigationViewBinding(ConstraintLayout constraintLayout, Guideline guideline, Guideline guideline2, View view, ImageView imageView, TextView textView) {
        this.rootView = constraintLayout;
        this.guidelineLeft = guideline;
        this.guidelineRight = guideline2;
        this.itemNavigationDivider = view;
        this.itemNavigationImage = imageView;
        this.itemNavigationText = textView;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ItemNavigationViewBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ItemNavigationViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.item_navigation_view, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ItemNavigationViewBinding bind(View view) {
        int i = R.id.guideline_left;
        Guideline findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.guideline_left);
        if (findChildViewById != null) {
            i = R.id.guideline_right;
            Guideline findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.guideline_right);
            if (findChildViewById2 != null) {
                i = R.id.item_navigation_divider;
                View findChildViewById3 = ViewBindings.findChildViewById(view, (int) R.id.item_navigation_divider);
                if (findChildViewById3 != null) {
                    i = R.id.item_navigation_image;
                    ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.item_navigation_image);
                    if (imageView != null) {
                        i = R.id.item_navigation_text;
                        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.item_navigation_text);
                        if (textView != null) {
                            return new ItemNavigationViewBinding((ConstraintLayout) view, findChildViewById, findChildViewById2, findChildViewById3, imageView, textView);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
