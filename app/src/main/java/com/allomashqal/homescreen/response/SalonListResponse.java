package com.allomashqal.homescreen.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalonListResponse {

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

        @SerializedName("total_page")
        @Expose
        private Integer totalPage;
        @SerializedName("list")
        @Expose
        private java.util.List<List> list = null;
        @SerializedName("img_base_url")
        @Expose
        private String imgBaseUrl;

        public Integer getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(Integer totalPage) {
            this.totalPage = totalPage;
        }

        public java.util.List<List> getList() {
            return list;
        }

        public void setList(java.util.List<List> list) {
            this.list = list;
        }

        public String getImgBaseUrl() {
            return imgBaseUrl;
        }

        public void setImgBaseUrl(String imgBaseUrl) {
            this.imgBaseUrl = imgBaseUrl;
        }

        public class List {

            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("service_type")
            @Expose
            private String serviceType;
            @SerializedName("address")
            @Expose
            private Object address;
            @SerializedName("image")
            @Expose
            private Object image;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getServiceType() {
                return serviceType;
            }

            public void setServiceType(String serviceType) {
                this.serviceType = serviceType;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public Object getImage() {
                return image;
            }

            public void setImage(Object image) {
                this.image = image;
            }

        }
    }

}