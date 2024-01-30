package com.sca.in_telligent.ui.group.list;

import static com.sca.in_telligent.util.AlertUtil.showConfirmationAlert;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriptionRequest;
import com.sca.in_telligent.ui.base.BaseFragment;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import io.reactivex.rxjava3.core.Observable;

public class GroupListFragment extends BaseFragment implements GroupListMvpView, GroupListAdapter.Callback {
    public static final String ARG_GROUPS = "groups";
    public static final String ARG_SUBSCRIBER_ID = "subscriber_id";
    public static final String ARG_SUGGESTED = "suggested";
    public static final String TAG = "GroupListFragment";
    @Inject
    GroupListAdapter adapter;
    @BindView(R.id.group_recyclerview)
    RecyclerView groupList;
    GroupListSelector groupListSelector;
    @BindView(R.id.group_list_spinner)
    Spinner groupListSpinner;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    GroupListMvpPresenter<GroupListMvpView> mPresenter;
    @Inject
    SchedulerProvider schedulerProvider;
    @BindView(R.id.group_list_edittext_search)
    EditText searchEdittext;
    @Inject
    GroupListSpinnerAdapter spinnerAdapter;
    private int subscriberId;
    @BindView(R.id.swipe_refresh_layout_groups)
    SwipeRefreshLayout swipeRefreshLayout;

//    @BindView(R.id.fab_create_group)
//    FloatingActionButton fabCreateGroup;


    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Building> suggestedBuildings = new ArrayList<>();
    private ArrayList<Building> otherBuildings = new ArrayList<>();
    private ArrayList<Building> filteredBuildings = new ArrayList<>();
    private ArrayList<Building> buildingsUpdated = new ArrayList<>();
    private ArrayList<Building> combinedBuildings = new ArrayList<>();

    public interface GroupListSelector {

        void itemAboutClicked(int position, boolean createdByMe);

        void groupsUpdated(ArrayList<Building> updatedGroups);

        void onCreateGroupClicked();

        void onPullToRefreshGroups();
    }


    @Override
    public void onContactClicked(int i) {
    }

    @Override
    public void onOptOutFromCommunitySuccess() {
    }

    public static GroupListFragment newInstance(ArrayList<Building> groups,
                                                ArrayList<Building> suggested, int subscriberId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_GROUPS, groups);
        args.putSerializable(ARG_SUGGESTED, suggested);
        args.putInt(ARG_SUBSCRIBER_ID, subscriberId);
        GroupListFragment fragment = new GroupListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.groupListSelector = (GroupListSelector) context;
        buildings = (ArrayList<Building>) getArguments().getSerializable(ARG_GROUPS);
        suggestedBuildings = (ArrayList<Building>) getArguments().getSerializable(ARG_SUGGESTED);
        subscriberId = getArguments().getInt(ARG_SUBSCRIBER_ID);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_group_list, viewGroup, false);
        ActivityComponent activityComponent = getActivityComponent();


        if (activityComponent != null) {
            activityComponent.inject(this);

            setUnBinder(ButterKnife.bind(this, inflate));
            this.mPresenter.onAttach(this);

            swipeRefreshLayout = inflate.findViewById(R.id.swipe_refresh_layout_groups);
            searchEdittext = inflate.findViewById(R.id.group_list_edittext_search);
            spinnerAdapter = new GroupListSpinnerAdapter(getContext(), new ArrayList<>());
            groupListSpinner = inflate.findViewById(R.id.group_list_spinner);
            groupList = inflate.findViewById(R.id.group_recyclerview);
//            fabCreateGroup = inflate.findViewById(R.id.fab_create_group);


        }
        return inflate;
    }

    @Override
    protected void setUp(View view) {

        adapter.setCallback(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        groupList.setLayoutManager(mLayoutManager);
        groupList.setAdapter(adapter);

        addItems(this.buildings, this.suggestedBuildings);

        initSpinner();
        setTextWatcher();
        setUpPullToRefresh();

//        fabCreateGroup.setOnClickListener(v -> groupListSelector.onCreateGroupClicked());
        onContactClicked(subscriberId);

        groupListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spinnerItemSelected(groupListSpinner, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });


    }

    private void setUpPullToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (groupListSelector != null) {
                groupListSelector.onPullToRefreshGroups();
            }
        });
    }

    public void addItems(ArrayList<Building> buildings, ArrayList<Building> suggestedBuildings) {
        swipeRefreshLayout.setRefreshing(false);
        adapter.addItems(buildings, suggestedBuildings);
    }


    @OnItemSelected(R.id.group_list_spinner)
    public void spinnerItemSelected(Spinner spinner, int position) {
        GroupSpinnerItemType spinnerItemType = GroupSpinnerItemType.NONE;
        switch (spinnerItemType.getSpinnerMode(position)) {
            case NONE:
                adapter.updateItems(buildings);
                groupListSelector.groupsUpdated(buildings);
                break;
            case PEOPLE:
                updateSpinnerList(Building.Category.PEOPLE);
                break;
            case ORGANIZATIONS:
                updateSpinnerList(Building.Category.ORGANIZATION);
                break;
            case HELPLINES:
                updateSpinnerList(Building.Category.HELPLINE);
                break;
            case EMERGENCY:
                updateSpinnerList(Building.Category.EMERGENCY);
                break;
        }
    }


    @SuppressLint("CheckResult")
    private void updateSpinnerList(Building.Category category) {
        Observable.fromIterable(buildings)
                .filter(
                        building -> belongsToCategory(category, building))
                .toList().subscribe(updatedBuildings -> {
                    buildingsUpdated = (ArrayList<Building>) updatedBuildings;
                    adapter.updateItems(buildingsUpdated);
                    groupListSelector.groupsUpdated(buildingsUpdated);
                });
    }

    private boolean belongsToCategory(Building.Category category, Building building) {

        if (category == null) {
            Log.e(TAG, "belongsToCategory: category == nul");
            return false;
        }

        if (category.getCategory().equalsIgnoreCase(Building.Category.PEOPLE.getCategory()) &&
                (building.getSubscriberId() != null && building.getSubscriberId().intValue() == subscriberId)) {
            return true;
        }

        if (category.getCategory().equalsIgnoreCase(building.getFilterCategory())) {
            return true;
        }

        return false;
    }

    private void initSpinner() {
        ArrayList<String> spinnerItems = new ArrayList<>();
        spinnerItems.add(getString(R.string.none));
        spinnerItems.add(getString(R.string.people));
        spinnerItems.add(getString(R.string.organizations));
        spinnerItems.add(getString(R.string.helplines));
        spinnerItems.add(getString(R.string.emergency_service));
        spinnerAdapter.addItems(spinnerItems);
        groupListSpinner.setAdapter(spinnerAdapter);
    }

    public void onDestroyView() {
        this.mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onAboutClicked(int i, boolean z) {
        this.groupListSelector.itemAboutClicked(i, z);
    }


    @Override
    public void onConnectClicked(String buildingId, boolean connect, boolean suggested) {
        UpdateSubscriptionRequest updateSubscriptionRequest = new UpdateSubscriptionRequest();
        updateSubscriptionRequest.setBuildingId(buildingId);
        if (connect) {
            updateSubscriptionRequest.setAction("subscribe");
            mPresenter.subscribe(updateSubscriptionRequest, suggested);
        } else {
            showConfirmationAlert(getContext(),
                    R.string.are_you_sure_you_want_to_unsubscribe_from_this_community,
                    (dialog, which) -> {
                        updateSubscriptionRequest.setAction("unsubscribe");
                        mPresenter.unsubscribe(updateSubscriptionRequest);
                    });
        }
    }

    @Override
    public void onIgnoreClicked(String buildingId, int ignoredPosition) {
        mPresenter.onIgnoreCommunityClicked(buildingId, ignoredPosition);
    }

    private void setTextWatcher() {
        searchEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @SuppressLint("CheckResult")
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() >= 3) {
                    Observable.fromIterable(buildings)
                            .filter(building -> building.getName().toLowerCase()
                                    .contains(editable.toString().toLowerCase()))
                            .toList()
                            .subscribe(filteredList -> {
                                filteredBuildings = (ArrayList<Building>) filteredList;
                                groupListSelector.groupsUpdated(filteredBuildings);
                            });
                    mPresenter.searchCommunities(editable.toString());
                } else {
                        buildings.removeAll(otherBuildings);
                        combinedBuildings = new ArrayList<>();
                        adapter.updateItems(buildings);
                        groupListSelector.groupsUpdated(buildings);
                }
            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateGroupList(ArrayList<Building> otherGroups) {

        otherBuildings = otherGroups;

        setBuildingType(otherBuildings, Building.Type.NORMAL);

        Observable.fromIterable(otherBuildings).doOnNext(building -> building.setOther(true)).toList()
                .subscribe(otherBuildingsSet -> otherBuildings = (ArrayList<Building>) otherBuildingsSet);

        Observable.fromIterable(otherBuildings).filter(
                        otherBuilding -> !buildings.stream().anyMatch(building -> building.equals(otherBuilding)))
                .toList().subscribe(
                        otherBuildingsFiltered -> otherBuildings = (ArrayList<Building>) otherBuildingsFiltered);

        if (otherBuildings.size() > 0 && !otherBuildings.stream()
                .anyMatch(building -> building.getType() == Building.Type.GRAY_HEADER)) {
            Building building = new Building();
            building.setName("Other Groups");
            building.setType(Building.Type.GRAY_HEADER);
            otherBuildings.add(0, building);
        }
        combinedBuildings = new ArrayList<>();
        combinedBuildings.addAll(filteredBuildings);
        combinedBuildings.addAll(otherBuildings);
        adapter.updateItems(combinedBuildings);
        groupListSelector.groupsUpdated(combinedBuildings);
    }



    @SuppressLint("CheckResult")
    @Override
    public void subscribed(String buildingId, boolean suggested) {
        if (suggested) {
            Observable.fromIterable(suggestedBuildings)
                    .filter(building -> building.getId() == Integer.parseInt(buildingId))
                    .doOnNext(building -> building.setType(Building.Type.NORMAL)).toList()
                    .subscribe(filteredBuildings -> buildings.addAll(filteredBuildings));

            buildings.sort(Comparator.comparing(Building::getName));

            Observable.fromIterable(suggestedBuildings)
                    .filter(building -> building.getId() != Integer.parseInt(buildingId)).toList()
                    .subscribe(filteredList -> suggestedBuildings = (ArrayList<Building>) filteredList);

            adapter.updateItems(buildings);

            groupListSelector.groupsUpdated(buildings);

            mPresenter.getSuggestedGroups();
        } else {
            if (combinedBuildings.size() > 0) {
                Building connectedBuilding = otherBuildings.stream()
                        .filter(building -> building.getId() == Integer.parseInt(buildingId)).findFirst().get();
                otherBuildings.remove(connectedBuilding);
                connectedBuilding.setOther(false);

                filteredBuildings.add(connectedBuilding);
                buildings.add(connectedBuilding);

                filteredBuildings.sort(Comparator.comparing(Building::getName));
                buildings.sort(Comparator.comparing(Building::getName));

                combinedBuildings = new ArrayList<>();
                combinedBuildings.addAll(filteredBuildings);
                combinedBuildings.addAll(otherBuildings);

                adapter.updateItems(combinedBuildings);
                groupListSelector.groupsUpdated(combinedBuildings);
            }
        }
    }


    @SuppressLint("CheckResult")
    @Override
    public void unsubscribed(String buildingId) {

        Building disconnectedBuilding = null;

        if (combinedBuildings.size() > 0) {

            disconnectedBuilding = filteredBuildings.stream()
                    .filter(building -> building.getId() == Integer.parseInt(buildingId)).
                    findFirst().get();

            filteredBuildings.remove(disconnectedBuilding);
            buildings.remove(disconnectedBuilding);

            combinedBuildings = new ArrayList<>();
            combinedBuildings.addAll(filteredBuildings);
            combinedBuildings.addAll(otherBuildings);

            adapter.updateItems(combinedBuildings);
            groupListSelector.groupsUpdated(combinedBuildings);

        } else {
            disconnectedBuilding = buildings.stream()
                    .filter(building -> building.getId() == Integer.parseInt(buildingId)).
                    findFirst().get();

            Observable.fromIterable(buildings)
                    .filter(building -> building.getId() != Integer.parseInt(buildingId)).toList()
                    .subscribe(filteredList -> buildings = (ArrayList<Building>) filteredList);
            groupListSelector.groupsUpdated(buildings);
            adapter.updateItems(buildings);
        }

        if (disconnectedBuilding != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("You were automatically subscribed to this community");
            builder.setMessage(String.format("Would you like to opt-out from automatic subscriptions to %s", disconnectedBuilding.getName()));
            Building finalDisconnectedBuilding = disconnectedBuilding;
            builder.setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {
                mPresenter.optOutFromCommunity(finalDisconnectedBuilding.getId(), true);
            });
            builder.setNegativeButton(android.R.string.no, (dialogInterface, i) -> {

            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
    }


    @Override
    public void updateSuggested(ArrayList<Building> arrayList) {
        this.suggestedBuildings.clear();
        this.suggestedBuildings.addAll(arrayList);
        this.adapter.updateSuggestedItems(this.suggestedBuildings);
    }

    @Override
    public void updateNextSuggested(Building building, int i) {
        this.suggestedBuildings.set(i, building);
        this.adapter.updateSuggestedItems(this.suggestedBuildings);
    }

    ArrayList<Building> setBuildingType(ArrayList<Building> arrayList, Building.Type type) {
        Iterator<Building> it = arrayList.iterator();
        while (it.hasNext()) {
            it.next().setType(type);
        }
        return arrayList;
    }

    public void groupDeletion(Building building, int i) {
        if (this.buildings.size() > 0 && this.buildings.contains(building)) {
            this.buildings.remove(building);
            this.adapter.updateItems(this.buildings);
        }
        if (this.filteredBuildings.size() > 0) {
            this.filteredBuildings.remove(building);
        }
        if (this.buildingsUpdated.size() > 0) {
            this.buildingsUpdated.remove(building);
        }
        if (this.adapter.getBuildings().size() > 0) {
            this.adapter.getBuildings().remove(building);
        }
        this.adapter.notifyItemRemoved(i);
    }
}
