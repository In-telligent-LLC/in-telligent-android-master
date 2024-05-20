package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.sca.in_telligent.openapi.data.network.ApiEndPoint;

import java.io.Serializable;
import java.util.Objects;

public class Building implements Serializable {

  private Type type;

  private Category category;
  private boolean isManagedByCurrentUser;

  public boolean isOther() {
    return isOther;
  }

  public void setOther(boolean other) {
    isOther = other;
  }

  private boolean isOther;

  public int getId() {
    return id;
  }

  @SerializedName("id")
  private int id;

  @SerializedName("name")
  private String name = "";

  public String getDescription() {
    return description;
  }

  @SerializedName("description")
  private String description;

  public String getPassword() {
    return password;
  }

  @SerializedName("password")
  private String password;

  public String getWebsite() {
    return website;
  }

  @SerializedName("website")
  private String website;

  @SerializedName("filterCategory")
  private String filterCategory;

  public boolean isVoipEnabled() {
    return isVoipEnabled;
  }

  @JsonAdapter(BooleanTypeAdapter.class)
  @SerializedName("isVoipEnabled")
  private boolean isVoipEnabled;

  public boolean isTextEnabled() {
    return isTextEnabled;
  }

  @JsonAdapter(BooleanTypeAdapter.class)
  @SerializedName("isTextEnabled")
  private boolean isTextEnabled;

  public String getCreated() {
    return created;
  }

  @SerializedName("created")
  private String created;

  @SerializedName("subscriberId")
  private Integer subscriberId;

  public int getSubscriberCount() {
    return subscriberCount;
  }

  @SerializedName("subscriberCount")
  private int subscriberCount;

  @SerializedName("BuildingsSubscriber")
  private BuildingsSubscriber buildingsSubscriber;

  @SerializedName("BuildingCategory")
  private BuildingCategory buildingCategory;

  public BuildingAddress getBuildingAddress() {
    return buildingAddress;
  }

  @SerializedName("BuildingAddress")
  private BuildingAddress buildingAddress;

  public enum Type {
    SUGGESTED_HEADER(0), SUGGESTED_ITEM(1), NORMAL(2),
    GRAY_HEADER(3);

    int number;

    Type(int i) {
      number = i;
    }

    public int getNumber() {
      return number;
    }
  }

  public enum Category {
    ORGANIZATION("organization"), PEOPLE("people"), EMERGENCY(
        "emergency"), HELPLINE("helpline");

    String category;

    Category(String s) {
      category = s;
    }

    public String getCategory() {
      return category;
    }
  }

  public boolean isCreatedByMe() {
    return createdByMe;
  }

  public void setCreatedByMe(boolean createdByMe) {
    this.createdByMe = createdByMe;
  }

  private boolean createdByMe;

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private boolean suggestedHeader;

  public boolean isSuggestedHeader() {
    return suggestedHeader;
  }

  public void setSuggestedHeader(boolean suggestedHeader) {
    this.suggestedHeader = suggestedHeader;
  }

  public Integer getSubscriberId() {
    return subscriberId;
  }

  public boolean isSuggestedItem() {
    return suggestedItem;
  }

  public void setSuggestedItem(boolean suggestedItem) {
    this.suggestedItem = suggestedItem;
  }

  public void setIsManagedByUser(boolean isManagedByCurrentUser){
    this.isManagedByCurrentUser = isManagedByCurrentUser;
  }

  public boolean isManagedByCurrentUser() {
    return isManagedByCurrentUser;
  }

  public BuildingsSubscriber getBuildingsSubscriber() {
    return buildingsSubscriber;
  }

  public String getFilterCategory() {
    return filterCategory;
  }

  private boolean suggestedItem;

  public String getImageUrl() {
    if (buildingCategory != null && buildingCategory.getBuildingCategoryImages() != null
        && buildingCategory.getBuildingCategoryImages().size() > 0) {
      return ApiEndPoint.BASE_IMAGE_URL + "/img/categories/" + buildingCategory
          .getBuildingCategoryImages().get(0).getImage();
    } else {
      return null;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Building)) return false;
    Building building = (Building) o;
    return getId() == building.getId();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }
}
