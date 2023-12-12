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

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class NotificationSettingsListItemBinding implements ViewBinding {
    public final TextView notificationSettingAlwaysText;
    public final ImageView notificationSettingItemImage;
    public final TextView notificationSettingNeverText;
    public final TextView notificationSettingOnlyText;
    public final TextView notificationSettingReceiveText;
    public final TextView notificationSettingTitleText;
    private final ConstraintLayout rootView;

    private NotificationSettingsListItemBinding(ConstraintLayout constraintLayout, TextView textView, ImageView imageView, TextView textView2, TextView textView3, TextView textView4, TextView textView5) {
        this.rootView = constraintLayout;
        this.notificationSettingAlwaysText = textView;
        this.notificationSettingItemImage = imageView;
        this.notificationSettingNeverText = textView2;
        this.notificationSettingOnlyText = textView3;
        this.notificationSettingReceiveText = textView4;
        this.notificationSettingTitleText = textView5;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static NotificationSettingsListItemBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static NotificationSettingsListItemBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.notification_settings_list_item, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static NotificationSettingsListItemBinding bind(View view) {
        int i = R.id.notification_setting_always_text;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_setting_always_text);
        if (textView != null) {
            i = R.id.notification_setting_item_image;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.notification_setting_item_image);
            if (imageView != null) {
                i = R.id.notification_setting_never_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_setting_never_text);
                if (textView2 != null) {
                    i = R.id.notification_setting_only_text;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_setting_only_text);
                    if (textView3 != null) {
                        i = R.id.notification_setting_receive_text;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_setting_receive_text);
                        if (textView4 != null) {
                            i = R.id.notification_setting_title_text;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_setting_title_text);
                            if (textView5 != null) {
                                return new NotificationSettingsListItemBinding((ConstraintLayout) view, textView, imageView, textView2, textView3, textView4, textView5);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
