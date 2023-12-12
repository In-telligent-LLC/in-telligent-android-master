package com.sca.in_telligent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sca.in_telligent.R;
import com.sca.in_telligent.util.MySpinner;

public final class FragmentContactMessageBinding implements ViewBinding {
    public final MySpinner contactAlertSpinner;
    public final RelativeLayout contactAlertSpinnerLayout;
    public final ImageView contactMessageBackButton;
    public final TextView contactMessageBackText;
    public final ConstraintLayout contactSendAttachmentLayout;
    public final ProgressBar contactSendAttachmentProgressBar;
    public final TextView contactSendAttachmentText;
    public final ImageView contactSendMessageAttachmentIcon;
    public final TextView contactSendMessageButton;
    public final EditText contactSendMessageEdittext;
    public final RelativeLayout contactSendSpinnerLayout;
    public final EditText contactSendTitleEdittext;
    public final MySpinner contactSendToSpinner;
    public final TextView contactTitleText;
    public final Guideline guidelineLeft;
    public final Guideline guidelineRight;
    public final FieldRequiredLayoutBinding requiredAlertType;
    public final FieldRequiredLayoutBinding requiredEnterMessage;
    public final FieldRequiredLayoutBinding requiredEnterTitle;
    public final FieldRequiredLayoutBinding requiredSendTo;
    private final ConstraintLayout rootView;

    private FragmentContactMessageBinding(ConstraintLayout constraintLayout, MySpinner mySpinner, RelativeLayout relativeLayout, ImageView imageView, TextView textView, ConstraintLayout constraintLayout2, ProgressBar progressBar, TextView textView2, ImageView imageView2, TextView textView3, EditText editText, RelativeLayout relativeLayout2, EditText editText2, MySpinner mySpinner2, TextView textView4, Guideline guideline, Guideline guideline2, FieldRequiredLayoutBinding fieldRequiredLayoutBinding, FieldRequiredLayoutBinding fieldRequiredLayoutBinding2, FieldRequiredLayoutBinding fieldRequiredLayoutBinding3, FieldRequiredLayoutBinding fieldRequiredLayoutBinding4) {
        this.rootView = constraintLayout;
        this.contactAlertSpinner = mySpinner;
        this.contactAlertSpinnerLayout = relativeLayout;
        this.contactMessageBackButton = imageView;
        this.contactMessageBackText = textView;
        this.contactSendAttachmentLayout = constraintLayout2;
        this.contactSendAttachmentProgressBar = progressBar;
        this.contactSendAttachmentText = textView2;
        this.contactSendMessageAttachmentIcon = imageView2;
        this.contactSendMessageButton = textView3;
        this.contactSendMessageEdittext = editText;
        this.contactSendSpinnerLayout = relativeLayout2;
        this.contactSendTitleEdittext = editText2;
        this.contactSendToSpinner = mySpinner2;
        this.contactTitleText = textView4;
        this.guidelineLeft = guideline;
        this.guidelineRight = guideline2;
        this.requiredAlertType = fieldRequiredLayoutBinding;
        this.requiredEnterMessage = fieldRequiredLayoutBinding2;
        this.requiredEnterTitle = fieldRequiredLayoutBinding3;
        this.requiredSendTo = fieldRequiredLayoutBinding4;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static FragmentContactMessageBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static FragmentContactMessageBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_contact_message, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FragmentContactMessageBinding bind(View view) {
        int i = R.id.contact_alert_spinner;
        MySpinner mySpinner = (MySpinner) ViewBindings.findChildViewById(view, (int) R.id.contact_alert_spinner);
        if (mySpinner != null) {
            i = R.id.contact_alert_spinner_layout;
            RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, (int) R.id.contact_alert_spinner_layout);
            if (relativeLayout != null) {
                i = R.id.contact_message_back_button;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.contact_message_back_button);
                if (imageView != null) {
                    i = R.id.contact_message_back_text;
                    TextView textView = (TextView) ViewBindings.findChildViewById(view, (int) R.id.contact_message_back_text);
                    if (textView != null) {
                        i = R.id.contact_send_attachment_layout;
                        ConstraintLayout findChildViewById = ViewBindings.findChildViewById(view, (int) R.id.contact_send_attachment_layout);
                        if (findChildViewById != null) {
                            i = R.id.contact_send_attachment_progress_bar;
                            ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(view, (int) R.id.contact_send_attachment_progress_bar);
                            if (progressBar != null) {
                                i = R.id.contact_send_attachment_text;
                                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.contact_send_attachment_text);
                                if (textView2 != null) {
                                    i = R.id.contact_send_message_attachment_icon;
                                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, (int) R.id.contact_send_message_attachment_icon);
                                    if (imageView2 != null) {
                                        i = R.id.contact_send_message_button;
                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.contact_send_message_button);
                                        if (textView3 != null) {
                                            i = R.id.contact_send_message_edittext;
                                            EditText editText = (EditText) ViewBindings.findChildViewById(view, (int) R.id.contact_send_message_edittext);
                                            if (editText != null) {
                                                i = R.id.contact_send_spinner_layout;
                                                RelativeLayout relativeLayout2 = (RelativeLayout) ViewBindings.findChildViewById(view, (int) R.id.contact_send_spinner_layout);
                                                if (relativeLayout2 != null) {
                                                    i = R.id.contact_send_title_edittext;
                                                    EditText editText2 = (EditText) ViewBindings.findChildViewById(view, (int) R.id.contact_send_title_edittext);
                                                    if (editText2 != null) {
                                                        i = R.id.contact_send_to_spinner;
                                                        MySpinner mySpinner2 = (MySpinner) ViewBindings.findChildViewById(view, (int) R.id.contact_send_to_spinner);
                                                        if (mySpinner2 != null) {
                                                            i = R.id.contact_title_text;
                                                            TextView textView4 = (TextView) ViewBindings.findChildViewById(view, (int) R.id.contact_title_text);
                                                            if (textView4 != null) {
                                                                i = R.id.guideline_left;
                                                                Guideline findChildViewById2 = ViewBindings.findChildViewById(view, (int) R.id.guideline_left);
                                                                if (findChildViewById2 != null) {
                                                                    i = R.id.guideline_right;
                                                                    Guideline findChildViewById3 = ViewBindings.findChildViewById(view, (int) R.id.guideline_right);
                                                                    if (findChildViewById3 != null) {
                                                                        i = R.id.required_alert_type;
                                                                        View findChildViewById4 = ViewBindings.findChildViewById(view, (int) R.id.required_alert_type);
                                                                        if (findChildViewById4 != null) {
                                                                            FieldRequiredLayoutBinding bind = FieldRequiredLayoutBinding.bind(findChildViewById4);
                                                                            i = R.id.required_enter_message;
                                                                            View findChildViewById5 = ViewBindings.findChildViewById(view, (int) R.id.required_enter_message);
                                                                            if (findChildViewById5 != null) {
                                                                                FieldRequiredLayoutBinding bind2 = FieldRequiredLayoutBinding.bind(findChildViewById5);
                                                                                i = R.id.required_enter_title;
                                                                                View findChildViewById6 = ViewBindings.findChildViewById(view, (int) R.id.required_enter_title);
                                                                                if (findChildViewById6 != null) {
                                                                                    FieldRequiredLayoutBinding bind3 = FieldRequiredLayoutBinding.bind(findChildViewById6);
                                                                                    i = R.id.required_send_to;
                                                                                    View findChildViewById7 = ViewBindings.findChildViewById(view, (int) R.id.required_send_to);
                                                                                    if (findChildViewById7 != null) {
                                                                                        return new FragmentContactMessageBinding((ConstraintLayout) view, mySpinner, relativeLayout, imageView, textView, findChildViewById, progressBar, textView2, imageView2, textView3, editText, relativeLayout2, editText2, mySpinner2, textView4, findChildViewById2, findChildViewById3, bind, bind2, bind3, FieldRequiredLayoutBinding.bind(findChildViewById7));
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
