package com.sca.in_telligent.ui.inbox;

import com.sca.in_telligent.R;

public enum InboxNotificationType {

  NORMAL("normal"),
  LIFE_SAFETY("life-safety"),
  PERSONAL_SAFETY("personal-safety"),
  CRITICAL("critical"),
  WEATHER_ALERT("weather-alert"),
  PING("ping"),
  SUGGESTED("suggested");

  private String alertType;

  InboxNotificationType(String alertType) {
    this.alertType = alertType;
  }

  public InboxNotificationType getNotificationType(String type) {
    for (InboxNotificationType notificationType : values()) {
      if (notificationType.alertType.equals(type)) {
        return notificationType;
      }
    }
    return NORMAL;
  }

  public int getName() {
    switch (this) {
      case NORMAL:
        return R.string.normal;
      case LIFE_SAFETY:
        return R.string.life_safety;
      case PERSONAL_SAFETY:
        return R.string.personal_alert;
      case CRITICAL:
        return R.string.critical_alert;
      case WEATHER_ALERT:
        return R.string.critical_alert;
      case SUGGESTED:
        return R.string.suggested_alerts;
      case PING:
        return R.string.ping_alert;
    }
    return R.string.normal;
  }

  public int getIcon() {
    switch (this) {
      case NORMAL:
        return R.drawable.regularx;
      case LIFE_SAFETY:
        return R.drawable.life_safetyx;
      case PERSONAL_SAFETY:
        return R.drawable.life_safetyx;
      case CRITICAL:
        return R.drawable.criticalxx;
      case WEATHER_ALERT:
        return R.drawable.weatherx;
      case SUGGESTED:
        return 0;
      case PING:
        return R.drawable.ping;
    }
    return 0;
  }

  public int getColor() {
    switch (this) {
      case NORMAL:
        return R.color.gray59;
      case LIFE_SAFETY:
        return R.color.red;
      case PERSONAL_SAFETY:
        return R.color.gray59;
      case CRITICAL:
        return R.color.yellow;
      case WEATHER_ALERT:
        return R.color.yellow;
      case SUGGESTED:
        return R.color.gray59;
      case PING:
        return R.color.gray59;
    }
    return R.color.gray59;

  }
}
