package com.sca.in_telligent.ui.group.detail.other;

import static com.sca.in_telligent.util.AlertUtil.showConfirmationAlert;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.openapi.data.network.model.SubscribeToCommunityRequest;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriptionRequest;
import com.sca.in_telligent.ui.base.BaseFragment;
import com.sca.in_telligent.ui.group.alert.list.AlertListFragment;
import com.sca.in_telligent.ui.group.detail.GroupDetailSelector;
import com.sca.in_telligent.ui.inbox.InboxAdapter;
import com.sca.in_telligent.util.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
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
            descriptionLongText = inflate.findViewById(R.id.group_detail_description_long);
            descriptionShortText = inflate.findViewById(R.id.group_detail_description_short);
            disconnectButton = inflate.findViewById(R.id.group_detail_disconnect);
            groupDetailInfo = inflate.findViewById(R.id.group_detail_info);
            groupName = inflate.findViewById(R.id.group_detail_name);
            leftArrow = inflate.findViewById(R.id.group_detail_left_arrow);
            mainImage = inflate.findViewById(R.id.group_detail_main_image);
            messageFeedButton = inflate.findViewById(R.id.group_detail_message_feed);
            readMoreButton = inflate.findViewById(R.id.group_detail_read_more);
            rightArrow = inflate.findViewById(R.id.group_detail_right_arrow);


            setUnBinder(ButterKnife.bind(this, inflate));
            this.mPresenter.onAttach(this);
        }
        return inflate;
    }

    public void onDestroyView() {
        this.mPresenter.onDetach();
        super.onDestroyView();
    }

    @SuppressLint("SetTextI18n")
    @Override
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
            this.readMoreButton.setVisibility(View.GONE);
        }
        if (isEllipsized()) {
            this.readMoreButton.setVisibility(View.VISIBLE);
        } else {
            this.readMoreButton.setVisibility(View.GONE);
        }
        if (this.building.isOther()) {
            this.disconnectButton.setText(getString(R.string.connect));
        } else {
            this.disconnectButton.setText(getString(R.string.disconnect));
        }
        String createdDate = this.building.getCreated();
        CommonUtils.getDateString(createdDate);

        this.groupDetailInfo.setText(getResources().getString(R.string.created_on) + " " + createdDate);

        this.groupDetailInfo.setText(getResources().getString(R.string.created_on) + " " + CommonUtils.getDateString(createdDate));

        rightArrow.setOnClickListener(v -> rightClick());
        leftArrow.setOnClickListener(v -> leftClick());
        readMoreButton.setOnClickListener(v -> readMoreClick());
        messageFeedButton.setOnClickListener(this::loadNotifications);
        disconnectButton.setOnClickListener(this::onClick);
    }

    @OnClick({R.id.group_detail_message_feed})
    public void loadNotifications(View view) {
        groupDetailSelector.loadNotifications(this.building.getId());


    }

    @OnClick({R.id.group_detail_read_more})
    public void readMoreClick() {
        if (!this.textExpanded) {
            this.descriptionShortText.setVisibility(View.GONE);
            this.descriptionLongText.setVisibility(View.VISIBLE);
            this.readMoreButton.setText(getString(R.string.read_less));
        } else {
            this.descriptionShortText.setVisibility(View.VISIBLE);
            this.descriptionLongText.setVisibility(View.GONE);
            this.readMoreButton.setText(getString(R.string.read_more));
        }
        this.textExpanded = !this.textExpanded;
    }

    @OnClick({R.id.group_detail_right_arrow})
    public void rightClick() {
        this.groupDetailSelector.groupRightClick(this.position);
        getBaseActivity().onFragmentDetached(TAG);
    }

    @OnClick({R.id.group_detail_left_arrow})
    public void leftClick() {
        this.groupDetailSelector.groupLeftClicked(this.position);
        getBaseActivity().onFragmentDetached(TAG);
    }

    private boolean isEllipsized() {
        return this.descriptionShortText.getEllipsize().toString().length() > 0;
    }

    @OnClick(R.id.group_detail_disconnect)
    void onClick(View v) {

        if (building.isOther()) {
            if (building.getPassword() != null) {
                showPasswordDialog(building);
            } else {
                UpdateSubscriptionRequest updateSubscriptionRequest = new UpdateSubscriptionRequest();
                updateSubscriptionRequest.setBuildingId(Integer.toString(building.getId()));
                updateSubscriptionRequest.setAction("subscribe");
                disconnectButton.setText(getString(R.string.disconnect));
                mPresenter.connectGroup(updateSubscriptionRequest);
            }
        } else {
            showConfirmationAlert(getContext(),
                    R.string.are_you_sure_you_want_to_unsubscribe_from_this_community,
                    (dialog, which) -> {
                        performDisconnect();
                    });
        }
    }



    private void performDisconnect() {
        UpdateSubscriptionRequest updateSubscriptionRequest = new UpdateSubscriptionRequest();
        updateSubscriptionRequest.setBuildingId(Integer.toString(this.building.getId()));
        updateSubscriptionRequest.setAction(SubscribeToCommunityRequest.ACTION_UNSUBSCRIBE);
        this.disconnectButton.setText(getString(R.string.connect));
        this.mPresenter.disconnectGroup(updateSubscriptionRequest);
    }

    @Override
    public void unsubscribed(String str) {
        getActivity().onBackPressed();
        this.groupDetailSelector.unSubscribed(Integer.parseInt(str));
    }

    @Override
    public void loadNotifications(ArrayList<Notification> notifications) {
        Log.d(TAG, notifications.toString());

    }

    @Override
    public void subscribed(String str) {
        this.groupDetailSelector.subscribed(Integer.parseInt(str));
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    private void showPasswordDialog(Building building) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(R.string.password);
        alertDialog.setMessage(getString(R.string.enter_password));

        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton(R.string.yes,
                (dialog, which) -> {
                    String password1 = input.getText().toString();

                    if (password1.equals(building.getPassword())) {
                        UpdateSubscriptionRequest updateSubscriptionRequest = new UpdateSubscriptionRequest();
                        updateSubscriptionRequest.setBuildingId(Integer.toString(building.getId()));
                        updateSubscriptionRequest.setAction("subscribe");
                        disconnectButton.setText(getString(R.string.disconnect));
                        mPresenter.connectGroup(updateSubscriptionRequest);
                    } else {
                        showMessage(getString(R.string.invalid_password));
                    }
                });
        alertDialog.setNegativeButton(R.string.no,
                (dialog, which) -> dialog.cancel());

        alertDialog.show();

    }

}
