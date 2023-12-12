package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class FragmentContactListBinding implements ViewBinding {
    public final View contactListDivider;
    public final EditText contactListEdittextSearch;
    public final RecyclerView contactListRecyclerview;
    public final TextView contactSelectGroupText;
    public final TextView contactTitleText;
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    private final ConstraintLayout rootView;

    private FragmentContactListBinding(ConstraintLayout constraintLayout, View view, EditText editText, RecyclerView recyclerView, TextView textView, TextView textView2, Guideline guideline, Guideline guideline2) {
        this.rootView = constraintLayout;
        this.contactListDivider = view;
        this.contactListEdittextSearch = editText;
        this.contactListRecyclerview = recyclerView;
        this.contactSelectGroupText = textView;
        this.contactTitleText = textView2;
        this.guidelineLeft = guideline;
        this.guidelineRight = guideline2;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static FragmentContactListBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static FragmentContactListBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_contact_list, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FragmentContactListBinding bind(View view) {
        int i = R.id.contact_list_divider;
        View findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.contact_list_divider);
        if (findChildViewById != null) {
            i = R.id.contact_list_edittext_search;
            EditText editText = (EditText) ViewBindings.findChildViewById(view, (int) R.id.contact_list_edittext_search);
            if (editText != null) {
                i = R.id.contact_list_recyclerview;
                RecyclerView findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.contact_list_recyclerview);
                if (findChildViewById2 != null) {
                    i = R.id.contact_select_group_text;
                    TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.contact_select_group_text);
                    if (textView != null) {
                        i = R.id.contact_title_text;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.contact_title_text);
                        if (textView2 != null) {
                            i = R.id.guideline_left;
                            Guideline findChildViewById3 = ViewBindings.findChildViewById(view, (int) R.id.guideline_left);
                            if (findChildViewById3 != null) {
                                i = R.id.guideline_right;
                                Guideline findChildViewById4 = ViewBindings.findChildViewById(view, (int) R.id.guideline_right);
                                if (findChildViewById4 != null) {
                                    return new FragmentContactListBinding((ConstraintLayout) view, findChildViewById, editText, findChildViewById2, textView, textView2, findChildViewById3, findChildViewById4);
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
