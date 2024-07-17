package com.example.smartlamp.MqttMessageHandler;


import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.nio.charset.StandardCharsets;

public class MqttPushMessageHandler{
    private MqttPushMessageInterface PushMessage;
    public MqttPushMessageHandler(MqttPushMessageInterface pushMessage){
        this.PushMessage = pushMessage;
    }
    public void PushMeesage(MqttClient client, String topic, String Message, String Purpose){
        this.PushMessage.PushMessage(topic, Message);
        new Thread(()->{
            try {
                if(client.isConnected()){
                    MessageJson messageJson = new MessageJson(topic, Message, Purpose);
                    Gson gson = new Gson();
                    MqttMessage message = new MqttMessage();
                    message.setPayload(gson.toJson(messageJson).getBytes(StandardCharsets.UTF_8));
                    client.publish(topic, message);
                }
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}



