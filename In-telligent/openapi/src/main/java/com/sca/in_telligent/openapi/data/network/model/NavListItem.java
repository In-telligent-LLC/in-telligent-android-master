package com.sca.in_telligent.openapi.data.network.model;

import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class NavListItem implements Serializable {
    public int image;
    public String name;

    public NavListItem(String str, int i) {
        this.name = str;
        this.image = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int i) {
        this.image = i;
    }
}
