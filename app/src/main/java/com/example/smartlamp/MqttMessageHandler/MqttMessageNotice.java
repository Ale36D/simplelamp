package com.example.smartlamp.MqttMessageHandler;
public interface MqttMessageNotice {
    void MessageNotice(String Topic, String Message);
}