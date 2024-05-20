package com.sca.in_telligent.util;

import android.content.Context;
import android.location.Location;
import com.google.android.gms.maps.model.LatLng;

public interface LocationUtil {


  public void getCurrentLocation(final TimeoutLocationListener timeoutLocationListener);

  public Location getLastKnownLocation();

  public LatLng getLatLngFromZip(final Context context, final String zipCode);

  boolean isProviderEnabled();
}
