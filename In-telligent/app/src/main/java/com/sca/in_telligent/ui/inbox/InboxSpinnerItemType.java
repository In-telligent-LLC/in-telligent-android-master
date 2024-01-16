package com.sca.in_telligent.ui.inbox;

public enum InboxSpinnerItemType {
  NONE(0),
  UNREAD(1),
  SAVED(2);

  private final int itemType;

  InboxSpinnerItemType(int itemType) {
    this.itemType = itemType;
  }

  public InboxSpinnerItemType getSpinnerMode(int id) {
    for (InboxSpinnerItemType spinnerItem : values()) {
      if (spinnerItem.itemType == id) {
        return spinnerItem;
      }
    }
    return null;
  }
}
