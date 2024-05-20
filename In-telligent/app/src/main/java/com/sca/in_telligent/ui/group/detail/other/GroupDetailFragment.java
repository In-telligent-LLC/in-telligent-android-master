package com.sca.in_telligent.ui.group.detail.other;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriptionRequest;
import com.sca.in_telligent.ui.base.BaseFragment;
import com.sca.in_telligent.ui.group.detail.GroupDetailSelector;
import com.sca.in_telligent.util.CommonUtils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sca.in_telligent.util.AlertUtil.showConfirmationAlert;

public class GroupDetailFragment extends BaseFragment implements GroupDetailMvpView {

    public static final String TAG = "GroupDetailFragment";
    private int position;

    Building building;

    @Inject
    GroupDetailMvpPresenter<GroupDetailMvpView> mPresenter;

    @BindView(R.id.group_detail_description_long)
    TextView descriptionLongText;

    @BindView(R.id.group_detail_read_more)
    TextView readMoreButton;

    @BindView(R.id.group_detail_description_short)
    TextView descriptionShortText;

    @BindView(R.id.group_detail_main_image)
    ImageView mainImage;

    @BindView(R.id.group_detail_info)
    TextView groupDetailInfo;

    @BindView(R.id.group_detail_name)
    TextView groupName;

    @BindView(R.id.group_detail_right_arrow)
    ImageView rightArrow;

    @BindView(R.id.group_detail_left_arrow)
    ImageView leftArrow;

    @BindView(R.id.group_detail_message_feed)
    TextView messageFeedButton;

    @BindView(R.id.group_detail_disconnect)
    TextView disconnectButton;

    private boolean textExpanded = false;

    GroupDetailSelector groupDetailSelector;

    public static GroupDetailFragment newInstance(Building building, int position) {
        Bundle args = new Bundle();
        args.putSerializable("building", building);
        args.putInt("position", position);
        GroupDetailFragment fragment = new GroupDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    groupDetailSelector = (GroupDetailSelector) context;

  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    building = (Building) getArguments().getSerializable("building");
    position = getArguments().getInt("position");
  }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_detail, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    protected void setUp(View view) {

        if (building == null) {
            Log.e(TAG, "setUp: tried to load information of a null building");
            return;
        }

        groupName.setText(building.getName());
        descriptionShortText.setText(building.getDescription());
        descriptionLongText.setText(building.getDescription());

        if (building.getImageUrl() != null && !building.getImageUrl().equals("")) {
            Picasso.get().load(building.getImageUrl()).into(mainImage);
        } else {
            mainImage.setImageResource(CommonUtils.getDefaultImage(building.getId()));
        }

        if (building.getDescription().isEmpty()) {
            readMoreButton.setVisibility(View.GONE);
        }

        if (isEllipsized()) {
            readMoreButton.setVisibility(View.VISIBLE);
        } else {
            readMoreButton.setVisibility(View.GONE);
        }

        if (building.isOther()) {
            disconnectButton.setText(getString(R.string.connect));
        } else {
            disconnectButton.setText(getString(R.string.disconnect));
        }

        groupDetailInfo.setText(getResources().getString(R.string.created_on) + " " + CommonUtils
                .getDateString(building.getCreated()));
    }

    @OnClick(R.id.group_detail_message_feed)
    void messageFeedClick(View v) {
        groupDetailSelector.messageFeedClick(building.getId());
    }

    @OnClick(R.id.group_detail_read_more)
    void readMoreClick(View v) {
        if (!textExpanded) {
            descriptionShortText.setVisibility(View.GONE);
            descriptionLongText.setVisibility(View.VISIBLE);
            readMoreButton.setText(getString(R.string.read_less));
        } else {
            descriptionShortText.setVisibility(View.VISIBLE);
            descriptionLongText.setVisibility(View.GONE);
            readMoreButton.setText(getString(R.string.read_more));
        }
        textExpanded = !textExpanded;
    }

    @OnClick(R.id.group_detail_right_arrow)
    void rightClick(View v) {
        groupDetailSelector.groupRightClick(position);
        getBaseActivity().onFragmentDetached(TAG);
    }

    @OnClick(R.id.group_detail_left_arrow)
    void leftClick(View v) {
        groupDetailSelector.groupLeftClicked(position);
        getBaseActivity().onFragmentDetached(TAG);
    }

    private boolean isEllipsized() {
        boolean isEllipsized = false;
        if (descriptionShortText.getEllipsize().toString().length() > 0) {
            isEllipsized = true;
        }
        return isEllipsized;
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
        updateSubscriptionRequest.setBuildingId(Integer.toString(building.getId()));
        updateSubscriptionRequest.setAction("unsubscribe");
        disconnectButton.setText(getString(R.string.connect));
        mPresenter.disconnectGroup(updateSubscriptionRequest);
    }

    @Override
    public void unsubscribed(String buildingId) {
        getActivity().onBackPressed();
        groupDetailSelector.unSubscribed(Integer.parseInt(buildingId));
    }

    @Override
    public void subscribed(String buildingId) {
        groupDetailSelector.subscribed(Integer.parseInt(buildingId));
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
