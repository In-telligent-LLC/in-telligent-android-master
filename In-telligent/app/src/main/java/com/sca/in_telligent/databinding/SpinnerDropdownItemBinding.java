package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class SpinnerDropdownItemBinding implements ViewBinding {
    private final RelativeLayout rootView;
    public final TextView spinnerItemText;

    private SpinnerDropdownItemBinding(RelativeLayout relativeLayout, TextView textView) {
        this.rootView = relativeLayout;
        this.spinnerItemText = textView;
    }

    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static SpinnerDropdownItemBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static SpinnerDropdownItemBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.spinner_dropdown_item, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static SpinnerDropdownItemBinding bind(View view) {
        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.spinner_item_text);
        if (textView != null) {
            return new SpinnerDropdownItemBinding((RelativeLayout) view, textView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(R.id.spinner_item_text)));
    }
}
