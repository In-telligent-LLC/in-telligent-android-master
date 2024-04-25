package com.sca.in_telligent.ui.group.list;

import com.sca.in_telligent.R;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.AutoSubscribeRequest;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.SearchCommunityResponse;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriptionRequest;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;

public class GroupListPresenter<V extends GroupListMvpView> extends BasePresenter<V> implements GroupListMvpPresenter<V> {
    private final int ignoredPosition;

    @Inject
    public GroupListPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
        this.ignoredPosition = -1;
    }


    @Override
    public void searchCommunities(String groupName) {
        getMvpView().showLoading();
        getCompositeDisposable().add(
                getDataManager().searchCommunities(groupName).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(searchCommunityResponse -> {
                            getMvpView().hideLoading();
                            getMvpView().updateGroupList(searchCommunityResponse.getBuildings());

                        }, throwable -> getMvpView().hideLoading()));
    }


    @Override
    public void subscribe(UpdateSubscriptionRequest updateSubscriptionRequest, boolean suggested) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().updateSubscription(updateSubscriptionRequest).
                subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(
                        successResponse -> {
                            getMvpView().hideLoading();
                            if (successResponse.isSuccess()) {
                                getMvpView().subscribed(updateSubscriptionRequest.getBuildingId(), suggested);
                            } else {
                                if (successResponse.getErrors() != null) {
                                    getMvpView().showMessage(successResponse.getErrors().getName().get(0));
                                }
                            }
                        }, throwable -> getMvpView().hideLoading()));
    }



    @Override
    public void unsubscribe(UpdateSubscriptionRequest updateSubscriptionRequest) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().updateSubscription(updateSubscriptionRequest).
                subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(
                        successResponse -> {
                            getMvpView().hideLoading();
                            if (successResponse.isSuccess()) {
                                getMvpView().unsubscribed(updateSubscriptionRequest.getBuildingId());
                            } else {
                                if (successResponse.getErrors() != null) {
                                    getMvpView().showMessage(successResponse.getErrors().getName().get(0));
                                }
                            }
                        }, throwable -> getMvpView().hideLoading()));
    }


    @Override
    public void optOutFromCommunity(int buildingId, boolean optOut) {
        getMvpView().showLoading();
        AutoSubscribeRequest autoSubscribeRequest = new AutoSubscribeRequest(buildingId, optOut);
        getCompositeDisposable().add(getDataManager().autoSubscribe(autoSubscribeRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui()).subscribe(successResponse -> {
                    getMvpView().hideLoading();
                    getMvpView().onOptOutFromCommunitySuccess();
                }, throwable -> {
                    getMvpView().hideLoading();
                    getMvpView().onError(R.string.there_was_error_sending_request);
                }));
    }


    @Override
    public void getSuggestedGroups() {
        getCompositeDisposable().add(
                getDataManager().getSuggestedGroups()
                        .map((Function<SearchCommunityResponse,
                                                        List<Building>>) SearchCommunityResponse::getBuildings)
                        .take(2)
                        .flatMap(Observable::fromIterable)
                        .doOnNext(building -> building.setType(Building.Type.SUGGESTED_ITEM)).toList()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(
                                buildings -> {
                                    getMvpView().hideLoading();
                                    getMvpView().updateSuggested((ArrayList<Building>) buildings);

                                }, throwable -> getMvpView().hideLoading()));
    }


    private void getNextSuggested(int ignoredPosition) {
        getCompositeDisposable().add(
                getDataManager().getSuggestedGroups()
                        .map(searchCommunityResponse -> searchCommunityResponse.getBuildings().get(0))
                        .doOnNext(building -> building.setType(Building.Type.SUGGESTED_ITEM))
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(
                                building -> {
                                    getMvpView().hideLoading();
                                    getMvpView().updateNextSuggested(building, ignoredPosition);

                                }, throwable -> getMvpView().hideLoading()));
    }

    @Override
    public void onIgnoreCommunityClicked(String buildingId, int ignoredPosition) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().ignoreBuilding(buildingId).subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui()).doOnComplete(() -> {
                    getMvpView().hideLoading();
                }).subscribe(successResponse -> {
                    getNextSuggested(ignoredPosition);
                }, throwable -> {
                    getMvpView().onError(R.string.there_was_error_sending_request);
                }));
    }


}
