package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager.widget.ViewPager;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class FragmentGroupDetailContainerBinding implements ViewBinding {
    private final FrameLayout rootView;
    public final ViewPager viewpagerContainer;

    private FragmentGroupDetailContainerBinding(FrameLayout frameLayout, ViewPager viewPager) {
        this.rootView = frameLayout;
        this.viewpagerContainer = viewPager;
    }

    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static FragmentGroupDetailContainerBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static FragmentGroupDetailContainerBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_group_detail_container, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FragmentGroupDetailContainerBinding bind(View view) {
        ViewPager findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.viewpager_container);
        if (findChildViewById != null) {
            return new FragmentGroupDetailContainerBinding((FrameLayout) view, findChildViewById);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(R.id.viewpager_container)));
    }
}
