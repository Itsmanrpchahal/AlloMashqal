package com.allomashqal.chat.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatResponse {

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

        @SerializedName("chat")
        @Expose
        private List<Chat> chat = null;
        @SerializedName("reciever_detail")
        @Expose
        private RecieverDetail recieverDetail;

        public List<Chat> getChat() {
            return chat;
        }

        public void setChat(List<Chat> chat) {
            this.chat = chat;
        }

        public RecieverDetail getRecieverDetail() {
            return recieverDetail;
        }

        public void setRecieverDetail(RecieverDetail recieverDetail) {
            this.recieverDetail = recieverDetail;
        }

        public class Chat {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("sender_id")
            @Expose
            private String senderId;
            @SerializedName("reciever_id")
            @Expose
            private String recieverId;
            @SerializedName("date_time")
            @Expose
            private String dateTime;
            @SerializedName("message")
            @Expose
            private String message;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSenderId() {
                return senderId;
            }

            public void setSenderId(String senderId) {
                this.senderId = senderId;
            }

            public String getRecieverId() {
                return recieverId;
            }

            public void setRecieverId(String recieverId) {
                this.recieverId = recieverId;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

        }

        public class RecieverDetail {

            @SerializedName("name")
            @Expose
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

        }

    }
}