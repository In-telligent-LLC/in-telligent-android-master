package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class FragmentGroupDetailBinding implements ViewBinding {
    public final Barrier barrierBottom;
    public final Guideline centerView;
    public final TextView groupDetailDescriptionLong;
    public final TextView groupDetailDescriptionShort;
    public final TextView groupDetailDisconnect;
    public final TextView groupDetailInfo;
    public final ImageView groupDetailLeftArrow;
    public final ImageView groupDetailMainImage;
    public final TextView groupDetailMessageFeed;
    public final TextView groupDetailName;
    public final TextView groupDetailReadMore;
    public final ImageView groupDetailRightArrow;
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    private final ScrollView rootView;

    private FragmentGroupDetailBinding(ScrollView scrollView, Barrier barrier, Guideline guideline, TextView textView, TextView textView2, TextView textView3, TextView textView4, ImageView imageView, ImageView imageView2, TextView textView5, TextView textView6, TextView textView7, ImageView imageView3, Guideline guideline2, Guideline guideline3) {
        this.rootView = scrollView;
        this.barrierBottom = barrier;
        this.centerView = guideline;
        this.groupDetailDescriptionLong = textView;
        this.groupDetailDescriptionShort = textView2;
        this.groupDetailDisconnect = textView3;
        this.groupDetailInfo = textView4;
        this.groupDetailLeftArrow = imageView;
        this.groupDetailMainImage = imageView2;
        this.groupDetailMessageFeed = textView5;
        this.groupDetailName = textView6;
        this.groupDetailReadMore = textView7;
        this.groupDetailRightArrow = imageView3;
        this.guidelineLeft = guideline2;
        this.guidelineRight = guideline3;
    }

    public ScrollView getRoot() {
        return this.rootView;
    }

    public static FragmentGroupDetailBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static FragmentGroupDetailBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_group_detail, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FragmentGroupDetailBinding bind(View view) {
        int i = R.id.barrier_bottom;
        Barrier findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.barrier_bottom);
        if (findChildViewById != null) {
            i = R.id.center_view;
            Guideline findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.center_view);
            if (findChildViewById2 != null) {
                i = R.id.group_detail_description_long;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.group_detail_description_long);
                if (textView != null) {
                    i = R.id.group_detail_description_short;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.group_detail_description_short);
                    if (textView2 != null) {
                        i = R.id.group_detail_disconnect;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.group_detail_disconnect);
                        if (textView3 != null) {
                            i = R.id.group_detail_info;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.group_detail_info);
                            if (textView4 != null) {
                                i = R.id.group_detail_left_arrow;
                                ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.group_detail_left_arrow);
                                if (imageView != null) {
                                    i = R.id.group_detail_main_image;
                                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.group_detail_main_image);
                                    if (imageView2 != null) {
                                        i = R.id.group_detail_message_feed;
                                        TextView textView5 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.group_detail_message_feed);
                                        if (textView5 != null) {
                                            i = R.id.group_detail_name;
                                            TextView textView6 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.group_detail_name);
                                            if (textView6 != null) {
                                                i = R.id.group_detail_read_more;
                                                TextView textView7 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.group_detail_read_more);
                                                if (textView7 != null) {
                                                    i = R.id.group_detail_right_arrow;
                                                    ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.group_detail_right_arrow);
                                                    if (imageView3 != null) {
                                                        i = R.id.guideline_left;
                                                        Guideline findChildViewById3 = ViewBindings.findChildViewById(view, (int) R.id.guideline_left);
                                                        if (findChildViewById3 != null) {
                                                            i = R.id.guideline_right;
                                                            Guideline findChildViewById4 = ViewBindings.findChildViewById(view, (int) R.id.guideline_right);
                                                            if (findChildViewById4 != null) {
                                                                return new FragmentGroupDetailBinding((ScrollView) view, findChildViewById, findChildViewById2, textView, textView2, textView3, textView4, imageView, imageView2, textView5, textView6, textView7, imageView3, findChildViewById3, findChildViewById4);
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
