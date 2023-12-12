package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class GroupSuggestedListItemBinding implements ViewBinding {
    public final RelativeLayout groupIgnoreTextLayout;
    public final TextView groupName;
    public final RelativeLayout groupSubscribeTextLayout;
    private final CardView rootView;
    public final ImageView suggestedImage;

    private GroupSuggestedListItemBinding(CardView cardView, RelativeLayout relativeLayout, TextView textView, RelativeLayout relativeLayout2, ImageView imageView) {
        this.rootView = cardView;
        this.groupIgnoreTextLayout = relativeLayout;
        this.groupName = textView;
        this.groupSubscribeTextLayout = relativeLayout2;
        this.suggestedImage = imageView;
    }

    public CardView getRoot() {
        return this.rootView;
    }

    public static GroupSuggestedListItemBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static GroupSuggestedListItemBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.group_suggested_list_item, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static GroupSuggestedListItemBinding bind(View view) {
        int i = R.id.group_ignore_text_layout;
        RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, (int) R.id.group_ignore_text_layout);
        if (relativeLayout != null) {
            i = R.id.group_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.group_name);
            if (textView != null) {
                i = R.id.group_subscribe_text_layout;
                RelativeLayout relativeLayout2 = (RelativeLayout) ViewBindings.findChildViewById(view, (int) R.id.group_subscribe_text_layout);
                if (relativeLayout2 != null) {
                    i = R.id.suggested_image;
                    ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.suggested_image);
                    if (imageView != null) {
                        return new GroupSuggestedListItemBinding((CardView) view, relativeLayout, textView, relativeLayout2, imageView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
