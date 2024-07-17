package com.example.smartlamp.MqttMessageHandler;

import android.content.Context;
import android.util.Log;

import com.example.smartlamp.ToastLogShow.ToastLogShow;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Arrays;
public class MqttMessageHandler implements MqttCallbackExtended {
    String Message = "";
    String TopicName = "";
    private MqttMessageNotice messageNotice;
    public MqttMessageHandler(MqttMessageNotice messageNotice){

        this.messageNotice = messageNotice;
    }
    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String topicName) {
        TopicName = topicName;
    }
    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public void connectComplete(boolean b, String s) {
        messageNotice.MessageNotice("Connect", "连接成功");
    }
    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String topicName, MqttMessage mqttMessage) throws Exception {
        try {
            this.setTopicName(topicName);
            this.setMessage(new String(mqttMessage.getPayload()));
            messageNotice.MessageNotice(this.getTopicName(), this.getMessage());
        }catch (Exception ignored){
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
