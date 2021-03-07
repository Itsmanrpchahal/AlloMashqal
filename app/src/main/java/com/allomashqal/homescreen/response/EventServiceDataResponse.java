package com.allomashqal.homescreen.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventServiceDataResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data {

        @SerializedName("service_list")
        @Expose
        private List<ServiceList> serviceList = null;
        @SerializedName("provider_detail")
        @Expose
        private ProviderDetail providerDetail;

        public List<ServiceList> getServiceList() {
            return serviceList;
        }

        public void setServiceList(List<ServiceList> serviceList) {
            this.serviceList = serviceList;
        }

        public ProviderDetail getProviderDetail() {
            return providerDetail;
        }

        public void setProviderDetail(ProviderDetail providerDetail) {
            this.providerDetail = providerDetail;
        }

        public class ServiceList {

            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("price")
            @Expose
            private String price;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("date")
            @Expose
            private String date;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

        }

        public class ProviderDetail {

            @SerializedName("provider_name")
            @Expose
            private String providerName;
            @SerializedName("rating")
            @Expose
            private Integer rating;
            @SerializedName("latitude")
            @Expose
            private String latitude;
            @SerializedName("longitude")
            @Expose
            private String longitude;

            public String getProviderName() {
                return providerName;
            }

            public void setProviderName(String providerName) {
                this.providerName = providerName;
            }

            public Integer getRating() {
                return rating;
            }

            public void setRating(Integer rating) {
                this.rating = rating;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

        }

    }
}