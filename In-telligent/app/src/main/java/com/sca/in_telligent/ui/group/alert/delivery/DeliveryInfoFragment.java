package com.sca.in_telligent.ui.group.alert.delivery;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.DeliveryInfoResponse;
import com.sca.in_telligent.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class DeliveryInfoFragment extends BaseFragment implements DeliveryInfoMvpView {

  public static final String TAG = "DeliveryInfoFragment";

  @Inject
  DeliveryInfoMvpPresenter<DeliveryInfoMvpView> mPresenter;

  @Inject
  DeliveryInfoAdapter deliveryInfoAdapter;

  @Inject
  LinearLayoutManager mLayoutManager;

  @BindView(R.id.delivery_info_recyclerview)
  RecyclerView deliveryInfoList;

  @BindView(R.id.delivery_info_title_text)
  TextView titleText;

  @BindView(R.id.delivery_info_info_text)
  TextView recipientText;

  private String buildingId;
  private String notificationId;
  private String messageName;

  public static DeliveryInfoFragment newInstance(String buildingId, String notificationId,
      String messageName) {
    Bundle args = new Bundle();
    args.putString("buildingId", buildingId);
    args.putString("notificationId", notificationId);
    args.putString("messageName", messageName);
    DeliveryInfoFragment fragment = new DeliveryInfoFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    buildingId = getArguments().getString("buildingId");
    notificationId = getArguments().getString("notificationId");
    messageName = getArguments().getString("messageName");
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_delivery_info, container, false);
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
    mPresenter.getDeliveryInfo(buildingId, notificationId);
    titleText.setText(messageName);
  }

  @Override
  public void onDestroyView() {
    mPresenter.onDetach();
    super.onDestroyView();
  }

  @Override
  public void deliveryInfoFetched(DeliveryInfoResponse deliveryInfoResponse) {
    if (deliveryInfoResponse.getDeliveryInfoItems() != null
        && deliveryInfoResponse.getDeliveryInfoItems().size() > 0) {
      recipientText.setText(getString(R.string.total_recipients)+": (" + deliveryInfoResponse.getSent() + ")");
      deliveryInfoAdapter.addItems(deliveryInfoResponse.getDeliveryInfoItems());
      mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
      deliveryInfoList.setLayoutManager(mLayoutManager);
      deliveryInfoList.setAdapter(deliveryInfoAdapter);
    }
  }
}
