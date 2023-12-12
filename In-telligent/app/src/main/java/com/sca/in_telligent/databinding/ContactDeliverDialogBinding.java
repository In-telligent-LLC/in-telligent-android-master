package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ContactDeliverDialogBinding implements ViewBinding {
    public final TextView contactDeliverOkButton;
    public final ProgressBar contactDeliverProgressbar;
    public final RecyclerView contactDeliverRecyclerview;
    public final EditText contactDeliverSearch;
    public final TextView contactDeliveryNoMemberText;
    public final Guideline guideline2;
    private final ConstraintLayout rootView;

    private ContactDeliverDialogBinding(ConstraintLayout constraintLayout, TextView textView, ProgressBar progressBar, RecyclerView recyclerView, EditText editText, TextView textView2, Guideline guideline) {
        this.rootView = constraintLayout;
        this.contactDeliverOkButton = textView;
        this.contactDeliverProgressbar = progressBar;
        this.contactDeliverRecyclerview = recyclerView;
        this.contactDeliverSearch = editText;
        this.contactDeliveryNoMemberText = textView2;
        this.guideline2 = guideline;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ContactDeliverDialogBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ContactDeliverDialogBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.contact_deliver_dialog, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ContactDeliverDialogBinding bind(View view) {
        int i = R.id.contact_deliver_ok_button;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.contact_deliver_ok_button);
        if (textView != null) {
            i = R.id.contact_deliver_progressbar;
            ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(view, (int) R.id.contact_deliver_progressbar);
            if (progressBar != null) {
                i = R.id.contact_deliver_recyclerview;
                RecyclerView findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.contact_deliver_recyclerview);
                if (findChildViewById != null) {
                    i = R.id.contact_deliver_search;
                    EditText editText = (EditText) ViewBindings.findChildViewById(view, (int) R.id.contact_deliver_search);
                    if (editText != null) {
                        i = R.id.contact_delivery_no_member_text;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.contact_delivery_no_member_text);
                        if (textView2 != null) {
                            i = R.id.guideline2;
                            Guideline findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.guideline2);
                            if (findChildViewById2 != null) {
                                return new ContactDeliverDialogBinding((ConstraintLayout) view, textView, progressBar, findChildViewById, editText, textView2, findChildViewById2);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
