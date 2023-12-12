package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class FragmentAlertDetailBinding implements ViewBinding {
    public final TextView alertDetailAttachmentText;
    public final TextView alertDetailDescriptionText;
    public final TextView alertDetailInfoText;
    public final TextView alertDetailTitleText;
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    private final ScrollView rootView;

    private FragmentAlertDetailBinding(ScrollView scrollView, TextView textView, TextView textView2, TextView textView3, TextView textView4, Guideline guideline, Guideline guideline2) {
        this.rootView = scrollView;
        this.alertDetailAttachmentText = textView;
        this.alertDetailDescriptionText = textView2;
        this.alertDetailInfoText = textView3;
        this.alertDetailTitleText = textView4;
        this.guidelineLeft = guideline;
        this.guidelineRight = guideline2;
    }

    public ScrollView getRoot() {
        return this.rootView;
    }

    public static FragmentAlertDetailBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static FragmentAlertDetailBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_alert_detail, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FragmentAlertDetailBinding bind(View view) {
        int i = R.id.alert_detail_attachment_text;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.alert_detail_attachment_text);
        if (textView != null) {
            i = R.id.alert_detail_description_text;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.alert_detail_description_text);
            if (textView2 != null) {
                i = R.id.alert_detail_info_text;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.alert_detail_info_text);
                if (textView3 != null) {
                    i = R.id.alert_detail_title_text;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.alert_detail_title_text);
                    if (textView4 != null) {
                        i = R.id.guideline_left;
                        Guideline findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.guideline_left);
                        if (findChildViewById != null) {
                            i = R.id.guideline_right;
                            Guideline findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.guideline_right);
                            if (findChildViewById2 != null) {
                                return new FragmentAlertDetailBinding((ScrollView) view, textView, textView2, textView3, textView4, findChildViewById, findChildViewById2);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
