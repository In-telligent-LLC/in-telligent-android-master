package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class FragmentNotificationSettingsBinding implements ViewBinding {
    public final TextView notificationSettingLightningLabel;
    public final Switch notificationSettingLightningToggle;
    public final RecyclerView notificationSettingRecyclerview;
    public final TextView notificationSettingSevereLabel;
    public final Switch notificationSettingSevereToggle;
    public final View notificationSettingsDivider;
    private final ConstraintLayout rootView;
    public final TextView severeWarningTitle;

    private FragmentNotificationSettingsBinding(ConstraintLayout constraintLayout, TextView textView, Switch r3, RecyclerView recyclerView, TextView textView2, Switch r6, View view, TextView textView3) {
        this.rootView = constraintLayout;
        this.notificationSettingLightningLabel = textView;
        this.notificationSettingLightningToggle = r3;
        this.notificationSettingRecyclerview = recyclerView;
        this.notificationSettingSevereLabel = textView2;
        this.notificationSettingSevereToggle = r6;
        this.notificationSettingsDivider = view;
        this.severeWarningTitle = textView3;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static FragmentNotificationSettingsBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static FragmentNotificationSettingsBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_notification_settings, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FragmentNotificationSettingsBinding bind(View view) {
        int i = R.id.notification_setting_lightning_label;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_setting_lightning_label);
        if (textView != null) {
            i = R.id.notification_setting_lightning_toggle;
            Switch r5 = (Switch) ViewBindings.findChildViewById(view, (int) R.id.notification_setting_lightning_toggle);
            if (r5 != null) {
                i = R.id.notification_setting_recyclerview;
                RecyclerView findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.notification_setting_recyclerview);
                if (findChildViewById != null) {
                    i = R.id.notification_setting_severe_label;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_setting_severe_label);
                    if (textView2 != null) {
                        i = R.id.notification_setting_severe_toggle;
                        Switch r8 = (Switch) ViewBindings.findChildViewById(view, (int) R.id.notification_setting_severe_toggle);
                        if (r8 != null) {
                            i = R.id.notification_settings_divider;
                            View findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.notification_settings_divider);
                            if (findChildViewById2 != null) {
                                i = R.id.severe_warning_title;
                                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.severe_warning_title);
                                if (textView3 != null) {
                                    return new FragmentNotificationSettingsBinding((ConstraintLayout) view, textView, r5, findChildViewById, textView2, r8, findChildViewById2, textView3);
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
