package com.sca.in_telligent.ui.findlocation;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.LocationModel;
import com.sca.in_telligent.ui.base.BaseDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindLocationDialog extends BaseDialog implements FindLocationMvpView,
    OnMapReadyCallback {

  private static final String TAG = "FindLocationDialog";

  @Inject
  FindLocationMvpPresenter<FindLocationMvpView> mPresenter;

  @Inject
  FusedLocationProviderClient fusedLocationProviderClient;

  private GoogleMap googleMap;

  Location mLastKnownLocation;

  @BindView(R.id.mapView)
  MapView mapView;

  private LocationModel lastLocation;
  private String subscriberId = "";
  private String name = "";

  public static FindLocationDialog newInstance(LocationModel locationModel, String subscriberId,
      String name) {
    FindLocationDialog fragment = new FindLocationDialog();
    Bundle bundle = new Bundle();
    bundle.putSerializable("locationModel", locationModel);
    bundle.putString("subscriberId", subscriberId);
    bundle.putString("name", name);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.find_location_dialog, container, false);

    ActivityComponent component = getActivityComponent();
    if (component != null) {

      component.inject(this);

      setUnBinder(ButterKnife.bind(this, view));

      mPresenter.onAttach(this);
    }
    mapView.setVisibility(View.INVISIBLE);
    mapView.onCreate(savedInstanceState);
    mapView.onResume();

    return view;
  }

  @Override
  protected void setUp(View view) {
//    mPresenter.requestLocationPermission();
    subscriberId = getArguments().getString("subscriberId");

    lastLocation = (LocationModel) getArguments().getSerializable("locationModel");
    name = getArguments().getString("name");
    if (lastLocation != null) {
      MapsInitializer.initialize(getContext());

      mapView.getMapAsync(this);
    }

    if (!subscriberId.isEmpty()) {
      mPresenter.getUpdatedLocation(subscriberId);
    }
  }

  public void show(FragmentManager fragmentManager) {
    super.show(fragmentManager, TAG);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    if (googleMap != null && lastLocation != null && lastLocation.getLat() != null
        && lastLocation.getLng() != null) {
      mapView.setVisibility(View.VISIBLE);
      this.googleMap = googleMap;
//      googleMap.setMyLocationEnabled(true);
      googleMap.getUiSettings().setMyLocationButtonEnabled(false);
//      googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(createBoundary(  new LatLng(lastLocation.getLat(),
//          lastLocation.getLng())), 10));
      googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
          new LatLng(lastLocation.getLat(),
              lastLocation.getLng()), 15));
      addMarker(new LatLng(lastLocation.getLat(), lastLocation.getLng()));
    }
  }

  @Override
  public void updatedLocationFetched(LocationModel locationModel) {
    if (locationModel != null) {
      updateMap(locationModel);
    }
  }

  @Override
  public void locationPermissionResult(boolean granted) {
    if (!granted) {
      dismissDialog(TAG);
    }
  }

  private void updateMap(LocationModel locationModel) {
    if (googleMap != null && locationModel != null && locationModel.getLat() != null
        && locationModel.getLng() != null) {
      googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
          new LatLng(locationModel.getLat(),
              locationModel.getLng()), 15));
      addMarker(new LatLng(locationModel.getLat(), locationModel.getLng()));
    }
  }

  void addMarker(LatLng latLng) {
    googleMap.addMarker(new MarkerOptions().position(latLng)
        .title(name)).showInfoWindow();
  }

  @OnClick({R.id.find_location_back_button, R.id.find_location_back_text})
  void backClick(View v) {
    getActivity().onBackPressed();
  }

  @Override
  public void onDestroyView() {
    mPresenter.onDetach();
    super.onDestroyView();
  }

  LatLngBounds createBoundary(LatLng latLng) {
    LatLngBounds latLngBounds;
    LatLngBounds.Builder builder = new Builder();
    builder.include(latLng);
    latLngBounds = builder.build();
    return latLngBounds;
  }


}
