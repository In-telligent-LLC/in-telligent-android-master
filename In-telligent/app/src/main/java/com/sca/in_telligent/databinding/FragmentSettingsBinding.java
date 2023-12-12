package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.FragmentTabHost;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class FragmentSettingsBinding implements ViewBinding {
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    private final ConstraintLayout rootView;
    public final ImageView settingsBackButton;
    public final TextView settingsBackText;
    public final TextView settingsLogoutText;
    public final TextView settingsTitleText;
    public final FrameLayout tabcontent;
    public final FragmentTabHost tabhost;
    public final TabWidget tabs;

    private FragmentSettingsBinding(ConstraintLayout constraintLayout, Guideline guideline, Guideline guideline2, ImageView imageView, TextView textView, TextView textView2, TextView textView3, FrameLayout frameLayout, FragmentTabHost fragmentTabHost, TabWidget tabWidget) {
        this.rootView = constraintLayout;
        this.guidelineLeft = guideline;
        this.guidelineRight = guideline2;
        this.settingsBackButton = imageView;
        this.settingsBackText = textView;
        this.settingsLogoutText = textView2;
        this.settingsTitleText = textView3;
        this.tabcontent = frameLayout;
        this.tabhost = fragmentTabHost;
        this.tabs = tabWidget;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static FragmentSettingsBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static FragmentSettingsBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_settings, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FragmentSettingsBinding bind(View view) {
        int i = R.id.guideline_left;
        Guideline findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.guideline_left);
        if (findChildViewById != null) {
            i = R.id.guideline_right;
            Guideline findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.guideline_right);
            if (findChildViewById2 != null) {
                i = R.id.settings_back_button;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.settings_back_button);
                if (imageView != null) {
                    i = R.id.settings_back_text;
                    TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_back_text);
                    if (textView != null) {
                        i = R.id.settings_logout_text;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_logout_text);
                        if (textView2 != null) {
                            i = R.id.settings_title_text;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_title_text);
                            if (textView3 != null) {
                                i = 16908305;
                                FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, 16908305);
                                if (frameLayout != null) {
                                    i = 16908306;
                                    FragmentTabHost findChildViewById3 = ViewBindings.findChildViewById(view, 16908306);
                                    if (findChildViewById3 != null) {
                                        i = 16908307;
                                        TabWidget tabWidget = (TabWidget) ViewBindings.findChildViewById(view, 16908307);
                                        if (tabWidget != null) {
                                            return new FragmentSettingsBinding((ConstraintLayout) view, findChildViewById, findChildViewById2, imageView, textView, textView2, textView3, frameLayout, findChildViewById3, tabWidget);
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
