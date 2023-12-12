package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class FragmentAlertListBinding implements ViewBinding {
    public final RecyclerView alertListRecyclerview;
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    private final ConstraintLayout rootView;
    public final SwipeRefreshLayout swipeRefreshLayoutAlerts;

    private FragmentAlertListBinding(ConstraintLayout constraintLayout, RecyclerView recyclerView, Guideline guideline, Guideline guideline2, SwipeRefreshLayout swipeRefreshLayout) {
        this.rootView = constraintLayout;
        this.alertListRecyclerview = recyclerView;
        this.guidelineLeft = guideline;
        this.guidelineRight = guideline2;
        this.swipeRefreshLayoutAlerts = swipeRefreshLayout;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static FragmentAlertListBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static FragmentAlertListBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_alert_list, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FragmentAlertListBinding bind(View view) {
        int i = R.id.alert_list_recyclerview;
        RecyclerView findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.alert_list_recyclerview);
        if (findChildViewById != null) {
            i = R.id.guideline_left;
            Guideline findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.guideline_left);
            if (findChildViewById2 != null) {
                i = R.id.guideline_right;
                Guideline findChildViewById3 = ViewBindings.findChildViewById(view, (int) R.id.guideline_right);
                if (findChildViewById3 != null) {
                    i = R.id.swipe_refresh_layout_alerts;
                    SwipeRefreshLayout findChildViewById4 = ViewBindings.findChildViewById(view, (int) R.id.swipe_refresh_layout_alerts);
                    if (findChildViewById4 != null) {
                        return new FragmentAlertListBinding((ConstraintLayout) view, findChildViewById, findChildViewById2, findChildViewById3, findChildViewById4);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
