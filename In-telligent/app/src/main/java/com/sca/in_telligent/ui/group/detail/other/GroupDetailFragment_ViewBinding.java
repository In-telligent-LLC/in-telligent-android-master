package com.sca.in_telligent.ui.group.detail.other;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.sca.in_telligent.R;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class GroupDetailFragment_ViewBinding implements Unbinder {
    private GroupDetailFragment target;
    private View view7f080149;
    private View view7f08014b;
    private View view7f08014d;
    private View view7f08014f;
    private View view7f080150;

    public GroupDetailFragment_ViewBinding(final GroupDetailFragment groupDetailFragment, View view) {
        this.target = groupDetailFragment;
        groupDetailFragment.descriptionLongText = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.group_detail_description_long, "field 'descriptionLongText'", TextView.class);
        View findRequiredView = Utils.findRequiredView(view, (int) R.id.group_detail_read_more, "field 'readMoreButton' and method 'readMoreClick'");
        groupDetailFragment.readMoreButton = (TextView) Utils.castView(findRequiredView, (int) R.id.group_detail_read_more, "field 'readMoreButton'", TextView.class);
        this.view7f08014f = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: com.sca.in_telligent.ui.group.detail.other.GroupDetailFragment_ViewBinding.1
            public void doClick(View view2) {
                groupDetailFragment.readMoreClick(view2);
            }
        });
        groupDetailFragment.descriptionShortText = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.group_detail_description_short, "field 'descriptionShortText'", TextView.class);
        groupDetailFragment.mainImage = (ImageView) Utils.findRequiredViewAsType(view, (int) R.id.group_detail_main_image, "field 'mainImage'", ImageView.class);
        groupDetailFragment.groupDetailInfo = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.group_detail_info, "field 'groupDetailInfo'", TextView.class);
        groupDetailFragment.groupName = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.group_detail_name, "field 'groupName'", TextView.class);
        View findRequiredView2 = Utils.findRequiredView(view, (int) R.id.group_detail_right_arrow, "field 'rightArrow' and method 'rightClick'");
        groupDetailFragment.rightArrow = (ImageView) Utils.castView(findRequiredView2, (int) R.id.group_detail_right_arrow, "field 'rightArrow'", ImageView.class);
        this.view7f080150 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: com.sca.in_telligent.ui.group.detail.other.GroupDetailFragment_ViewBinding.2
            public void doClick(View view2) {
                groupDetailFragment.rightClick(view2);
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, (int) R.id.group_detail_left_arrow, "field 'leftArrow' and method 'leftClick'");
        groupDetailFragment.leftArrow = (ImageView) Utils.castView(findRequiredView3, (int) R.id.group_detail_left_arrow, "field 'leftArrow'", ImageView.class);
        this.view7f08014b = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: com.sca.in_telligent.ui.group.detail.other.GroupDetailFragment_ViewBinding.3
            public void doClick(View view2) {
                groupDetailFragment.leftClick(view2);
            }
        });
        View findRequiredView4 = Utils.findRequiredView(view, (int) R.id.group_detail_message_feed, "field 'messageFeedButton' and method 'messageFeedClick'");
        groupDetailFragment.messageFeedButton = (TextView) Utils.castView(findRequiredView4, (int) R.id.group_detail_message_feed, "field 'messageFeedButton'", TextView.class);
        this.view7f08014d = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() { // from class: com.sca.in_telligent.ui.group.detail.other.GroupDetailFragment_ViewBinding.4
            public void doClick(View view2) {
                groupDetailFragment.messageFeedClick(view2);
            }
        });
        View findRequiredView5 = Utils.findRequiredView(view, (int) R.id.group_detail_disconnect, "field 'disconnectButton' and method 'onClick'");
        groupDetailFragment.disconnectButton = (TextView) Utils.castView(findRequiredView5, (int) R.id.group_detail_disconnect, "field 'disconnectButton'", TextView.class);
        this.view7f080149 = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() { // from class: com.sca.in_telligent.ui.group.detail.other.GroupDetailFragment_ViewBinding.5
            public void doClick(View view2) {
                groupDetailFragment.onClick(view2);
            }
        });
    }

    public void unbind() {
        GroupDetailFragment groupDetailFragment = this.target;
        if (groupDetailFragment == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        groupDetailFragment.descriptionLongText = null;
        groupDetailFragment.readMoreButton = null;
        groupDetailFragment.descriptionShortText = null;
        groupDetailFragment.mainImage = null;
        groupDetailFragment.groupDetailInfo = null;
        groupDetailFragment.groupName = null;
        groupDetailFragment.rightArrow = null;
        groupDetailFragment.leftArrow = null;
        groupDetailFragment.messageFeedButton = null;
        groupDetailFragment.disconnectButton = null;
        this.view7f08014f.setOnClickListener(null);
        this.view7f08014f = null;
        this.view7f080150.setOnClickListener(null);
        this.view7f080150 = null;
        this.view7f08014b.setOnClickListener(null);
        this.view7f08014b = null;
        this.view7f08014d.setOnClickListener(null);
        this.view7f08014d = null;
        this.view7f080149.setOnClickListener(null);
        this.view7f080149 = null;
    }
}
