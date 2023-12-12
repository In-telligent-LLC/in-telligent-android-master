package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

public final class GroupSuggestedHeaderItemBinding implements ViewBinding {
    public final TextView headerText;
    private final ConstraintLayout rootView;
    public final ConstraintLayout suggestedGroupsHeaderContainer;
    public final ImageView suggestedItemArrow;

    private GroupSuggestedHeaderItemBinding(ConstraintLayout constraintLayout, TextView textView, ConstraintLayout constraintLayout2, ImageView imageView) {
        this.rootView = constraintLayout;
        this.headerText = textView;
        this.suggestedGroupsHeaderContainer = constraintLayout2;
        this.suggestedItemArrow = imageView;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static GroupSuggestedHeaderItemBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static GroupSuggestedHeaderItemBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.group_suggested_header_item, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static GroupSuggestedHeaderItemBinding bind(View view) {
        int i = R.id.header_text;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.header_text);
        if (textView != null) {
            ConstraintLayout constraintLayout = (ConstraintLayout) view;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.suggested_item_arrow);
            if (imageView != null) {
                return new GroupSuggestedHeaderItemBinding(constraintLayout, textView, constraintLayout, imageView);
            }
            i = 2131231424;
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
