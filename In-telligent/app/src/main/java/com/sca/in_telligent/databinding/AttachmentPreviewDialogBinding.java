package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;
import com.sca.in_telligent.util.ZoomableImageView;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class AttachmentPreviewDialogBinding implements ViewBinding {
    public final ImageView attachmentPreviewClose;
    public final ZoomableImageView attachmentPreviewImage;
    public final VideoView attachmentPreviewVideoView;
    private final ConstraintLayout rootView;

    private AttachmentPreviewDialogBinding(ConstraintLayout constraintLayout, ImageView imageView, ZoomableImageView zoomableImageView, VideoView videoView) {
        this.rootView = constraintLayout;
        this.attachmentPreviewClose = imageView;
        this.attachmentPreviewImage = zoomableImageView;
        this.attachmentPreviewVideoView = videoView;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static AttachmentPreviewDialogBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static AttachmentPreviewDialogBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.attachment_preview_dialog, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static AttachmentPreviewDialogBinding bind(View view) {
        int i = R.id.attachment_preview_close;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.attachment_preview_close);
        if (imageView != null) {
            i = R.id.attachment_preview_image;
            ZoomableImageView zoomableImageView = (ZoomableImageView) ViewBindings.findChildViewById(view, (int) R.id.attachment_preview_image);
            if (zoomableImageView != null) {
                i = R.id.attachment_preview_videoView;
                VideoView videoView = (VideoView) ViewBindings.findChildViewById(view, (int) R.id.attachment_preview_videoView);
                if (videoView != null) {
                    return new AttachmentPreviewDialogBinding((ConstraintLayout) view, imageView, zoomableImageView, videoView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
