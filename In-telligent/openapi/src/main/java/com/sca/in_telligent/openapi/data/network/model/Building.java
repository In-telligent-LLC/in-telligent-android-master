package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Objects;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class Building implements Serializable {
    @SerializedName("BuildingAddress")
    private BuildingAddress buildingAddress;
    @SerializedName("BuildingCategory")
    private BuildingCategory buildingCategory;
    @SerializedName("BuildingsSubscriber")
    private BuildingsSubscriber buildingsSubscriber;
    private Category category;
    @SerializedName("created")
    private String created;
    private boolean createdByMe;
    @SerializedName("description")
    private String description;
    @SerializedName("filterCategory")
    private String filterCategory;
    @SerializedName("id")
    private int id;
    private boolean isManagedByCurrentUser;
    private boolean isOther;
    @SerializedName("isTextEnabled")
    @JsonAdapter(BooleanTypeAdapter.class)
    private boolean isTextEnabled;
    @SerializedName("isVoipEnabled")
    @JsonAdapter(BooleanTypeAdapter.class)
    private boolean isVoipEnabled;
    @SerializedName("name")
    private String name = "";
    @SerializedName("password")
    private String password;
    @SerializedName("subscriberCount")
    private int subscriberCount;
    @SerializedName("subscriberId")
    private Integer subscriberId;
    private boolean suggestedHeader;
    private boolean suggestedItem;
    private Type type;
    @SerializedName("website")
    private String website;

    public boolean isOther() {
        return this.isOther;
    }

    public void setOther(boolean z) {
        this.isOther = z;
    }

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPassword() {
        return this.password;
    }

    public String getWebsite() {
        return this.website;
    }

    public boolean isVoipEnabled() {
        return this.isVoipEnabled;
    }

    public boolean isTextEnabled() {
        return this.isTextEnabled;
    }

    public String getCreated() {
        return this.created;
    }

    public int getSubscriberCount() {
        return this.subscriberCount;
    }

    public BuildingAddress getBuildingAddress() {
        return this.buildingAddress;
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public enum Type {
        SUGGESTED_HEADER(0),
        SUGGESTED_ITEM(1),
        NORMAL(2),
        GRAY_HEADER(3);
        
        int number;

        Type(int i) {
            this.number = i;
        }

        public int getNumber() {
            return this.number;
        }
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public enum Category {
        ORGANIZATION("organization"),
        PEOPLE("people"),
        EMERGENCY("emergency"),
        HELPLINE("helpline");
        
        String category;

        Category(String str) {
            this.category = str;
        }

        public String getCategory() {
            return this.category;
        }
    }

    public boolean isCreatedByMe() {
        return this.createdByMe;
    }

    public void setCreatedByMe(boolean z) {
        this.createdByMe = z;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public boolean isSuggestedHeader() {
        return this.suggestedHeader;
    }

    public void setSuggestedHeader(boolean z) {
        this.suggestedHeader = z;
    }

    public Integer getSubscriberId() {
        return this.subscriberId;
    }

    public boolean isSuggestedItem() {
        return this.suggestedItem;
    }

    public void setSuggestedItem(boolean z) {
        this.suggestedItem = z;
    }

    public void setIsManagedByUser(boolean z) {
        this.isManagedByCurrentUser = z;
    }

    public boolean isManagedByCurrentUser() {
        return this.isManagedByCurrentUser;
    }

    public BuildingsSubscriber getBuildingsSubscriber() {
        return this.buildingsSubscriber;
    }

    public String getFilterCategory() {
        return this.filterCategory;
    }

    public String getImageUrl() {
        BuildingCategory buildingCategory = this.buildingCategory;
        if (buildingCategory == null || buildingCategory.getBuildingCategoryImages() == null || this.buildingCategory.getBuildingCategoryImages().size() <= 0) {
            return null;
        }
        return "https://app.in-telligent.com/img/categories/" + this.buildingCategory.getBuildingCategoryImages().get(0).getImage();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof Building) && getId() == ((Building) obj).getId();
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(getId()));
    }
}
