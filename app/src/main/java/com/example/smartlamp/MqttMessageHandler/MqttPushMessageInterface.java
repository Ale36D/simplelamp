package com.example.smartlamp.MqttMessageHandler;

public interface MqttPushMessageInterface {
    void PushMessage(String topic,String Message);
}
