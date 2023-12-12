package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class FragmentNotificationDetailBinding implements ViewBinding {
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    public final RecyclerView notificationDetailAttachmentList;
    public final TextView notificationDetailAttachmentText;
    public final ImageView notificationDetailBackButton;
    public final RelativeLayout notificationDetailBackLayout;
    public final View notificationDetailBottomDivider;
    public final TextView notificationDetailDescriptionText;
    public final TextView notificationDetailInfoText;
    public final ImageView notificationDetailLeftArrow;
    public final ImageView notificationDetailRightArrow;
    public final TextView notificationDetailSave;
    public final TextView notificationDetailTitleText;
    public final TextView notificationDetailTranslate;
    public final TextView notificationDetailTts;
    private final ConstraintLayout rootView;

    private FragmentNotificationDetailBinding(ConstraintLayout constraintLayout, Guideline guideline, Guideline guideline2, RecyclerView recyclerView, TextView textView, ImageView imageView, RelativeLayout relativeLayout, View view, TextView textView2, TextView textView3, ImageView imageView2, ImageView imageView3, TextView textView4, TextView textView5, TextView textView6, TextView textView7) {
        this.rootView = constraintLayout;
        this.guidelineLeft = guideline;
        this.guidelineRight = guideline2;
        this.notificationDetailAttachmentList = recyclerView;
        this.notificationDetailAttachmentText = textView;
        this.notificationDetailBackButton = imageView;
        this.notificationDetailBackLayout = relativeLayout;
        this.notificationDetailBottomDivider = view;
        this.notificationDetailDescriptionText = textView2;
        this.notificationDetailInfoText = textView3;
        this.notificationDetailLeftArrow = imageView2;
        this.notificationDetailRightArrow = imageView3;
        this.notificationDetailSave = textView4;
        this.notificationDetailTitleText = textView5;
        this.notificationDetailTranslate = textView6;
        this.notificationDetailTts = textView7;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static FragmentNotificationDetailBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static FragmentNotificationDetailBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_notification_detail, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FragmentNotificationDetailBinding bind(View view) {
        int i = R.id.guideline_left;
        Guideline findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.guideline_left);
        if (findChildViewById != null) {
            i = R.id.guideline_right;
            Guideline findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.guideline_right);
            if (findChildViewById2 != null) {
                i = R.id.notification_detail_attachment_list;
                RecyclerView findChildViewById3 = ViewBindings.findChildViewById(view, (int) R.id.notification_detail_attachment_list);
                if (findChildViewById3 != null) {
                    i = R.id.notification_detail_attachment_text;
                    TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_detail_attachment_text);
                    if (textView != null) {
                        i = R.id.notification_detail_back_button;
                        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.notification_detail_back_button);
                        if (imageView != null) {
                            i = R.id.notification_detail_back_layout;
                            RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, (int) R.id.notification_detail_back_layout);
                            if (relativeLayout != null) {
                                i = R.id.notification_detail_bottom_divider;
                                View findChildViewById4 = ViewBindings.findChildViewById(view, (int) R.id.notification_detail_bottom_divider);
                                if (findChildViewById4 != null) {
                                    i = R.id.notification_detail_description_text;
                                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_detail_description_text);
                                    if (textView2 != null) {
                                        i = R.id.notification_detail_info_text;
                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_detail_info_text);
                                        if (textView3 != null) {
                                            i = R.id.notification_detail_left_arrow;
                                            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.notification_detail_left_arrow);
                                            if (imageView2 != null) {
                                                i = R.id.notification_detail_right_arrow;
                                                ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.notification_detail_right_arrow);
                                                if (imageView3 != null) {
                                                    i = R.id.notification_detail_save;
                                                    TextView textView4 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_detail_save);
                                                    if (textView4 != null) {
                                                        i = R.id.notification_detail_title_text;
                                                        TextView textView5 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_detail_title_text);
                                                        if (textView5 != null) {
                                                            i = R.id.notification_detail_translate;
                                                            TextView textView6 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_detail_translate);
                                                            if (textView6 != null) {
                                                                i = R.id.notification_detail_tts;
                                                                TextView textView7 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.notification_detail_tts);
                                                                if (textView7 != null) {
                                                                    return new FragmentNotificationDetailBinding((ConstraintLayout) view, findChildViewById, findChildViewById2, findChildViewById3, textView, imageView, relativeLayout, findChildViewById4, textView2, textView3, imageView2, imageView3, textView4, textView5, textView6, textView7);
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
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
