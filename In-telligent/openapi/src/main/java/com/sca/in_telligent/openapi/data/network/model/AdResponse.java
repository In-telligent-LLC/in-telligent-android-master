package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Marcos Ambrosi on 5/2/19.
 */
public class AdResponse {

    @SerializedName("data")
    Data data;

    public BannerAd getBannerAd() {
        if (data == null) return null;
        return data.bannerAd;
    }

    public static class Data {

        @SerializedName("bannerAd")
        private BannerAd bannerAd;

        public BannerAd getBannerAd() {
            return bannerAd;
        }

    }

    public static class BannerAd {

        public static final String BASE_IMAGE_URL = "https://app.in-telligent.com/img/banner_ads/";

        @SerializedName("image")
        private String image;

        @SerializedName("id")
        private int adId;

        @SerializedName("url")
        private String url;


        public int getAdId() {
            return adId;
        }

        public String getUrl() {
            return url;
        }

        public String getImage() {
            return BASE_IMAGE_URL.concat(image);
        }
    }
}
