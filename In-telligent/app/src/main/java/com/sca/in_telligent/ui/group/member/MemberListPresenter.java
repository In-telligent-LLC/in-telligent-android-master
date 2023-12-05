package com.sca.in_telligent.ui.group.member;

import android.util.Log;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.Invitee;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MemberListPresenter<V extends MemberListMvpView> extends BasePresenter<V> implements
        MemberListMvpPresenter<V> {

    private static final String TAG = MemberListPresenter.class.getSimpleName();
    private String buildingId;
    private ArrayList<Invitee> invites;

    @Inject
    public MemberListPresenter(DataManager dataManager,
                               SchedulerProvider schedulerProvider,
                               CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getMembers(String buildingId, boolean showLoading) {
        this.buildingId = buildingId;

        if (showLoading) {
            getMvpView().showLoading();
        }

        getCompositeDisposable().add(
                getDataManager().getInvitees(buildingId).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui()).subscribe(inviteeResponse -> {
                    getMvpView().hideLoading();
                    invites = inviteeResponse.getInvites();
                    getMvpView().memberListFetched(invites);
                }, throwable -> getMvpView().hideLoading()));
    }


    @Override
    public void getLastLocation(String subscriberId, String name) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().getLastLocation(subscriberId)
                .subscribeOn(getSchedulerProvider().io()).
                        observeOn(getSchedulerProvider().ui())
                .subscribe(locationModel -> {
                    getMvpView().hideLoading();
                    getMvpView().lastLocationFetched(locationModel, subscriberId, name);
                }, throwable -> getMvpView().hideLoading()));
    }

    @Override
    public void removeMember(int inviteId, int position) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().removeMember(inviteId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(successResponse -> {
                    getMvpView().hideLoading();
                    getMembers(buildingId, true);
                    getMvpView().onMemberInvitationRemoved(position);
                }, throwable -> {
                    Log.e(TAG, "There was an error trying to delete an invite", throwable);
                    getMvpView().hideLoading();
                }));
    }

    @Override
    public void filterMembers(String name) {
        if (name.isEmpty()) {
            getMvpView().memberListFetched(invites);
            return;
        }

        Consumer<Throwable> onError = throwable -> {
            Log.e(TAG, "filterNames: " + throwable.getMessage(), throwable);
        };

        Disposable membersFilterDisposable = Observable.fromIterable(invites)
                .filter(invitee -> invitee.getName()
                        .contains(name)).toList()
                .observeOn(getSchedulerProvider().ui())
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(invitees -> getMvpView().memberListFetched(new ArrayList<>(invitees)), onError);

        getCompositeDisposable().add(membersFilterDisposable);
    }
}
