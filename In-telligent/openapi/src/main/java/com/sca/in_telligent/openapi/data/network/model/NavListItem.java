package com.sca.in_telligent.openapi.data.network.model;

import java.io.Serializable;

public class NavListItem implements Serializable {

  public NavListItem(String name, int image) {
    this.name = name;
    this.image = image;
  }

  public String name;

  public int image;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getImage() {
    return image;
  }

  public void setImage(int image) {
    this.image = image;
  }
}
