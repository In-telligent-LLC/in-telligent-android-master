package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ContactListGroupViewBinding implements ViewBinding {
    public final Barrier barrierRight;
    public final ImageView contactGroupItemCall;
    public final ImageView contactGroupItemMessage;
    public final TextView contactGroupItemName;
    public final ImageView contactGroupItemProfileImage;
    private final ConstraintLayout rootView;

    private ContactListGroupViewBinding(ConstraintLayout constraintLayout, Barrier barrier, ImageView imageView, ImageView imageView2, TextView textView, ImageView imageView3) {
        this.rootView = constraintLayout;
        this.barrierRight = barrier;
        this.contactGroupItemCall = imageView;
        this.contactGroupItemMessage = imageView2;
        this.contactGroupItemName = textView;
        this.contactGroupItemProfileImage = imageView3;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ContactListGroupViewBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ContactListGroupViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.contact_list_group_view, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ContactListGroupViewBinding bind(View view) {
        int i = R.id.barrier_right;
        Barrier findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.barrier_right);
        if (findChildViewById != null) {
            i = R.id.contact_group_item_call;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.contact_group_item_call);
            if (imageView != null) {
                i = R.id.contact_group_item_message;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.contact_group_item_message);
                if (imageView2 != null) {
                    i = R.id.contact_group_item_name;
                    TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.contact_group_item_name);
                    if (textView != null) {
                        i = R.id.contact_group_item_profile_image;
                        ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.contact_group_item_profile_image);
                        if (imageView3 != null) {
                            return new ContactListGroupViewBinding((ConstraintLayout) view, findChildViewById, imageView, imageView2, textView, imageView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
