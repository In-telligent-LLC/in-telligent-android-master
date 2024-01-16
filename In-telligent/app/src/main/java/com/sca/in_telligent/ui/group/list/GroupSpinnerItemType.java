package com.sca.in_telligent.ui.group.list;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public enum GroupSpinnerItemType {
    NONE(0),
    PEOPLE(1),
    ORGANIZATIONS(2),
    HELPLINES(3),
    EMERGENCY(4);
    
    private final int itemType;

    GroupSpinnerItemType(int i) {
        this.itemType = i;
    }

    public GroupSpinnerItemType getSpinnerMode(int i) {
        GroupSpinnerItemType[] values;
        for (GroupSpinnerItemType groupSpinnerItemType : values()) {
            if (groupSpinnerItemType.itemType == i) {
                return groupSpinnerItemType;
            }
        }
        return null;
    }
}
