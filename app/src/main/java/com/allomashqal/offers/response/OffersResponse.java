package com.allomashqal.offers.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OffersResponse {

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

        @SerializedName("offers")
        @Expose
        private List<Offer> offers = null;

        public List<Offer> getOffers() {
            return offers;
        }

        public void setOffers(List<Offer> offers) {
            this.offers = offers;
        }


        @SerializedName("time_list")
        @Expose
        private List<String> timeList = null;

        public List<String> getTimeList() {
            return timeList;
        }

        public void setTimeList(List<String> timeList) {
            this.timeList = timeList;
        }

        public class Offer {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("provider_name")
            @Expose
            private String providerName;
            @SerializedName("price")
            @Expose
            private String price;
            @SerializedName("start_date")
            @Expose
            private String startDate;
            @SerializedName("end_date")
            @Expose
            private String endDate;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("latitude")
            @Expose
            private String latitude;
            @SerializedName("longitude")
            @Expose
            private String longitude;
            @SerializedName("provider_id")
            @Expose
            private String provider_id;

            public String getProvider_id() {
                return provider_id;
            }

            public void setProvider_id(String provider_id) {
                this.provider_id = provider_id;
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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getProviderName() {
                return providerName;
            }

            public void setProviderName(String providerName) {
                this.providerName = providerName;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

        }
    }
}