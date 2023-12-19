package com.sca.in_telligent.ui.group.detail.created;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.ui.base.BaseFragment;
import com.sca.in_telligent.ui.group.detail.GroupDetailSelector;
import com.sca.in_telligent.ui.group.list.GroupListFragment;
import com.sca.in_telligent.util.CommonUtils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class CreatedGroupDetailFragment extends BaseFragment implements CreatedGroupDetailMvpView {

    public static final String TAG = "CreatedGroupDetailFragment";

    GroupDetailSelector groupDetailSelector;

    private int position;

    @Inject
    CreatedGroupDetailMvpPresenter<CreatedGroupDetailMvpView> mPresenter;

    Building building;

    @BindView(R.id.created_group_detail_main_image)
    ImageView mainImage;

    @BindView(R.id.created_group_detail_name)
    TextView groupName;

    @BindView(R.id.created_group_detail_info)
    TextView groupDetail;

    @BindView(R.id.created_group_detail_description)
    TextView groupDescription;

    @BindView(R.id.created_group_detail_edit_pen_image)
    ImageView editGroupImage;

    @BindView(R.id.created_group_detail_trash_image)
    TextView deleteButton;

    public static CreatedGroupDetailFragment newInstance(Building building, int position) {
        Bundle args = new Bundle();
        args.putSerializable("building", building);
        args.putInt("position", position);
        CreatedGroupDetailFragment fragment = new CreatedGroupDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        groupDetailSelector = (GroupDetailSelector) context;
        building = (Building) getArguments().getSerializable("building");
        position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_created_group_detail, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;
    }

    @OnClick(R.id.created_group_detail_member)
    void memberViewClick(View v) {
        groupDetailSelector
                .viewMemberSelected(building.getId(), building.getSubscriberCount(), building.getName());
    }

    @OnClick(R.id.created_group_detail_invite)
    void inviteClick(View v) {
        groupDetailSelector.inviteOtherSelected(building.getId());
    }

    @OnClick(R.id.created_group_detail_alert)
    void alertViewClick(View v) {
        groupDetailSelector.alertViewSelected(building.getId());
    }

    @Override
    protected void setUp(View view) {
        if (building != null) {
            groupName.setText(building.getName());
            if (building.getCreated() != null) {
                groupDetail.setText(getResources().getString(R.string.created_on) + " " + CommonUtils
                        .getDateString(building.getCreated().toString()));
            } else {
                groupDetail.setVisibility(View.GONE);
            }
            groupDescription.setText(building.getWebsite());
            if (building.getImageUrl() != null && !building.getImageUrl().equals("")) {
                Picasso.get().load(building.getImageUrl()).into(mainImage);
            } else {
                mainImage.setImageResource(CommonUtils.getDefaultImage(building.getId()));
            }
        }
    }

    @OnClick(R.id.created_group_detail_right_arrow)
    void rightClick(View v) {
        groupDetailSelector.groupRightClick(position);
        getBaseActivity().onFragmentDetached(TAG);
    }

    @OnClick(R.id.created_group_detail_left_arrow)
    void leftClick(View v) {
        groupDetailSelector.groupLeftClicked(position);
        getBaseActivity().onFragmentDetached(TAG);
    }

    @OnClick(R.id.created_group_detail_edit_pen_image)
    void editGroupClick(View v) {
        groupDetailSelector.editGroupSelected(position);
    }

    @OnClick(R.id.created_group_detail_trash_image)
    void deleteClick(View v) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(getString(R.string.delete));
        alertDialog.setMessage(
                getString(R.string.are_you_sure_you_want_to_delete) + " " + building.getName());
        alertDialog
                .setPositiveButton(getString(android.R.string.ok), (dialogInterface, i) -> {
                    mPresenter.deletePersonalCommunity(Integer.toString(building.getId()));
                }).setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> {

        });

        alertDialog.show();
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void groupDeleted() {
        showMessage(getString(R.string.group_deleted));
        GroupListFragment groupListFragment = (GroupListFragment) getActivity()
                .getSupportFragmentManager()
                .findFragmentByTag(GroupListFragment.TAG);
        groupListFragment.groupDeletion(building, position);
        getBaseActivity().onFragmentDetached(TAG);
    }
}
