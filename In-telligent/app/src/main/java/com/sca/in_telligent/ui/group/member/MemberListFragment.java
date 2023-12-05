package com.sca.in_telligent.ui.group.member;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.Invitee;
import com.sca.in_telligent.openapi.data.network.model.LocationModel;
import com.sca.in_telligent.ui.base.BaseFragment;
import com.sca.in_telligent.ui.findlocation.FindLocationDialog;
import com.sca.in_telligent.util.AlertUtil;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

public class MemberListFragment extends BaseFragment implements MemberListMvpView,
        MemberListAdapter.Callback {

    public static final String TAG = "MemberListFragment";

    @Inject
    MemberListMvpPresenter<MemberListMvpView> mPresenter;

    @BindView(R.id.member_list_recyclerview)
    RecyclerView memberListView;

    @Inject
    SchedulerProvider schedulerProvider;

    @BindView(R.id.member_list_total_number)
    TextView memberCountField;

    @BindView(R.id.member_list_group_name)
    TextView groupNameField;

    @BindView(R.id.member_list_search)
    EditText searchView;

    @BindView(R.id.member_list_progressbar)
    ProgressBar progressBar;

    @BindView(R.id.swipe_refresh_layout_member_list)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    LinearLayoutManager mLayoutManager;

    private String buildingId;
    private String memberCount;
    private String groupName;

    private ArrayList<Invitee> inviteeList = new ArrayList();
    private MemberListAdapter adapter;

    public interface MemberListSelector {

        void editSelected(Invitee invitee);
    }

    MemberListSelector memberListSelector;

    public static MemberListFragment newInstance(int buildingId, int memberCount, String groupName) {
        Bundle args = new Bundle();
        args.putString("buildingId", Integer.toString(buildingId));
        args.putString("memberCount", Integer.toString(memberCount));
        args.putString("groupName", groupName);
        MemberListFragment fragment = new MemberListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        memberListSelector = (MemberListSelector) context;
        buildingId = getArguments().getString("buildingId");
        memberCount = getArguments().getString("memberCount");
        groupName = getArguments().getString("groupName");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_list, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        loadMembers(true);

        adapter = new MemberListAdapter();
        adapter.setCallback(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        memberListView.setLayoutManager(mLayoutManager);
        memberListView.setAdapter(adapter);

        Observable.create((ObservableOnSubscribe<String>) emitter -> searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emitter.onNext(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        })).map(s -> s.toLowerCase().trim())
                .debounce(250, TimeUnit.MILLISECONDS)
                .doOnError(throwable -> {
                    Log.e(TAG, "onViewCreated: " + throwable.getMessage(), throwable);
                })
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .subscribe(s -> mPresenter.filterMembers(s));


        swipeRefreshLayout.setOnRefreshListener(() -> loadMembers(false));

    }

    public void loadMembers(boolean showLoading) {
        mPresenter.getMembers(buildingId, showLoading);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void memberListFetched(ArrayList<Invitee> invitees) {
        swipeRefreshLayout.setRefreshing(false);
        inviteeList = invitees;
        int size = (invitees == null) ? 0 : invitees.size();
        memberCountField.setText(getString(R.string.total_members) + ": (" + size + ")");
        groupNameField.setText(groupName);
        adapter.addItems(inviteeList);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onDeleteSelected(int inviteId, String memberName, int position) {
        AlertUtil.showConfirmationAlert(getContext(), getString(R.string.remove_invite),
                getString(R.string.are_you_sure_you_want_to_remove_from_community, memberName,
                        groupName), (dialogInterface, i) -> {
                    mPresenter.removeMember(inviteId, position);
                });
    }

    @Override
    public void onMemberInvitationRemoved(int position) {
        adapter.removeItem(position);
        memberCountField.setText(getString(R.string.total_members) + ": (" + adapter.getItemCount() + ")");
    }

    @Override
    public void onEditSelected(int position) {
        memberListSelector.editSelected(inviteeList.get(position));
    }


    @Override
    public void lastLocationFetched(LocationModel locationModel, String subscriberId, String name) {
        FindLocationDialog findLocationDialog = FindLocationDialog
                .newInstance(locationModel, subscriberId, name);
        findLocationDialog.show(getFragmentManager());
    }

    @Override
    public void onMemberLocation(int position, String name) {
        mPresenter.getLastLocation(Integer.toString(inviteeList.get(position).getSubscriberId()), name);
    }
}
