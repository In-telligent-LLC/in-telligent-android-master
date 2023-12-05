package com.sca.in_telligent.ui.notificationdetail;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class NotificationDetailFragment_ViewBinding implements Unbinder {
    private NotificationDetailFragment target;
    private View view7f080211;
    private View view7f080215;
    private View view7f080216;
    private View view7f080217;
    private View view7f080219;
    private View view7f08021a;

    public NotificationDetailFragment_ViewBinding(final NotificationDetailFragment notificationDetailFragment, View view) {
        this.target = notificationDetailFragment;
        notificationDetailFragment.notificationTitleText = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.notification_detail_title_text, "field 'notificationTitleText'", TextView.class);
        notificationDetailFragment.notificationInfoText = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.notification_detail_info_text, "field 'notificationInfoText'", TextView.class);
        notificationDetailFragment.notificationAttachmentText = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.notification_detail_attachment_text, "field 'notificationAttachmentText'", TextView.class);
        notificationDetailFragment.notificationDescriptionText = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.notification_detail_description_text, "field 'notificationDescriptionText'", TextView.class);
        notificationDetailFragment.notificationAttachmentList = (RecyclerView) Utils.findRequiredViewAsType(view, (int) R.id.notification_detail_attachment_list, "field 'notificationAttachmentList'", RecyclerView.class);
        View findRequiredView = Utils.findRequiredView(view, (int) R.id.notification_detail_back_layout, "field 'backLayout' and method 'backClick'");
        notificationDetailFragment.backLayout = (RelativeLayout) Utils.castView(findRequiredView, (int) R.id.notification_detail_back_layout, "field 'backLayout'", RelativeLayout.class);
        this.view7f080211 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: com.sca.in_telligent.ui.notificationdetail.NotificationDetailFragment_ViewBinding.1
            public void doClick(View view2) {
                notificationDetailFragment.backClick(view2);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, (int) R.id.notification_detail_translate, "field 'translateButton' and method 'translateClick'");
        notificationDetailFragment.translateButton = (TextView) Utils.castView(findRequiredView2, (int) R.id.notification_detail_translate, "field 'translateButton'", TextView.class);
        this.view7f080219 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: com.sca.in_telligent.ui.notificationdetail.NotificationDetailFragment_ViewBinding.2
            public void doClick(View view2) {
                notificationDetailFragment.translateClick(view2);
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, (int) R.id.notification_detail_tts, "field 'ttsButton' and method 'textToSpeechClick'");
        notificationDetailFragment.ttsButton = (TextView) Utils.castView(findRequiredView3, (int) R.id.notification_detail_tts, "field 'ttsButton'", TextView.class);
        this.view7f08021a = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: com.sca.in_telligent.ui.notificationdetail.NotificationDetailFragment_ViewBinding.3
            public void doClick(View view2) {
                notificationDetailFragment.textToSpeechClick(view2);
            }
        });
        View findRequiredView4 = Utils.findRequiredView(view, (int) R.id.notification_detail_save, "field 'saveButton' and method 'editSave'");
        notificationDetailFragment.saveButton = (TextView) Utils.castView(findRequiredView4, (int) R.id.notification_detail_save, "field 'saveButton'", TextView.class);
        this.view7f080217 = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() { // from class: com.sca.in_telligent.ui.notificationdetail.NotificationDetailFragment_ViewBinding.4
            public void doClick(View view2) {
                notificationDetailFragment.editSave(view2);
            }
        });
        View findRequiredView5 = Utils.findRequiredView(view, (int) R.id.notification_detail_left_arrow, "field 'previousButton' and method 'previousNotification'");
        notificationDetailFragment.previousButton = (ImageView) Utils.castView(findRequiredView5, (int) R.id.notification_detail_left_arrow, "field 'previousButton'", ImageView.class);
        this.view7f080215 = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() { // from class: com.sca.in_telligent.ui.notificationdetail.NotificationDetailFragment_ViewBinding.5
            public void doClick(View view2) {
                notificationDetailFragment.previousNotification(view2);
            }
        });
        View findRequiredView6 = Utils.findRequiredView(view, (int) R.id.notification_detail_right_arrow, "field 'nextButton' and method 'nextNotification'");
        notificationDetailFragment.nextButton = (ImageView) Utils.castView(findRequiredView6, (int) R.id.notification_detail_right_arrow, "field 'nextButton'", ImageView.class);
        this.view7f080216 = findRequiredView6;
        findRequiredView6.setOnClickListener(new DebouncingOnClickListener() { // from class: com.sca.in_telligent.ui.notificationdetail.NotificationDetailFragment_ViewBinding.6
            public void doClick(View view2) {
                notificationDetailFragment.nextNotification(view2);
            }
        });
    }

    public void unbind() {
        NotificationDetailFragment notificationDetailFragment = this.target;
        if (notificationDetailFragment == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        notificationDetailFragment.notificationTitleText = null;
        notificationDetailFragment.notificationInfoText = null;
        notificationDetailFragment.notificationAttachmentText = null;
        notificationDetailFragment.notificationDescriptionText = null;
        notificationDetailFragment.notificationAttachmentList = null;
        notificationDetailFragment.backLayout = null;
        notificationDetailFragment.translateButton = null;
        notificationDetailFragment.ttsButton = null;
        notificationDetailFragment.saveButton = null;
        notificationDetailFragment.previousButton = null;
        notificationDetailFragment.nextButton = null;
        this.view7f080211.setOnClickListener(null);
        this.view7f080211 = null;
        this.view7f080219.setOnClickListener(null);
        this.view7f080219 = null;
        this.view7f08021a.setOnClickListener(null);
        this.view7f08021a = null;
        this.view7f080217.setOnClickListener(null);
        this.view7f080217 = null;
        this.view7f080215.setOnClickListener(null);
        this.view7f080215 = null;
        this.view7f080216.setOnClickListener(null);
        this.view7f080216 = null;
    }
}
