package com.sca.in_telligent.ui.contact.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class ContactListFragment extends BaseFragment implements ContactListMvpView,
        ContactListGroupAdapter.Callback {

    public static final String TAG = "ContactListFragment";

    public interface ContactListCallback {

        void callClick(Building building);

        void messageClick(Building building);
    }

    private ContactListCallback contactListCallback;

    @Inject
    ContactListMvpPresenter<ContactListMvpView> mPresenter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ContactListGroupAdapter contactListAdapter;

    @BindView(R.id.contact_list_recyclerview)
    RecyclerView contactListRecyclerview;

    ArrayList<Building> contactBuildings = new ArrayList<>();

    public static ContactListFragment newInstance(ArrayList<Building> buildings) {
        Bundle args = new Bundle();
        args.putSerializable("buildings", buildings);
        ContactListFragment fragment = new ContactListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        contactListCallback = (ContactListCallback) context;
        contactBuildings = (ArrayList<Building>) getArguments().getSerializable("buildings");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
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

        if (contactBuildings != null) {
            contactListAdapter.setCallback(this);
            contactListAdapter.addItems(contactBuildings);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            contactListRecyclerview.setLayoutManager(mLayoutManager);
            contactListRecyclerview.setAdapter(contactListAdapter);
        }
    }

    @OnTextChanged(R.id.contact_list_edittext_search)
    protected void onTextChanged(CharSequence text) {
        filter(text.toString());
    }

    private void filter(String filterString) {
        List<Building> filteredList = new ArrayList();
        for (Building b : contactBuildings) {

            if (b.getName().toLowerCase().contains(filterString.toLowerCase())) {
                filteredList.add(b);
            }
        }
        contactListAdapter.updateList(filteredList);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onMessageselected(Building building) {
        contactListCallback.messageClick(building);

    }

    @Override
    public void onCallSelected(Building building) {
        contactListCallback.callClick(building);
    }
}
