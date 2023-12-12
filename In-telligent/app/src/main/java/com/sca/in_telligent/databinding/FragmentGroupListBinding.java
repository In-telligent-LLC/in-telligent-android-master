package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class FragmentGroupListBinding implements ViewBinding {
    public final EditText groupListEdittextSearch;
    public final Spinner groupListSpinner;
    public final RelativeLayout groupListSpinnerLayout;
    public final RecyclerView groupRecyclerview;
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    private final ConstraintLayout rootView;
    public final SwipeRefreshLayout swipeRefreshLayoutGroups;

    private FragmentGroupListBinding(ConstraintLayout constraintLayout, EditText editText, Spinner spinner, RelativeLayout relativeLayout, RecyclerView recyclerView, Guideline guideline, Guideline guideline2, SwipeRefreshLayout swipeRefreshLayout) {
        this.rootView = constraintLayout;
        this.groupListEdittextSearch = editText;
        this.groupListSpinner = spinner;
        this.groupListSpinnerLayout = relativeLayout;
        this.groupRecyclerview = recyclerView;
        this.guidelineLeft = guideline;
        this.guidelineRight = guideline2;
        this.swipeRefreshLayoutGroups = swipeRefreshLayout;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static FragmentGroupListBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static FragmentGroupListBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_group_list, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FragmentGroupListBinding bind(View view) {
        int i = R.id.group_list_edittext_search;
        EditText editText = (EditText) ViewBindings.findChildViewById(view, (int) R.id.group_list_edittext_search);
        if (editText != null) {
            i = R.id.group_list_spinner;
            Spinner spinner = (Spinner) ViewBindings.findChildViewById(view, (int) R.id.group_list_spinner);
            if (spinner != null) {
                i = R.id.group_list_spinner_layout;
                RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, (int) R.id.group_list_spinner_layout);
                if (relativeLayout != null) {
                    i = R.id.group_recyclerview;
                    RecyclerView findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.group_recyclerview);
                    if (findChildViewById != null) {
                        i = R.id.guideline_left;
                        Guideline findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.guideline_left);
                        if (findChildViewById2 != null) {
                            i = R.id.guideline_right;
                            Guideline findChildViewById3 = ViewBindings.findChildViewById(view, (int) R.id.guideline_right);
                            if (findChildViewById3 != null) {
                                i = R.id.swipe_refresh_layout_groups;
                                SwipeRefreshLayout findChildViewById4 = ViewBindings.findChildViewById(view, (int) R.id.swipe_refresh_layout_groups);
                                if (findChildViewById4 != null) {
                                    return new FragmentGroupListBinding((ConstraintLayout) view, editText, spinner, relativeLayout, findChildViewById, findChildViewById2, findChildViewById3, findChildViewById4);
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
