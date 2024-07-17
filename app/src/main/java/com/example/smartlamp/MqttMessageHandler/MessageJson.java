package com.example.smartlamp.MqttMessageHandler;

public class MessageJson {
    public MessageJson(){}
    public MessageJson(String devId, String data, String Purpose) {
        this.devID = devId;
        this.Message = data;
        this.Purpose = Purpose;
    }
    public String getDevID() {
        return devID;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String purpose) {
        Purpose = purpose;
    }

    String devID = "";
    String Message = "";

    String Purpose = "";
}
