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
public final class GroupListEmptyViewBinding implements ViewBinding {
    public final TextView groupListNoMessageText;
    private final ConstraintLayout rootView;

    private GroupListEmptyViewBinding(ConstraintLayout constraintLayout, TextView textView) {
        this.rootView = constraintLayout;
        this.groupListNoMessageText = textView;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static GroupListEmptyViewBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static GroupListEmptyViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.group_list_empty_view, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static GroupListEmptyViewBinding bind(View view) {
        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.group_list_no_message_text);
        if (textView != null) {
            return new GroupListEmptyViewBinding((ConstraintLayout) view, textView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(R.id.group_list_no_message_text)));
    }
}
