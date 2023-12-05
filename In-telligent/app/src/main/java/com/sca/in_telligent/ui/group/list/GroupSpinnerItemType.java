package com.sca.in_telligent.ui.group.list;

public enum GroupSpinnerItemType {
  NONE(0),
  PEOPLE(1),
  ORGANIZATIONS(2), HELPLINES(3), EMERGENCY(4);

  private int itemType;

  GroupSpinnerItemType(int itemType) {
    this.itemType = itemType;
  }

  public GroupSpinnerItemType getSpinnerMode(int id) {
    for (GroupSpinnerItemType spinnerItem : values()) {
      if (spinnerItem.itemType == id) {
        return spinnerItem;
      }
    }
    return null;
  }
}
