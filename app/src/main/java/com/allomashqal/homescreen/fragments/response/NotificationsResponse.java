package com.allomashqal.homescreen.fragments.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationsResponse {

@SerializedName("success")
@Expose
private Boolean success;
@SerializedName("data")
@Expose
private List<Datum> data = null;
@SerializedName("message")
@Expose
private String message;

public Boolean getSuccess() {
return success;
}

public void setSuccess(Boolean success) {
this.success = success;
}

public List<Datum> getData() {
return data;
}

public void setData(List<Datum> data) {
this.data = data;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

    public class Datum {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("booking")
        @Expose
        private String booking;
        @SerializedName("total_bill")
        @Expose
        private String totalBill;
        @SerializedName("date")
        @Expose
        private String date;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBooking() {
            return booking;
        }

        public void setBooking(String booking) {
            this.booking = booking;
        }

        public String getTotalBill() {
            return totalBill;
        }

        public void setTotalBill(String totalBill) {
            this.totalBill = totalBill;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }
}