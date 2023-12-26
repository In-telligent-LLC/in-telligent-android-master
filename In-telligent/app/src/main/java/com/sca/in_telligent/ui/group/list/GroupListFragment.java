package com.sca.in_telligent.ui.group.list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Predicate;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.SubscribeToCommunityRequest;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriptionRequest;
import com.sca.in_telligent.ui.base.BaseFragment;

import com.sca.in_telligent.util.AlertUtil;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import javax.inject.Inject;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
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
    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Building> suggestedBuildings = new ArrayList<>();
    private ArrayList<Building> otherBuildings = new ArrayList<>();
    private ArrayList<Building> filteredBuildings = new ArrayList<>();
    private ArrayList<Building> buildingsUpdated = new ArrayList<>();
    private ArrayList<Building> combinedBuildings = new ArrayList<>();

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public interface GroupListSelector {
        void groupsUpdated(ArrayList<Building> arrayList);

        void itemAboutClicked(int i, boolean z);

        void onPullToRefreshGroups();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$unsubscribed$21(DialogInterface dialogInterface, int i) {
    }

    @Override // com.sca.in_telligent.ui.group.list.GroupListAdapter.Callback
    public void onContactClicked(int i) {
    }

    @Override // com.sca.in_telligent.ui.group.list.GroupListMvpView
    public void onOptOutFromCommunitySuccess() {
    }

    public static GroupListFragment newInstance(ArrayList<Building> arrayList, ArrayList<Building> arrayList2, int i) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_GROUPS, arrayList);
        bundle.putSerializable(ARG_SUGGESTED, arrayList2);
        bundle.putInt(ARG_SUBSCRIBER_ID, i);
        GroupListFragment groupListFragment = new GroupListFragment();
        groupListFragment.setArguments(bundle);
        return groupListFragment;
    }

    @Override // com.sca.in_telligent.ui.base.BaseFragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.groupListSelector = (GroupListSelector) context;
        this.buildings = (ArrayList) getArguments().getSerializable(ARG_GROUPS);
        this.suggestedBuildings = (ArrayList) getArguments().getSerializable(ARG_SUGGESTED);
        this.subscriberId = getArguments().getInt(ARG_SUBSCRIBER_ID);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_group_list, viewGroup, false);
        ActivityComponent activityComponent = getActivityComponent();
        if (activityComponent != null) {
            activityComponent.inject(this);
            setUnBinder(ButterKnife.bind(this, inflate));
            this.mPresenter.onAttach(this);
        }
        return inflate;
    }

    @Override // com.sca.in_telligent.ui.base.BaseFragment
    protected void setUp(View view) {
        this.adapter.setCallback(this);
        this.mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        this.groupList.setLayoutManager(this.mLayoutManager);
        this.groupList.setAdapter(this.adapter);
        addItems(this.buildings, this.suggestedBuildings);
        initSpinner();
        setTextWatcher();
        setUpPullToRefresh();
    }

    private void setUpPullToRefresh() {
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.sca.in_telligent.ui.group.list.GroupListFragment$$ExternalSyntheticLambda16
            public final void onRefresh() {
                GroupListFragment.this.m211x85891d1f();
            }
        });
    }

    /* renamed from: lambda$setUpPullToRefresh$0$com-sca-in_telligent-ui-group-list-GroupListFragment  reason: not valid java name */
    public /* synthetic */ void m211x85891d1f() {
        GroupListSelector groupListSelector = this.groupListSelector;
        if (groupListSelector != null) {
            groupListSelector.onPullToRefreshGroups();
        }
    }

    public void addItems(ArrayList<Building> arrayList, ArrayList<Building> arrayList2) {
        this.swipeRefreshLayout.setRefreshing(false);
        this.adapter.addItems(arrayList, arrayList2);
    }

    /* renamed from: com.sca.in_telligent.ui.group.list.GroupListFragment$2  reason: invalid class name */
    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$sca$in_telligent$ui$group$list$GroupSpinnerItemType;

        static {
            int[] iArr = new int[GroupSpinnerItemType.values().length];
            $SwitchMap$com$sca$in_telligent$ui$group$list$GroupSpinnerItemType = iArr;
            try {
                iArr[GroupSpinnerItemType.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$sca$in_telligent$ui$group$list$GroupSpinnerItemType[GroupSpinnerItemType.PEOPLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$sca$in_telligent$ui$group$list$GroupSpinnerItemType[GroupSpinnerItemType.ORGANIZATIONS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$sca$in_telligent$ui$group$list$GroupSpinnerItemType[GroupSpinnerItemType.HELPLINES.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$sca$in_telligent$ui$group$list$GroupSpinnerItemType[GroupSpinnerItemType.EMERGENCY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public void spinnerItemSelected(Spinner spinner, int i) {
        int i2 = AnonymousClass2.$SwitchMap$com$sca$in_telligent$ui$group$list$GroupSpinnerItemType[GroupSpinnerItemType.NONE.getSpinnerMode(i).ordinal()];
        if (i2 == 1) {
            this.adapter.updateItems(this.buildings);
            this.groupListSelector.groupsUpdated(this.buildings);
        } else if (i2 == 2) {
            updateSpinnerList(Building.Category.PEOPLE);
        } else if (i2 == 3) {
            updateSpinnerList(Building.Category.ORGANIZATION);
        } else if (i2 == 4) {
            updateSpinnerList(Building.Category.HELPLINE);
        } else if (i2 != 5) {
        } else {
            updateSpinnerList(Building.Category.EMERGENCY);
        }
    }


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

    @Override // com.sca.in_telligent.ui.group.list.GroupListAdapter.Callback
    public void onAboutClicked(int i, boolean z) {
        this.groupListSelector.itemAboutClicked(i, z);
    }

    @Override // com.sca.in_telligent.ui.group.list.GroupListAdapter.Callback
    public void onConnectClicked(String str, boolean z, boolean z2) {
        final UpdateSubscriptionRequest updateSubscriptionRequest = new UpdateSubscriptionRequest();
        updateSubscriptionRequest.setBuildingId(str);
        if (z) {
            updateSubscriptionRequest.setAction(SubscribeToCommunityRequest.ACTION_SUBSCRIBE);
            this.mPresenter.subscribe(updateSubscriptionRequest, z2);
            return;
        }
        AlertUtil.showConfirmationAlert(getContext(), R.string.are_you_sure_you_want_to_unsubscribe_from_this_community, new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.group.list.GroupListFragment$$ExternalSyntheticLambda11
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                GroupListFragment.this.m210xaabd6bde(updateSubscriptionRequest, dialogInterface, i);
            }
        });
    }

    /* renamed from: lambda$onConnectClicked$3$com-sca-in_telligent-ui-group-list-GroupListFragment  reason: not valid java name */
    public /* synthetic */ void m210xaabd6bde(UpdateSubscriptionRequest updateSubscriptionRequest, DialogInterface dialogInterface, int i) {
        updateSubscriptionRequest.setAction(SubscribeToCommunityRequest.ACTION_UNSUBSCRIBE);
        this.mPresenter.unsubscribe(updateSubscriptionRequest);
    }

    @Override // com.sca.in_telligent.ui.group.list.GroupListAdapter.Callback
    public void onIgnoreClicked(String str, int i) {
        this.mPresenter.onIgnoreCommunityClicked(str, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.sca.in_telligent.ui.group.list.GroupListFragment$1  reason: invalid class name */
    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public class AnonymousClass1 implements TextWatcher {
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        AnonymousClass1() {
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

    }

    private void setTextWatcher() {
        this.searchEdittext.addTextChangedListener(new AnonymousClass1());
    }

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

    @Override // com.sca.in_telligent.ui.group.list.GroupListMvpView
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
        if (this.filteredBuildings.size() > 0 && this.filteredBuildings.contains(building)) {
            this.filteredBuildings.remove(building);
        }
        if (this.buildingsUpdated.size() > 0 && this.buildingsUpdated.contains(building)) {
            this.buildingsUpdated.remove(building);
        }
        if (this.adapter.getBuildings().size() > 0 && this.adapter.getBuildings().contains(building)) {
            this.adapter.getBuildings().remove(building);
        }
        this.adapter.notifyItemRemoved(i);
    }
}
