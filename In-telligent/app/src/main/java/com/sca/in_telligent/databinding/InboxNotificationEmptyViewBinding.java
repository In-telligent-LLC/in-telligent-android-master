package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class InboxNotificationEmptyViewBinding implements ViewBinding {
    public final TextView inboxNoMessageText;
    public final Button inboxRetryButton;
    private final ConstraintLayout rootView;

    private InboxNotificationEmptyViewBinding(ConstraintLayout constraintLayout, TextView textView, Button button) {
        this.rootView = constraintLayout;
        this.inboxNoMessageText = textView;
        this.inboxRetryButton = button;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static InboxNotificationEmptyViewBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static InboxNotificationEmptyViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.inbox_notification_empty_view, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static InboxNotificationEmptyViewBinding bind(View view) {
        int i = R.id.inbox_no_message_text;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.inbox_no_message_text);
        if (textView != null) {
            i = R.id.inbox_retry_button;
            Button button = (Button) ViewBindings.findChildViewById(view, (int) R.id.inbox_retry_button);
            if (button != null) {
                return new InboxNotificationEmptyViewBinding((ConstraintLayout) view, textView, button);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
