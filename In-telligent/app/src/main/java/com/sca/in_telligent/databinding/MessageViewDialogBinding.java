package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class MessageViewDialogBinding implements ViewBinding {
    public final ImageView logo;
    public final Guideline logoGuidelineLeft;
    public final Guideline logoGuidelineRight;
    public final TextView messagePreviewBodyText;
    public final TextView messagePreviewTitleText;
    public final TextView messageViewOkButton;
    public final TextView messageViewSeeFullMessageButton;
    private final ConstraintLayout rootView;

    private MessageViewDialogBinding(ConstraintLayout constraintLayout, ImageView imageView, Guideline guideline, Guideline guideline2, TextView textView, TextView textView2, TextView textView3, TextView textView4) {
        this.rootView = constraintLayout;
        this.logo = imageView;
        this.logoGuidelineLeft = guideline;
        this.logoGuidelineRight = guideline2;
        this.messagePreviewBodyText = textView;
        this.messagePreviewTitleText = textView2;
        this.messageViewOkButton = textView3;
        this.messageViewSeeFullMessageButton = textView4;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static MessageViewDialogBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static MessageViewDialogBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.message_view_dialog, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static MessageViewDialogBinding bind(View view) {
        int i = R.id.logo;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.logo);
        if (imageView != null) {
            i = R.id.logo_guideline_left;
            Guideline findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.logo_guideline_left);
            if (findChildViewById != null) {
                i = R.id.logo_guideline_right;
                Guideline findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.logo_guideline_right);
                if (findChildViewById2 != null) {
                    i = R.id.message_preview_body_text;
                    TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.message_preview_body_text);
                    if (textView != null) {
                        i = R.id.message_preview_title_text;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.message_preview_title_text);
                        if (textView2 != null) {
                            i = R.id.message_view_ok_button;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.message_view_ok_button);
                            if (textView3 != null) {
                                i = R.id.message_view_see_full_message_button;
                                TextView textView4 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.message_view_see_full_message_button);
                                if (textView4 != null) {
                                    return new MessageViewDialogBinding((ConstraintLayout) view, imageView, findChildViewById, findChildViewById2, textView, textView2, textView3, textView4);
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
