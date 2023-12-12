package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.sca.in_telligent.R;
import java.util.Objects;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class NotificationSettingsEmptyItemBinding implements ViewBinding {
    private final ConstraintLayout rootView;

    private NotificationSettingsEmptyItemBinding(ConstraintLayout constraintLayout) {
        this.rootView = constraintLayout;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static NotificationSettingsEmptyItemBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static NotificationSettingsEmptyItemBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.notification_settings_empty_item, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static NotificationSettingsEmptyItemBinding bind(View view) {
        Objects.requireNonNull(view, "rootView");
        return new NotificationSettingsEmptyItemBinding((ConstraintLayout) view);
    }
}
