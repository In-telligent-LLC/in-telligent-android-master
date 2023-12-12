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
public final class InboxNotificationItemViewBinding implements ViewBinding {
    public final Guideline guidelineLeftMargin;
    public final Guideline guidelineRightMargin;
    public final TextView notificationDescriptionText;
    public final TextView notificationInfoText;
    public final ImageView notificationProfileImage;
    public final ImageView notificationReadImage;
    public final TextView notificationTitleText;
    public final ImageView notificationTrashImage;
    public final ImageView notificationTypeImage;
    public final TextView notificationViewButton;
    private final ConstraintLayout rootView;

    private InboxNotificationItemViewBinding(ConstraintLayout constraintLayout, Guideline guideline, Guideline guideline2, TextView textView, TextView textView2, ImageView imageView, ImageView imageView2, TextView textView3, ImageView imageView3, ImageView imageView4, TextView textView4) {
        this.rootView = constraintLayout;
        this.guidelineLeftMargin = guideline;
        this.guidelineRightMargin = guideline2;
        this.notificationDescriptionText = textView;
        this.notificationInfoText = textView2;
        this.notificationProfileImage = imageView;
        this.notificationReadImage = imageView2;
        this.notificationTitleText = textView3;
        this.notificationTrashImage = imageView3;
        this.notificationTypeImage = imageView4;
        this.notificationViewButton = textView4;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static InboxNotificationItemViewBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static InboxNotificationItemViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.inbox_notification_item_view, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static InboxNotificationItemViewBinding bind(View view) {
        int i = R.id.guideline_left_margin;
        Guideline findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.guideline_left_margin);
        if (findChildViewById != null) {
            i = R.id.guideline_right_margin;
            Guideline findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.guideline_right_margin);
            if (findChildViewById2 != null) {
                i = R.id.notification_description_text;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_description_text);
                if (textView != null) {
                    i = R.id.notification_info_text;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_info_text);
                    if (textView2 != null) {
                        i = R.id.notification_profile_image;
                        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.notification_profile_image);
                        if (imageView != null) {
                            i = R.id.notification_read_image;
                            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.notification_read_image);
                            if (imageView2 != null) {
                                i = R.id.notification_title_text;
                                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_title_text);
                                if (textView3 != null) {
                                    i = R.id.notification_trash_image;
                                    ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.notification_trash_image);
                                    if (imageView3 != null) {
                                        i = R.id.notification_type_image;
                                        ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.notification_type_image);
                                        if (imageView4 != null) {
                                            i = R.id.notification_view_button;
                                            TextView textView4 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_view_button);
                                            if (textView4 != null) {
                                                return new InboxNotificationItemViewBinding((ConstraintLayout) view, findChildViewById, findChildViewById2, textView, textView2, imageView, imageView2, textView3, imageView3, imageView4, textView4);
                                            }
                                        }
                                    }
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
