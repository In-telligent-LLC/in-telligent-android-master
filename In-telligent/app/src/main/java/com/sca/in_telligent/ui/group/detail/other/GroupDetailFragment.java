package com.sca.in_telligent.ui.group.detail.other;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.SubscribeToCommunityRequest;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriptionRequest;
import com.sca.in_telligent.ui.base.BaseFragment;
import com.sca.in_telligent.ui.group.detail.GroupDetailSelector;
import com.sca.in_telligent.util.AlertUtil;
import com.sca.in_telligent.util.CommonUtils;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class GroupDetailFragment extends BaseFragment implements GroupDetailMvpView {
    public static final String TAG = "GroupDetailFragment";
    Building building;
    private int buildingsSize;
    @BindView(R.id.group_detail_description_long)
    TextView descriptionLongText;
    @BindView(R.id.group_detail_description_short)
    TextView descriptionShortText;
    @BindView(R.id.group_detail_disconnect)
    TextView disconnectButton;
    @BindView(R.id.group_detail_info)
    TextView groupDetailInfo;
    GroupDetailSelector groupDetailSelector;
    @BindView(R.id.group_detail_name)
    TextView groupName;
    @BindView(R.id.group_detail_left_arrow)
    ImageView leftArrow;
    @Inject
    GroupDetailMvpPresenter<GroupDetailMvpView> mPresenter;
    @BindView(R.id.group_detail_main_image)
    ImageView mainImage;
    @BindView(R.id.group_detail_message_feed)
    TextView messageFeedButton;
    private int position;
    @BindView(R.id.group_detail_read_more)
    TextView readMoreButton;
    @BindView(R.id.group_detail_right_arrow)
    ImageView rightArrow;
    private boolean textExpanded = false;

    public static GroupDetailFragment newInstance(Building building, int i, int i2) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("building", building);
        bundle.putInt("position", i);
        bundle.putInt("buildingsSize", i2);
        GroupDetailFragment groupDetailFragment = new GroupDetailFragment();
        groupDetailFragment.setArguments(bundle);
        return groupDetailFragment;
    }

    @Override // com.sca.in_telligent.ui.base.BaseFragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.groupDetailSelector = (GroupDetailSelector) context;
    }

    @Override // com.sca.in_telligent.ui.base.BaseFragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.building = (Building) getArguments().getSerializable("building");
        this.position = getArguments().getInt("position");
        this.buildingsSize = getArguments().getInt("buildingsSize");
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_group_detail, viewGroup, false);
        ActivityComponent activityComponent = getActivityComponent();
        if (activityComponent != null) {
            activityComponent.inject(this);
            setUnBinder(ButterKnife.bind(this, inflate));
            this.mPresenter.onAttach(this);
        }
        if (this.position == 0) {
            this.leftArrow.setForeground(ContextCompat.getDrawable(getActivity(), (int) R.color.translate_button_black));
        }
        if (this.position == this.buildingsSize - 1) {
            this.rightArrow.setForeground(ContextCompat.getDrawable(getActivity(), (int) R.color.translate_button_black));
        }
        return inflate;
    }

    public void onDestroyView() {
        this.mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override // com.sca.in_telligent.ui.base.BaseFragment
    protected void setUp(View view) {
        Building building = this.building;
        if (building == null) {
            Log.e(TAG, "setUp: tried to load information of a null building");
            return;
        }
        this.groupName.setText(building.getName());
        this.descriptionShortText.setText(this.building.getDescription());
        this.descriptionLongText.setText(this.building.getDescription());
        if (this.building.getImageUrl() != null && !this.building.getImageUrl().equals("")) {
            Picasso.get().load(this.building.getImageUrl()).into(this.mainImage);
        } else {
            this.mainImage.setImageResource(CommonUtils.getDefaultImage(this.building.getId()));
        }
        if (this.building.getDescription().isEmpty()) {
            this.readMoreButton.setVisibility(8);
        }
        if (isEllipsized()) {
            this.readMoreButton.setVisibility(0);
        } else {
            this.readMoreButton.setVisibility(8);
        }
        if (this.building.isOther()) {
            this.disconnectButton.setText(getString(R.string.connect));
        } else {
            this.disconnectButton.setText(getString(R.string.disconnect));
        }
        this.groupDetailInfo.setText(getResources().getString(R.string.created_on) + " " + CommonUtils.getDateString(this.building.getCreated()));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @OnClick({R.id.group_detail_message_feed})
    public void messageFeedClick(View view) {
        this.groupDetailSelector.messageFeedClick(this.building.getId());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @OnClick({R.id.group_detail_read_more})
    public void readMoreClick(View view) {
        if (!this.textExpanded) {
            this.descriptionShortText.setVisibility(8);
            this.descriptionLongText.setVisibility(0);
            this.readMoreButton.setText(getString(R.string.read_less));
        } else {
            this.descriptionShortText.setVisibility(0);
            this.descriptionLongText.setVisibility(8);
            this.readMoreButton.setText(getString(R.string.read_more));
        }
        this.textExpanded = !this.textExpanded;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @OnClick({R.id.group_detail_right_arrow})
    public void rightClick(View view) {
        this.groupDetailSelector.groupRightClick(this.position);
        getBaseActivity().onFragmentDetached(TAG);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @OnClick({R.id.group_detail_left_arrow})
    public void leftClick(View view) {
        this.groupDetailSelector.groupLeftClicked(this.position);
        getBaseActivity().onFragmentDetached(TAG);
    }

    private boolean isEllipsized() {
        return this.descriptionShortText.getEllipsize().toString().length() > 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @OnClick({R.id.group_detail_disconnect})
    public void onClick(View view) {
        if (this.building.isOther()) {
            if (this.building.getPassword() != null) {
                showPasswordDialog(this.building);
                return;
            }
            UpdateSubscriptionRequest updateSubscriptionRequest = new UpdateSubscriptionRequest();
            updateSubscriptionRequest.setBuildingId(Integer.toString(this.building.getId()));
            updateSubscriptionRequest.setAction(SubscribeToCommunityRequest.ACTION_SUBSCRIBE);
            this.disconnectButton.setText(getString(R.string.disconnect));
            this.mPresenter.connectGroup(updateSubscriptionRequest);
            return;
        }
        AlertUtil.showConfirmationAlert(getContext(), R.string.are_you_sure_you_want_to_unsubscribe_from_this_community, new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.group.detail.other.GroupDetailFragment$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                GroupDetailFragment.this.m195xa583d217(dialogInterface, i);
            }
        });
    }

    /* renamed from: lambda$onClick$0$com-sca-in_telligent-ui-group-detail-other-GroupDetailFragment  reason: not valid java name */
    public /* synthetic */ void m195xa583d217(DialogInterface dialogInterface, int i) {
        performDisconnect();
    }

    private void performDisconnect() {
        UpdateSubscriptionRequest updateSubscriptionRequest = new UpdateSubscriptionRequest();
        updateSubscriptionRequest.setBuildingId(Integer.toString(this.building.getId()));
        updateSubscriptionRequest.setAction(SubscribeToCommunityRequest.ACTION_UNSUBSCRIBE);
        this.disconnectButton.setText(getString(R.string.connect));
        this.mPresenter.disconnectGroup(updateSubscriptionRequest);
    }

    @Override // com.sca.in_telligent.ui.group.detail.other.GroupDetailMvpView
    public void unsubscribed(String str) {
        getActivity().onBackPressed();
        this.groupDetailSelector.unSubscribed(Integer.parseInt(str));
    }

    @Override // com.sca.in_telligent.ui.group.detail.other.GroupDetailMvpView
    public void subscribed(String str) {
        this.groupDetailSelector.subscribed(Integer.parseInt(str));
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    private void showPasswordDialog(final Building building) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.password);
        builder.setMessage(getString(R.string.enter_password));
        final EditText editText = new EditText(getActivity());
        editText.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        builder.setView(editText);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.group.detail.other.GroupDetailFragment$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                GroupDetailFragment.this.m196x2e90ab05(editText, building, dialogInterface, i);
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.group.detail.other.GroupDetailFragment$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    /* renamed from: lambda$showPasswordDialog$1$com-sca-in_telligent-ui-group-detail-other-GroupDetailFragment  reason: not valid java name */
    public /* synthetic */ void m196x2e90ab05(EditText editText, Building building, DialogInterface dialogInterface, int i) {
        if (editText.getText().toString().equals(building.getPassword())) {
            UpdateSubscriptionRequest updateSubscriptionRequest = new UpdateSubscriptionRequest();
            updateSubscriptionRequest.setBuildingId(Integer.toString(building.getId()));
            updateSubscriptionRequest.setAction(SubscribeToCommunityRequest.ACTION_SUBSCRIBE);
            this.disconnectButton.setText(getString(R.string.disconnect));
            this.mPresenter.connectGroup(updateSubscriptionRequest);
            return;
        }
        showMessage(getString(R.string.invalid_password));
    }
}
