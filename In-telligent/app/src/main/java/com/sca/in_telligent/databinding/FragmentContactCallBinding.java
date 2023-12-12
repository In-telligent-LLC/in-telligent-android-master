package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class FragmentContactCallBinding implements ViewBinding {
    public final Chronometer chronometer;
    public final ImageView contactCallStatusImage;
    public final ConstraintLayout dialConstraintOne;
    public final ConstraintLayout dialConstraintThree;
    public final ConstraintLayout dialConstraintTwo;
    public final RelativeLayout dialEightLayout;
    public final TextView dialEightText;
    public final RelativeLayout dialFiveLayout;
    public final TextView dialFiveText;
    public final RelativeLayout dialFourLayout;
    public final TextView dialFourText;
    public final RelativeLayout dialNineLayout;
    public final TextView dialNineText;
    public final RelativeLayout dialOneLayout;
    public final ConstraintLayout dialPadLayout;
    public final RelativeLayout dialSevenLayout;
    public final TextView dialSevenText;
    public final RelativeLayout dialSixLayout;
    public final TextView dialSixText;
    public final RelativeLayout dialThreeLayout;
    public final TextView dialThreeText;
    public final RelativeLayout dialTwoLayout;
    public final TextView dialTwoText;
    public final FloatingActionButton fabEndCall;
    public final FloatingActionButton fabStartCall;
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    public final Guideline guidelineTop;
    private final ConstraintLayout rootView;

    private FragmentContactCallBinding(ConstraintLayout constraintLayout, Chronometer chronometer, ImageView imageView, ConstraintLayout constraintLayout2, ConstraintLayout constraintLayout3, ConstraintLayout constraintLayout4, RelativeLayout relativeLayout, TextView textView, RelativeLayout relativeLayout2, TextView textView2, RelativeLayout relativeLayout3, TextView textView3, RelativeLayout relativeLayout4, TextView textView4, RelativeLayout relativeLayout5, ConstraintLayout constraintLayout5, RelativeLayout relativeLayout6, TextView textView5, RelativeLayout relativeLayout7, TextView textView6, RelativeLayout relativeLayout8, TextView textView7, RelativeLayout relativeLayout9, TextView textView8, FloatingActionButton floatingActionButton, FloatingActionButton floatingActionButton2, Guideline guideline, Guideline guideline2, Guideline guideline3) {
        this.rootView = constraintLayout;
        this.chronometer = chronometer;
        this.contactCallStatusImage = imageView;
        this.dialConstraintOne = constraintLayout2;
        this.dialConstraintThree = constraintLayout3;
        this.dialConstraintTwo = constraintLayout4;
        this.dialEightLayout = relativeLayout;
        this.dialEightText = textView;
        this.dialFiveLayout = relativeLayout2;
        this.dialFiveText = textView2;
        this.dialFourLayout = relativeLayout3;
        this.dialFourText = textView3;
        this.dialNineLayout = relativeLayout4;
        this.dialNineText = textView4;
        this.dialOneLayout = relativeLayout5;
        this.dialPadLayout = constraintLayout5;
        this.dialSevenLayout = relativeLayout6;
        this.dialSevenText = textView5;
        this.dialSixLayout = relativeLayout7;
        this.dialSixText = textView6;
        this.dialThreeLayout = relativeLayout8;
        this.dialThreeText = textView7;
        this.dialTwoLayout = relativeLayout9;
        this.dialTwoText = textView8;
        this.fabEndCall = floatingActionButton;
        this.fabStartCall = floatingActionButton2;
        this.guidelineLeft = guideline;
        this.guidelineRight = guideline2;
        this.guidelineTop = guideline3;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static FragmentContactCallBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static FragmentContactCallBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_contact_call, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FragmentContactCallBinding bind(View view) {
        int i = 2131230881;
        Chronometer chronometer = (Chronometer) ViewBindings.findChildViewById(view, 2131230881);
        if (chronometer != null) {
            i = R.id.contact_call_status_image;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.contact_call_status_image);
            if (imageView != null) {
                i = R.id.dial_constraint_one;
                ConstraintLayout findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.dial_constraint_one);
                if (findChildViewById != null) {
                    i = R.id.dial_constraint_three;
                    ConstraintLayout findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.dial_constraint_three);
                    if (findChildViewById2 != null) {
                        i = R.id.dial_constraint_two;
                        ConstraintLayout findChildViewById3 = ViewBindings.findChildViewById(view, (int) R.id.dial_constraint_two);
                        if (findChildViewById3 != null) {
                            i = R.id.dialEightLayout;
                            RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, (int) R.id.dialEightLayout);
                            if (relativeLayout != null) {
                                i = R.id.dial_eight_text;
                                TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.dial_eight_text);
                                if (textView != null) {
                                    i = R.id.dialFiveLayout;
                                    RelativeLayout relativeLayout2 = (RelativeLayout) ViewBindings.findChildViewById(view, (int) R.id.dialFiveLayout);
                                    if (relativeLayout2 != null) {
                                        i = R.id.dial_five_text;
                                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.dial_five_text);
                                        if (textView2 != null) {
                                            i = R.id.dialFourLayout;
                                            RelativeLayout relativeLayout3 = (RelativeLayout) ViewBindings.findChildViewById(view, (int) R.id.dialFourLayout);
                                            if (relativeLayout3 != null) {
                                                i = R.id.dial_four_text;
                                                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.dial_four_text);
                                                if (textView3 != null) {
                                                    i = R.id.dialNineLayout;
                                                    RelativeLayout relativeLayout4 = (RelativeLayout) ViewBindings.findChildViewById(view, (int) R.id.dialNineLayout);
                                                    if (relativeLayout4 != null) {
                                                        i = R.id.dial_nine_text;
                                                        TextView textView4 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.dial_nine_text);
                                                        if (textView4 != null) {
                                                            i = R.id.dialOneLayout;
                                                            RelativeLayout relativeLayout5 = (RelativeLayout) ViewBindings.findChildViewById(view, (int) R.id.dialOneLayout);
                                                            if (relativeLayout5 != null) {
                                                                i = R.id.dial_pad_layout;
                                                                ConstraintLayout findChildViewById4 = ViewBindings.findChildViewById(view, (int) R.id.dial_pad_layout);
                                                                if (findChildViewById4 != null) {
                                                                    i = R.id.dialSevenLayout;
                                                                    RelativeLayout relativeLayout6 = (RelativeLayout) ViewBindings.findChildViewById(view, (int) R.id.dialSevenLayout);
                                                                    if (relativeLayout6 != null) {
                                                                        i = R.id.dial_seven_text;
                                                                        TextView textView5 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.dial_seven_text);
                                                                        if (textView5 != null) {
                                                                            i = R.id.dialSixLayout;
                                                                            RelativeLayout relativeLayout7 = (RelativeLayout) ViewBindings.findChildViewById(view, (int) R.id.dialSixLayout);
                                                                            if (relativeLayout7 != null) {
                                                                                i = R.id.dial_six_text;
                                                                                TextView textView6 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.dial_six_text);
                                                                                if (textView6 != null) {
                                                                                    i = R.id.dialThreeLayout;
                                                                                    RelativeLayout relativeLayout8 = (RelativeLayout) ViewBindings.findChildViewById(view, (int) R.id.dialThreeLayout);
                                                                                    if (relativeLayout8 != null) {
                                                                                        i = R.id.dial_three_text;
                                                                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.dial_three_text);
                                                                                        if (textView7 != null) {
                                                                                            i = R.id.dialTwoLayout;
                                                                                            RelativeLayout relativeLayout9 = (RelativeLayout) ViewBindings.findChildViewById(view, (int) R.id.dialTwoLayout);
                                                                                            if (relativeLayout9 != null) {
                                                                                                i = R.id.dial_two_text;
                                                                                                TextView textView8 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.dial_two_text);
                                                                                                if (textView8 != null) {
                                                                                                    i = R.id.fab_end_call;
                                                                                                    FloatingActionButton floatingActionButton = (FloatingActionButton) ViewBindings.findChildViewById(view, (int) R.id.fab_end_call);
                                                                                                    if (floatingActionButton != null) {
                                                                                                        i = R.id.fab_start_call;
                                                                                                        FloatingActionButton floatingActionButton2 = (FloatingActionButton) ViewBindings.findChildViewById(view, (int) R.id.fab_start_call);
                                                                                                        if (floatingActionButton2 != null) {
                                                                                                            i = R.id.guideline_left;
                                                                                                            Guideline findChildViewById5 = ViewBindings.findChildViewById(view, (int) R.id.guideline_left);
                                                                                                            if (findChildViewById5 != null) {
                                                                                                                i = R.id.guideline_right;
                                                                                                                Guideline findChildViewById6 = ViewBindings.findChildViewById(view, (int) R.id.guideline_right);
                                                                                                                if (findChildViewById6 != null) {
                                                                                                                    i = R.id.guideline_top;
                                                                                                                    Guideline findChildViewById7 = ViewBindings.findChildViewById(view, (int) R.id.guideline_top);
                                                                                                                    if (findChildViewById7 != null) {
                                                                                                                        return new FragmentContactCallBinding((ConstraintLayout) view, chronometer, imageView, findChildViewById, findChildViewById2, findChildViewById3, relativeLayout, textView, relativeLayout2, textView2, relativeLayout3, textView3, relativeLayout4, textView4, relativeLayout5, findChildViewById4, relativeLayout6, textView5, relativeLayout7, textView6, relativeLayout8, textView7, relativeLayout9, textView8, floatingActionButton, floatingActionButton2, findChildViewById5, findChildViewById6, findChildViewById7);
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
