package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.gms.maps.MapView;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class FindLocationDialogBinding implements ViewBinding {
    public final ImageView findLocationBackButton;
    public final TextView findLocationBackText;
    public final TextView findLocationTitleText;
    public final MapView mapView;
    private final ConstraintLayout rootView;

    private FindLocationDialogBinding(ConstraintLayout constraintLayout, ImageView imageView, TextView textView, TextView textView2, MapView mapView) {
        this.rootView = constraintLayout;
        this.findLocationBackButton = imageView;
        this.findLocationBackText = textView;
        this.findLocationTitleText = textView2;
        this.mapView = mapView;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static FindLocationDialogBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static FindLocationDialogBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.find_location_dialog, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FindLocationDialogBinding bind(View view) {
        int i = R.id.find_location_back_button;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.find_location_back_button);
        if (imageView != null) {
            i = R.id.find_location_back_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.find_location_back_text);
            if (textView != null) {
                i = R.id.find_location_title_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.find_location_title_text);
                if (textView2 != null) {
                    i = R.id.mapView;
                    MapView mapView = (MapView) ViewBindings.findChildViewById(view, (int) R.id.mapView);
                    if (mapView != null) {
                        return new FindLocationDialogBinding((ConstraintLayout) view, imageView, textView, textView2, mapView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
