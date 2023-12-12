package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

public final class FragmentInboxBinding implements ViewBinding {
    public final RecyclerView inboxRecyclerview;
    public final Spinner inboxSpinner;
    public final RelativeLayout inboxSpinnerLayout;
    private final ConstraintLayout rootView;
    public final SwipeRefreshLayout swipeRefreshLayoutInbox;

    private FragmentInboxBinding(ConstraintLayout constraintLayout, RecyclerView recyclerView, Spinner spinner, RelativeLayout relativeLayout, SwipeRefreshLayout swipeRefreshLayout) {
        this.rootView = constraintLayout;
        this.inboxRecyclerview = recyclerView;
        this.inboxSpinner = spinner;
        this.inboxSpinnerLayout = relativeLayout;
        this.swipeRefreshLayoutInbox = swipeRefreshLayout;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static FragmentInboxBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static FragmentInboxBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_inbox, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FragmentInboxBinding bind(View view) {
        int i = R.id.inbox_recyclerview;
        RecyclerView findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.inbox_recyclerview);
        if (findChildViewById != null) {
            i = R.id.inbox_spinner;
            Spinner spinner = (Spinner) ViewBindings.findChildViewById(view, (int) R.id.inbox_spinner);
            if (spinner != null) {
                i = R.id.inbox_spinner_layout;
                RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, (int) R.id.inbox_spinner_layout);
                if (relativeLayout != null) {
                    i = R.id.swipe_refresh_layout_inbox;
                    SwipeRefreshLayout findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.swipe_refresh_layout_inbox);
                    if (findChildViewById2 != null) {
                        return new FragmentInboxBinding((ConstraintLayout) view, findChildViewById, spinner, relativeLayout, findChildViewById2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
