package com.sca.in_telligent.ui.group.list;

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
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.Building.Category;
import com.sca.in_telligent.openapi.data.network.model.Building.Type;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriptionRequest;
import com.sca.in_telligent.ui.base.BaseFragment;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Comparator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;

import static com.sca.in_telligent.util.AlertUtil.showConfirmationAlert;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class GroupListFragment extends BaseFragment implements GroupListMvpView,
        GroupListAdapter.Callback {

    public static final String TAG = "GroupListFragment";
    public static final String ARG_GROUPS = "groups";
    public static final String ARG_SUGGESTED = "suggested";
    public static final String ARG_SUBSCRIBER_ID = "subscriber_id";


    public interface GroupListSelector {

        void itemAboutClicked(int position, boolean createdByMe);

        void groupsUpdated(ArrayList<Building> updatedGroups);

        void onCreateGroupClicked();

        void onPullToRefreshGroups();
    }

    @Inject
    GroupListMvpPresenter<GroupListMvpView> mPresenter;

    @Inject
    GroupListAdapter adapter;

    @Inject
    GroupListSpinnerAdapter spinnerAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    SchedulerProvider schedulerProvider;

    @BindView(R.id.group_recyclerview)
    RecyclerView groupList;

    @BindView(R.id.swipe_refresh_layout_groups)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.group_list_spinner)
    Spinner groupListSpinner;

    @BindView(R.id.group_list_edittext_search)
    EditText searchEdittext;

    @BindView(R.id.fab_create_group)
    FloatingActionButton fabCreateGroup;

    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Building> suggestedBuildings = new ArrayList<>();
    private ArrayList<Building> otherBuildings = new ArrayList<>();
    private ArrayList<Building> filteredBuildings = new ArrayList<>();
    private ArrayList<Building> buildingsUpdated = new ArrayList<>();
    private ArrayList<Building> combinedBuildings = new ArrayList<>();
    private int subscriberId;

    GroupListSelector groupListSelector;


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
        groupListSelector = (GroupListSelector) context;
        buildings = (ArrayList<Building>) getArguments().getSerializable(ARG_GROUPS);
        suggestedBuildings = (ArrayList<Building>) getArguments().getSerializable(ARG_SUGGESTED);
        subscriberId = getArguments().getInt(ARG_SUBSCRIBER_ID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_list, container, false);
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

        adapter.setCallback(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        groupList.setLayoutManager(mLayoutManager);
        groupList.setAdapter(adapter);

        addItems(buildings, suggestedBuildings);

        initSpinner();
        setTextWatcher();
        setUpPullToRefresh();

        fabCreateGroup.setOnClickListener(v -> {
            groupListSelector.onCreateGroupClicked();
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
                updateSpinnerList(Category.PEOPLE);
                break;
            case ORGANIZATIONS:
                updateSpinnerList(Category.ORGANIZATION);
                break;
            case HELPLINES:
                updateSpinnerList(Category.HELPLINE);
                break;
            case EMERGENCY:
                updateSpinnerList(Category.EMERGENCY);
                break;
        }
    }

    private void updateSpinnerList(Category category) {
        Observable.fromIterable(buildings)
                .filter(
                        building -> belongsToCategory(category, building))
                .toList().subscribe(updatedBuildings -> {
            buildingsUpdated = (ArrayList<Building>) updatedBuildings;
            adapter.updateItems(buildingsUpdated);
            groupListSelector.groupsUpdated(buildingsUpdated);
        });
    }

    private boolean belongsToCategory(Category category, Building building) {

        if (category == null) {
            Log.e(TAG, "belongsToCategory: category == nul");
            return false;
        }

        if (category.getCategory().equalsIgnoreCase(Category.PEOPLE.getCategory()) &&
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

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onAboutClicked(int position, boolean createdByMe) {
        groupListSelector.itemAboutClicked(position, createdByMe);
    }

    @Override
    public void onContactClicked(int position) {

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

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() >= 3) {
                    Observable.fromIterable(buildings)
                            .filter(building -> building.getName().toLowerCase()
                                    .contains(editable.toString().toLowerCase())).toList()
                            .subscribe(filteredList -> {
                                filteredBuildings = (ArrayList<Building>) filteredList;
                                groupListSelector.groupsUpdated(filteredBuildings);
                            });

                    mPresenter.searchCommunities(editable.toString());
                } else {
//          buildings.removeAll(otherBuildings);
                    combinedBuildings = new ArrayList<>();
                    adapter.updateItems(buildings);
                    groupListSelector.groupsUpdated(buildings);
                }
            }
        });
    }

    @Override
    public void updateGroupList(ArrayList<Building> otherGroups) {

        otherBuildings = otherGroups;

        setBuildingType(otherBuildings, Type.NORMAL);

        Observable.fromIterable(otherBuildings).doOnNext(building -> building.setOther(true)).toList()
                .subscribe(otherBuildingsSet -> otherBuildings = (ArrayList<Building>) otherBuildingsSet);

        Observable.fromIterable(otherBuildings).filter(
                otherBuilding -> !buildings.stream().anyMatch(building -> building.equals(otherBuilding)))
                .toList().subscribe(
                otherBuildingsFiltered -> otherBuildings = (ArrayList<Building>) otherBuildingsFiltered);

        if (otherBuildings.size() > 0 && !otherBuildings.stream()
                .anyMatch(building -> building.getType() == Type.GRAY_HEADER)) {
            Building building = new Building();
            building.setName("Other Groups");
            building.setType(Type.GRAY_HEADER);
            otherBuildings.add(0, building);
        }
        combinedBuildings = new ArrayList<>();
        combinedBuildings.addAll(filteredBuildings);
        combinedBuildings.addAll(otherBuildings);
        adapter.updateItems(combinedBuildings);
        groupListSelector.groupsUpdated(combinedBuildings);
    }

    @Override
    public void subscribed(String buildingId, boolean suggested) {
        if (suggested) {
            Observable.fromIterable(suggestedBuildings)
                    .filter(building -> building.getId() == Integer.parseInt(buildingId))
                    .doOnNext(building -> building.setType(Type.NORMAL)).toList()
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
    public void onOptOutFromCommunitySuccess() {

    }

    @Override
    public void updateSuggested(ArrayList<Building> suggestedGroups) {
        suggestedBuildings.clear();
        suggestedBuildings.addAll(suggestedGroups);
        adapter.updateSuggestedItems(suggestedBuildings);
    }

    @Override
    public void updateNextSuggested(Building building, int ignoredPosition) {
        suggestedBuildings.set(ignoredPosition, building);
        adapter.updateSuggestedItems(suggestedBuildings);

    }

    ArrayList<Building> setBuildingType(ArrayList<Building> buildings, Type type) {
        for (Building building : buildings) {
            building.setType(type);
        }
        return buildings;
    }

    public void groupDeletion(Building building, int position) {
        if (buildings.size() > 0 && buildings.contains(building)) {
            buildings.remove(building);
            adapter.updateItems(buildings);
        }

        if (filteredBuildings.size() > 0 && filteredBuildings.contains(building)) {
            filteredBuildings.remove(building);
        }

        if (buildingsUpdated.size() > 0 && buildingsUpdated.contains(building)) {
            buildingsUpdated.remove(building);
        }

        if (adapter.getBuildings().size() > 0 && adapter.getBuildings().contains(building)) {
            adapter.getBuildings().remove(building);
        }
        adapter.notifyItemRemoved(position);
    }
}
