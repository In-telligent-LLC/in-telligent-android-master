package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class GroupNormalListItemBinding implements ViewBinding {
    public final TextView groupItemAboutText;
    public final TextView groupItemConnectText;
    public final TextView groupName;
    public final ImageView groupNormalItemImage;
    private final CardView rootView;

    private GroupNormalListItemBinding(CardView cardView, TextView textView, TextView textView2, TextView textView3, ImageView imageView) {
        this.rootView = cardView;
        this.groupItemAboutText = textView;
        this.groupItemConnectText = textView2;
        this.groupName = textView3;
        this.groupNormalItemImage = imageView;
    }

    public CardView getRoot() {
        return this.rootView;
    }

    public static GroupNormalListItemBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static GroupNormalListItemBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.group_normal_list_item, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static GroupNormalListItemBinding bind(View view) {
        int i = R.id.group_item_about_text;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.group_item_about_text);
        if (textView != null) {
            i = R.id.group_item_connect_text;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.group_item_connect_text);
            if (textView2 != null) {
                i = R.id.group_name;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.group_name);
                if (textView3 != null) {
                    i = R.id.group_normal_item_image;
                    ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.group_normal_item_image);
                    if (imageView != null) {
                        return new GroupNormalListItemBinding((CardView) view, textView, textView2, textView3, imageView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
