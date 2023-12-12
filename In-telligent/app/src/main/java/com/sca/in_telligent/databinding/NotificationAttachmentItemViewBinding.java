package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class NotificationAttachmentItemViewBinding implements ViewBinding {
    public final ImageView imageviewDocumentType;
    public final TextView notificationAttachmentDocumentTitle;
    public final ImageView notificationAttachmentThumbnailImage;
    public final FrameLayout notificationAttachmentThumbnailImageContainer;
    private final ConstraintLayout rootView;

    private NotificationAttachmentItemViewBinding(ConstraintLayout constraintLayout, ImageView imageView, TextView textView, ImageView imageView2, FrameLayout frameLayout) {
        this.rootView = constraintLayout;
        this.imageviewDocumentType = imageView;
        this.notificationAttachmentDocumentTitle = textView;
        this.notificationAttachmentThumbnailImage = imageView2;
        this.notificationAttachmentThumbnailImageContainer = frameLayout;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static NotificationAttachmentItemViewBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static NotificationAttachmentItemViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.notification_attachment_item_view, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static NotificationAttachmentItemViewBinding bind(View view) {
        int i = R.id.imageview_document_type;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.imageview_document_type);
        if (imageView != null) {
            i = R.id.notification_attachment_document_title;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_attachment_document_title);
            if (textView != null) {
                i = R.id.notification_attachment_thumbnail_image;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.notification_attachment_thumbnail_image);
                if (imageView2 != null) {
                    i = R.id.notification_attachment_thumbnail_image_container;
                    FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, (int) R.id.notification_attachment_thumbnail_image_container);
                    if (frameLayout != null) {
                        return new NotificationAttachmentItemViewBinding((ConstraintLayout) view, imageView, textView, imageView2, frameLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
