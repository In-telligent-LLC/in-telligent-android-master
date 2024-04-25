package com.sca.in_telligent.ui.contact.message.deliver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.BuildingMember;
import com.sca.in_telligent.openapi.data.network.model.Invitee;
import com.sca.in_telligent.ui.base.BaseDialog;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;


public class ContactDeliverDialog extends BaseDialog implements ContactDeliverMvpView,
        ContactDeliverListAdapter.Callback {

    private static final String TAG = "ContactDeliverDialog";

    @Inject
    ContactDeliverMvpPresenter<ContactDeliverMvpView> mPresenter;

    @Inject
    ContactDeliverListAdapter contactDeliverListAdapter;

    @Inject
    SchedulerProvider schedulerProvider;

    @Inject
    LinearLayoutManager mLayoutManager;

    private Unbinder mUnBinder;


    @BindView(R.id.contact_deliver_search)
    EditText searchView;

    @BindView(R.id.contact_deliver_recyclerview)
    RecyclerView contactList;

    @BindView(R.id.contact_deliver_ok_button)
    TextView okButton;

    @BindView(R.id.contact_deliver_progressbar)
    ProgressBar progressBar;

    @BindView(R.id.contact_delivery_no_member_text)
    TextView emptyText;

    private String buildingId;
    private boolean isPersonalCommunity;

    private ContactDeliverSelector contactDeliverSelector;

    private ArrayList<Invitee> invitees = new ArrayList<>();
    private ArrayList<BuildingMember> buildingMembers = new ArrayList<>();

    public interface ContactDeliverSelector {

        void sendDeliverList(ArrayList<Invitee> ids);

        void sendDeliverListMember(ArrayList<BuildingMember> ids);
    }

    public void setContactDeliverSelector(
            ContactDeliverSelector contactDeliverSelector) {
        this.contactDeliverSelector = contactDeliverSelector;
    }

    public static ContactDeliverDialog newInstance(String buildingId, boolean isPersonalCommunity) {
        ContactDeliverDialog fragment = new ContactDeliverDialog();
        Bundle bundle = new Bundle();
        bundle.putString("buildingId", buildingId);
        bundle.putBoolean("isPersonalCommunity", isPersonalCommunity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        buildingId = getArguments().getString("buildingId");
        isPersonalCommunity = getArguments().getBoolean("isPersonalCommunity");
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contact_deliver_dialog, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            searchView = view.findViewById(R.id.contact_deliver_search);
            contactList = view.findViewById(R.id.contact_deliver_recyclerview);
            okButton = view.findViewById(R.id.contact_deliver_ok_button);
            progressBar = view.findViewById(R.id.contact_deliver_progressbar);
            emptyText = view.findViewById(R.id.contact_delivery_no_member_text);

            okButton.setOnClickListener(this::okClicked);

            component.inject(this);

            setUnBinder(ButterKnife.bind(this, view));

            mPresenter.onAttach(this);
        }
        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        contactDeliverListAdapter.setCallback(this);
        contactList.setLayoutManager(mLayoutManager);
        contactList.setAdapter(contactDeliverListAdapter);

        contactDeliverListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                if (contactDeliverListAdapter.isEmpty()) {
                    emptyText.setVisibility(View.VISIBLE);
                } else {
                    emptyText.setVisibility(View.GONE);
                }
            }
        });

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
                .subscribe(s -> mPresenter.filterNames(s));

    }

    @Override
    protected void setUp(View view) {
        if (isPersonalCommunity) {
            mPresenter.getInvitees(buildingId);
        } else {
            mPresenter.getSubscribers(buildingId);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.contact_deliver_ok_button)
    void okClicked(View v) {
        if (isPersonalCommunity) {
            contactDeliverSelector.sendDeliverList(invitees);
        } else {
            contactDeliverSelector.sendDeliverListMember(buildingMembers);
        }
        mPresenter.onDetach();
        dismissDialog(TAG);
    }

    @Override
    public void onCheckChanged(ArrayList<Invitee> ids) {
        invitees = ids;
    }

    @Override
    public void onCheckChangedMember(ArrayList<BuildingMember> members) {
        buildingMembers = members;
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void inviteeFetched(ArrayList<Invitee> invitees) {
        contactDeliverListAdapter.addItems(invitees);
    }

    @Override
    public void memberFetched(ArrayList<BuildingMember> members) {
        contactDeliverListAdapter.addMemberItems(members);
    }


    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }
}
