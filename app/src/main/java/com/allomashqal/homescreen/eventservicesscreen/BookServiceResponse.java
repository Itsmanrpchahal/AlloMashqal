package com.allomashqal.homescreen.eventservicesscreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookServiceResponse {

@SerializedName("success")
@Expose
private Boolean success;
@SerializedName("data")
@Expose
private List<Object> data = null;
@SerializedName("message")
@Expose
private String message;

public Boolean getSuccess() {
return success;
}

public void setSuccess(Boolean success) {
this.success = success;
}

public List<Object> getData() {
return data;
}

public void setData(List<Object> data) {
this.data = data;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

}