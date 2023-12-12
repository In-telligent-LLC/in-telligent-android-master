package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ContactDeliverInviteeItemBinding implements ViewBinding {
    public final CheckBox contactDeliverItemCheckbox;
    public final TextView contactDeliverItemNameText;
    private final ConstraintLayout rootView;

    private ContactDeliverInviteeItemBinding(ConstraintLayout constraintLayout, CheckBox checkBox, TextView textView) {
        this.rootView = constraintLayout;
        this.contactDeliverItemCheckbox = checkBox;
        this.contactDeliverItemNameText = textView;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ContactDeliverInviteeItemBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ContactDeliverInviteeItemBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.contact_deliver_invitee_item, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ContactDeliverInviteeItemBinding bind(View view) {
        int i = R.id.contact_deliver_item_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(view, (int) R.id.contact_deliver_item_checkbox);
        if (checkBox != null) {
            i = R.id.contact_deliver_item_name_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.contact_deliver_item_name_text);
            if (textView != null) {
                return new ContactDeliverInviteeItemBinding((ConstraintLayout) view, checkBox, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
