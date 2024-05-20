package com.sca.in_telligent.ui.settings.notification;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.AlertSubscriptionRequest;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.Subscriber;
import com.sca.in_telligent.openapi.data.network.model.SubscriberRequest;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriberRequest;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.ui.base.BaseFragment;
import io.reactivex.Observable;
import java.util.ArrayList;
import javax.inject.Inject;

public class NotificationSettingsFragment extends BaseFragment implements
    NotificationSettingsMvpView, NotificationSettingAdapter.Callback {

  public static final String TAG = "NotificationSettingsFragment";

  @Inject
  NotificationSettingsMvpPresenter<NotificationSettingsMvpView> mPresenter;

  @Inject
  LinearLayoutManager mLayoutManager;

  @Inject
  NotificationSettingAdapter notificationSettingAdapter;

  @BindView(R.id.notification_setting_recyclerview)
  RecyclerView notificationSettingRecyclerView;

  @BindView(R.id.notification_setting_lightning_toggle)
  Switch lightningToggle;

  @BindView(R.id.notification_setting_severe_toggle)
  Switch severeToggle;

  private boolean lightningEnabled = false;
  private boolean weatherEnabled = false;

  public interface NotificationSettingsSelector {

    void alertSubscriptionUpdated(String buildingId, String subscription);

    void weatherWarningUpdated(boolean weatherEnabled, boolean lightningEnabled);
  }

  NotificationSettingsSelector notificationSettingsSelector;

  private ArrayList<Building> buildings = new ArrayList<>();

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    buildings = (ArrayList<Building>) getArguments().getSerializable("buildings");
    lightningEnabled = getArguments().getBoolean("lightning");
    weatherEnabled = getArguments().getBoolean("weather");
    notificationSettingsSelector = (NotificationSettingsSelector) context;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_notification_settings, container, false);
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
    if (buildings != null && buildings.size() > 0) {
      notificationSettingAdapter.setCallback(this);
      notificationSettingAdapter.addItems(buildings);
      mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
      notificationSettingRecyclerView.setLayoutManager(mLayoutManager);
      notificationSettingRecyclerView.setAdapter(notificationSettingAdapter);
    }

    severeToggle.setChecked(weatherEnabled);
    lightningToggle.setChecked(lightningEnabled);
  }

  @Override
  public void onItemSelected(String buildingId, String subscription) {
    AlertSubscriptionRequest alertSubscriptionRequest = new AlertSubscriptionRequest();
    alertSubscriptionRequest.setBuildingId(buildingId);
    alertSubscriptionRequest.setSubscription(subscription);
    mPresenter.syncAlertSubscription(alertSubscriptionRequest);
  }

  @OnCheckedChanged(R.id.notification_setting_severe_toggle)
  void onWeatherSelected(CompoundButton button, boolean checked) {
    weatherEnabled = checked;
    updateWeatherWarning();
    updateSubscriber();
  }

  @OnCheckedChanged(R.id.notification_setting_lightning_toggle)
  void onLightningSelected(CompoundButton button, boolean checked) {
    lightningEnabled = checked;
    updateWeatherWarning();
    updateSubscriber();
  }

  private void updateWeatherWarning() {
    UpdateSubscriberRequest updateSubscriberRequest = new UpdateSubscriberRequest();
    SubscriberRequest subscriberRequest = new SubscriberRequest();
    subscriberRequest.setLightningAlertEnabled(lightningEnabled);
    subscriberRequest.setWeatherAlertEnabled(weatherEnabled);
    updateSubscriberRequest.setSubscriberRequest(subscriberRequest);
    mPresenter.syncMetadata(updateSubscriberRequest);
  }

  private void updateSubscriber() {
    notificationSettingsSelector.weatherWarningUpdated(weatherEnabled,
        lightningEnabled);
  }

  @Override
  public void subscriptonUpdated(String buildingId, String subscription) {
    Observable.fromIterable(buildings)
        .doOnNext(building -> {
          if (building.getId() == Integer.parseInt(buildingId)) {
            building.getBuildingsSubscriber().setAlertsSubscription(subscription);
          }
        })
        .toList().subscribe(updatedBuildings -> buildings = (ArrayList<Building>) updatedBuildings);
    notificationSettingAdapter.addItems(buildings);
    notificationSettingsSelector.alertSubscriptionUpdated(buildingId, subscription);
  }

  @Override
  public void subscriberUpdateResult(Subscriber subscriber) {
    notificationSettingsSelector.weatherWarningUpdated(subscriber.isWeatherAlertEnabled(),
        subscriber.isLightningAlertEnabled());
  }
}
