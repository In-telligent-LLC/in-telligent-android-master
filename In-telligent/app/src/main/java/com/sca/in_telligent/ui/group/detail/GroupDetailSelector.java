package com.sca.in_telligent.ui.group.detail;

public interface GroupDetailSelector {

  void groupLeftClicked(int position);

  void groupRightClick(int position);

  void messageFeedClick(int buildingId);

  void viewMemberSelected(int buldingId,int memberCount, String groupName);

  void inviteOtherSelected(int buildingId);

  void alertViewSelected(int buildingId);

  void editGroupSelected(int position);

  void unSubscribed(int buildingId);

  void subscribed(int buildingId);

}
