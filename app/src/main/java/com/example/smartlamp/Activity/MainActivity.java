package com.example.smartlamp.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlamp.Adapter.ButtonFunctionViewAdapter;
import com.example.smartlamp.MqttMessageHandler.MessageJson;
import com.example.smartlamp.MqttMessageHandler.MqttMessageHandler;
import com.example.smartlamp.MqttMessageHandler.MqttPushMessageHandler;
import com.example.smartlamp.R;
import com.example.smartlamp.RcListInfo.ButtonFunctionViewInfo.ButtonFunctionViewInfo;
import com.example.smartlamp.ToastLogShow.ToastLogShow;
import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{
    RecyclerView.Adapter adapter;
    RecyclerView buttonFunctionRcView;

    public Button reConnectBtn, homeBtn, customBtn, restartBtn, lockBtn;
    public TextView tempValueTv, humiValueTv, lightValueTv;
    MqttClient  client = new MqttClient(MainActivity.clientUrl, MainActivity.clientID, new MemoryPersistence());

    MqttConnectOptions conOpt = new MqttConnectOptions();
    MqttMessageHandler  messageHandler = new MqttMessageHandler(MainActivity.this::MessageNotice);
    MqttPushMessageHandler pushMessager = new MqttPushMessageHandler(MainActivity.this::pushMessage);
    ArrayList<ButtonFunctionViewInfo> items = new ArrayList<>();
    static String clientUrl = "tcp://49.232.246.95:1884";
    static String clientID = "AleApps";
    static String UserName = "admin";
    static String UserPassWord = "admin";
    static String PushTopic = "AlesDev";

    public MainActivity() throws MqttException {}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        this.SrcWindowsShowInit();
        this.PageViewInit();
        this.ViewButtonClickHandler();
        this.reConnectBtn.callOnClick();
    }
    public void ViewButtonClickHandler() {
        this.reConnectBtn.setOnClickListener(view -> {
            this.InitMqtt();
        });
        this.homeBtn.setOnClickListener(view -> {
            pushMessager.PushMeesage(client, clientID, "home", "Set");
        });
        this.customBtn.setOnClickListener(view -> {
            pushMessager.PushMeesage(client, clientID, "custom","Set");
        });
        this.restartBtn.setOnClickListener(view -> {
            pushMessager.PushMeesage(client, clientID, "restart","Set");
        });
        this.lockBtn.setOnClickListener(view -> {
            pushMessager.PushMeesage(client, clientID, "lock","Set");
        });
    }
    void InitMqtt() {
        new Thread(() -> {
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))) {
                    if(!client.isConnected()){
                        conOpt.setUserName(MainActivity.UserName);
                        conOpt.setPassword(MainActivity.UserPassWord.toCharArray());
                        conOpt.setCleanSession(true);
                        conOpt.setKeepAliveInterval(20);
                        conOpt.setConnectionTimeout(10);
                        client.setCallback(messageHandler);
                        client.connect(conOpt);
                        client.subscribe(PushTopic, 1);
                    }else{
                        runOnUiThread(()->{
                            ToastLogShow.showToast(MainActivity.this, "已经连接成功啦🤩");
                        });
                    }
                } else {
                    MessageNotice("", "设备没有连接到网络😀");
                }
            } catch (MqttException e) {
                MessageNotice("", "异常😀");
            }
        }).start();
    }

    @SuppressLint("SetTextI18n")
    void MessageNotice(String Topic, String Message) {
        runOnUiThread(()->{
            try {
                Gson gson = new Gson();
                MessageJson messageJson  = gson.fromJson(Message, MessageJson.class);
                if(messageJson.getPurpose().equals("Upload")){
                    String[] dataArray = messageJson.getMessage().split(",");
                    this.tempValueTv.setText(dataArray[0] + "℃");
                    this.humiValueTv.setText(dataArray[1] + "%");
                    this.lightValueTv.setText(dataArray[2] + "lux");
                }

            }catch (Exception e){
                ToastLogShow.showToast(MainActivity.this, e.getMessage());
            }
        });
    }
    void pushMessage(String topic, String mess){
        runOnUiThread(()->{
            ToastLogShow.showToast(MainActivity.this, client.isConnected() ? "发送成功🥳" : "发送失败😁");
        });
    }
    void PageViewInit(){
        reConnectBtn = findViewById(R.id.reConnectBtn);
        homeBtn = findViewById(R.id.homeBtn);
        customBtn = findViewById(R.id.customBtn);
        restartBtn = findViewById(R.id.restartBtn);
        lockBtn = findViewById(R.id.lockBtn);
        tempValueTv = findViewById(R.id.tempValueTv);
        humiValueTv = findViewById(R.id.humiValueTv);
        lightValueTv = findViewById(R.id.lightValueTv);
        buttonFunctionRcView = findViewById(R.id.ButtonFunctionRcView);
        items.add(new ButtonFunctionViewInfo("冷光源", "打开/关闭", "btnlamp", ()->{
            pushMessager.PushMeesage(client, clientID, "cold","Set");}));
        items.add(new ButtonFunctionViewInfo("暖光源", "打开/关闭", "btnlamp", ()->{
            pushMessager.PushMeesage(client, clientID, "warm","Set");}));
        items.add(new ButtonFunctionViewInfo("红光源", "打开/关闭", "red", ()->{
            pushMessager.PushMeesage(client, clientID, "red","Set");}));
        items.add(new ButtonFunctionViewInfo("彩虹", "打开/关闭", "rainbow", ()->{
            pushMessager.PushMeesage(client, clientID, "randow","Set");}));
        buttonFunctionRcView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new ButtonFunctionViewAdapter(items);
        buttonFunctionRcView.setAdapter(adapter);
    }
    void SrcWindowsShowInit(){
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}