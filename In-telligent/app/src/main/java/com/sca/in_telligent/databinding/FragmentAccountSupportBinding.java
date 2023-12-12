package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class FragmentAccountSupportBinding implements ViewBinding {
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    public final FieldRequiredLayoutBinding requiredEnterMessage;
    private final ScrollView rootView;
    public final View settingsSupportDivider;
    public final TextView settingsSupportEmailText;
    public final TextView settingsSupportGeneralText;
    public final TextView settingsSupportHoursField;
    public final TextView settingsSupportHoursText;
    public final EditText settingsSupportMessageEdittext;
    public final TextView settingsSupportNumberField;
    public final TextView settingsSupportNumberText;
    public final TextView settingsSupportSendButton;
    public final TextView settingsSupportTitleText;

    private FragmentAccountSupportBinding(ScrollView scrollView, Guideline guideline, Guideline guideline2, FieldRequiredLayoutBinding fieldRequiredLayoutBinding, View view, TextView textView, TextView textView2, TextView textView3, TextView textView4, EditText editText, TextView textView5, TextView textView6, TextView textView7, TextView textView8) {
        this.rootView = scrollView;
        this.guidelineLeft = guideline;
        this.guidelineRight = guideline2;
        this.requiredEnterMessage = fieldRequiredLayoutBinding;
        this.settingsSupportDivider = view;
        this.settingsSupportEmailText = textView;
        this.settingsSupportGeneralText = textView2;
        this.settingsSupportHoursField = textView3;
        this.settingsSupportHoursText = textView4;
        this.settingsSupportMessageEdittext = editText;
        this.settingsSupportNumberField = textView5;
        this.settingsSupportNumberText = textView6;
        this.settingsSupportSendButton = textView7;
        this.settingsSupportTitleText = textView8;
    }

    public ScrollView getRoot() {
        return this.rootView;
    }

    public static FragmentAccountSupportBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static FragmentAccountSupportBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_account_support, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FragmentAccountSupportBinding bind(View view) {
        int i = R.id.guideline_left;
        Guideline findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.guideline_left);
        if (findChildViewById != null) {
            i = R.id.guideline_right;
            Guideline findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.guideline_right);
            if (findChildViewById2 != null) {
                i = R.id.required_enter_message;
                View findChildViewById3 = ViewBindings.findChildViewById(view, (int) R.id.required_enter_message);
                if (findChildViewById3 != null) {
                    FieldRequiredLayoutBinding bind = FieldRequiredLayoutBinding.bind(findChildViewById3);
                    i = R.id.settings_support_divider;
                    View findChildViewById4 = ViewBindings.findChildViewById(view, (int) R.id.settings_support_divider);
                    if (findChildViewById4 != null) {
                        i = R.id.settings_support_email_text;
                        TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_support_email_text);
                        if (textView != null) {
                            i = R.id.settings_support_general_text;
                            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_support_general_text);
                            if (textView2 != null) {
                                i = R.id.settings_support_hours_field;
                                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_support_hours_field);
                                if (textView3 != null) {
                                    i = R.id.settings_support_hours_text;
                                    TextView textView4 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_support_hours_text);
                                    if (textView4 != null) {
                                        i = R.id.settings_support_message_edittext;
                                        EditText editText = (EditText) ViewBindings.findChildViewById(view, (int) R.id.settings_support_message_edittext);
                                        if (editText != null) {
                                            i = R.id.settings_support_number_field;
                                            TextView textView5 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_support_number_field);
                                            if (textView5 != null) {
                                                i = R.id.settings_support_number_text;
                                                TextView textView6 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_support_number_text);
                                                if (textView6 != null) {
                                                    i = R.id.settings_support_send_button;
                                                    TextView textView7 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_support_send_button);
                                                    if (textView7 != null) {
                                                        i = R.id.settings_support_title_text;
                                                        TextView textView8 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.settings_support_title_text);
                                                        if (textView8 != null) {
                                                            return new FragmentAccountSupportBinding((ScrollView) view, findChildViewById, findChildViewById2, bind, findChildViewById4, textView, textView2, textView3, textView4, editText, textView5, textView6, textView7, textView8);
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
