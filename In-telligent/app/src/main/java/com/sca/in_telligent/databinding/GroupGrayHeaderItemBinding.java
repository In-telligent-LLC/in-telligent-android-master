package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class GroupGrayHeaderItemBinding implements ViewBinding {
    public final TextView grayHeaderText;
    private final ConstraintLayout rootView;

    private GroupGrayHeaderItemBinding(ConstraintLayout constraintLayout, TextView textView) {
        this.rootView = constraintLayout;
        this.grayHeaderText = textView;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static GroupGrayHeaderItemBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static GroupGrayHeaderItemBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.group_gray_header_item, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static GroupGrayHeaderItemBinding bind(View view) {
        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.gray_header_text);
        if (textView != null) {
            return new GroupGrayHeaderItemBinding((ConstraintLayout) view, textView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(R.id.gray_header_text)));
    }
}
