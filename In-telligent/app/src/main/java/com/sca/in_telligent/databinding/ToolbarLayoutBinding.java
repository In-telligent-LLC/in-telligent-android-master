package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.appbar.AppBarLayout;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ToolbarLayoutBinding implements ViewBinding {
    public final AppBarLayout appBarLayout;
    private final AppBarLayout rootView;
    public final ImageView sosButton;
    public final Toolbar toolbar;

    private ToolbarLayoutBinding(AppBarLayout appBarLayout, AppBarLayout appBarLayout2, ImageView imageView, Toolbar toolbar) {
        this.rootView = appBarLayout;
        this.appBarLayout = appBarLayout2;
        this.sosButton = imageView;
        this.toolbar = toolbar;
    }

    public AppBarLayout getRoot() {
        return this.rootView;
    }

    public static ToolbarLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ToolbarLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.toolbar_layout, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ToolbarLayoutBinding bind(View view) {
        AppBarLayout appBarLayout = (AppBarLayout) view;
        int i = R.id.sos_button;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.sos_button);
        if (imageView != null) {
            i = 2131231479;
            Toolbar findChildViewById = ViewBindings.findChildViewById(view, 2131231479);
            if (findChildViewById != null) {
                return new ToolbarLayoutBinding(appBarLayout, appBarLayout, imageView, findChildViewById);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
