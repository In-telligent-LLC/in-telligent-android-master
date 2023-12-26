package com.sca.in_telligent.ui.contact.message.deliver;

import android.util.Log;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.BuildingMember;
import com.sca.in_telligent.openapi.data.network.model.Invitee;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

public class ContactDeliverPresenter<V extends ContactDeliverMvpView> extends
        BasePresenter<V> implements
        ContactDeliverMvpPresenter<V> {


    private static final String TAG = ContactDeliverPresenter.class.getSimpleName();
    private ArrayList<Invitee> inviteeList = new ArrayList<>();
    private ArrayList<BuildingMember> memberList = new ArrayList<>();

    @Inject
    public ContactDeliverPresenter(DataManager dataManager,
                                   SchedulerProvider schedulerProvider,
                                   CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getInvitees(String buildingId) {
        getMvpView().showLoading();
        getCompositeDisposable().add(
                getDataManager().getInvitees(buildingId).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui()).subscribe(inviteeResponse -> {
                    getMvpView().hideLoading();
                    ArrayList<Invitee> invites = inviteeResponse.getInvites();
                    getMvpView().inviteeFetched(invites);
                    inviteeList = invites;
                }, throwable -> getMvpView().hideLoading()));
    }

    @Override
    public void getSubscribers(String buildingId) {
        getMvpView().showLoading();
        getCompositeDisposable().add(
                getDataManager().getBuildingMembers(buildingId).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(buildingMembers -> {
                            getMvpView().hideLoading();
                            getMvpView().memberFetched(buildingMembers);
                            this.memberList = buildingMembers;
                        }, throwable -> getMvpView().hideLoading()));
    }

    @Override
    public void filterNames(String name) {

        if (name.isEmpty()) {
            getMvpView().inviteeFetched(inviteeList);
            getMvpView().memberFetched(memberList);
            return;
        }

        Consumer<Throwable> onError = throwable -> {
            Log.e(TAG, "filterNames: " + throwable.getMessage(), throwable);
        };

        Disposable inviteFilterDisposable = Observable.fromIterable(inviteeList)
                .filter(invitee -> invitee.getName().toLowerCase()
                        .contains(name.toLowerCase())).toList()
                .observeOn(getSchedulerProvider().ui())
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(invitees -> getMvpView().inviteeFetched(new ArrayList<>(invitees)), onError);

        getCompositeDisposable().add(inviteFilterDisposable);


        Disposable buildingMembersFilterDisposable = Observable.fromIterable(memberList)
                .filter(buildingMember -> buildingMember.getSubscriberEmail().contains(name.toLowerCase())).toList()
                .observeOn(getSchedulerProvider().ui())
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(members -> getMvpView().memberFetched(new ArrayList<>(members)), onError);

        getCompositeDisposable().add(buildingMembersFilterDisposable);

    }
}
