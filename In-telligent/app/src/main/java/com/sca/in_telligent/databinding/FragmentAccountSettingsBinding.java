package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class FragmentAccountSettingsBinding implements ViewBinding {
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    private final ConstraintLayout rootView;
    public final TextView settingsAccountAlertLanguageText;
    public final View settingsAccountDivider;
    public final TextView settingsAccountLanguageSelected;
    public final TextView settingsAccountMailEdittext;
    public final TextView settingsAccountMailLabel;
    public final TextView settingsAccountNameEdittext;
    public final TextView settingsAccountNameLabel;
    public final TextView settingsAccountTitleText;

    private FragmentAccountSettingsBinding(ConstraintLayout constraintLayout, Guideline guideline, Guideline guideline2, TextView textView, View view, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6, TextView textView7) {
        this.rootView = constraintLayout;
        this.guidelineLeft = guideline;
        this.guidelineRight = guideline2;
        this.settingsAccountAlertLanguageText = textView;
        this.settingsAccountDivider = view;
        this.settingsAccountLanguageSelected = textView2;
        this.settingsAccountMailEdittext = textView3;
        this.settingsAccountMailLabel = textView4;
        this.settingsAccountNameEdittext = textView5;
        this.settingsAccountNameLabel = textView6;
        this.settingsAccountTitleText = textView7;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static FragmentAccountSettingsBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static FragmentAccountSettingsBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_account_settings, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FragmentAccountSettingsBinding bind(View view) {
        int i = R.id.guideline_left;
        Guideline findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.guideline_left);
        if (findChildViewById != null) {
            i = R.id.guideline_right;
            Guideline findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.guideline_right);
            if (findChildViewById2 != null) {
                i = R.id.settings_account_alert_language_text;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_account_alert_language_text);
                if (textView != null) {
                    i = R.id.settings_account_divider;
                    View findChildViewById3 = ViewBindings.findChildViewById(view, (int) R.id.settings_account_divider);
                    if (findChildViewById3 != null) {
                        i = R.id.settings_account_language_selected;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_account_language_selected);
                        if (textView2 != null) {
                            i = R.id.settings_account_mail_edittext;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_account_mail_edittext);
                            if (textView3 != null) {
                                i = R.id.settings_account_mail_label;
                                TextView textView4 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_account_mail_label);
                                if (textView4 != null) {
                                    i = R.id.settings_account_name_edittext;
                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_account_name_edittext);
                                    if (textView5 != null) {
                                        i = R.id.settings_account_name_label;
                                        TextView textView6 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_account_name_label);
                                        if (textView6 != null) {
                                            i = R.id.settings_account_title_text;
                                            TextView textView7 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_account_title_text);
                                            if (textView7 != null) {
                                                return new FragmentAccountSettingsBinding((ConstraintLayout) view, findChildViewById, findChildViewById2, textView, findChildViewById3, textView2, textView3, textView4, textView5, textView6, textView7);
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
