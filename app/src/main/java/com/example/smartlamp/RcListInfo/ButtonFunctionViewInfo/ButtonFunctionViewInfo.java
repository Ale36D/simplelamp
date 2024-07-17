package com.example.smartlamp.RcListInfo.ButtonFunctionViewInfo;

import android.view.View;

public class ButtonFunctionViewInfo {
    public ButtonFunctionViewInfo(String titleName, String buttonName, String lottiePath, ButtonFunctionEventInterface eventInterface) {
        this.TitleName = titleName;
        this.ButtonName = buttonName;
        this.LottiePath = lottiePath;
        this.eventInterface = eventInterface;
    }
    public void RunEvent(){
        this.eventInterface.ButtonEventFunction();
    }
    public void setButtonViewID(int buttonID){
        this.ButtonID = buttonID;
    }
    public int getButtonID(){
        return this.ButtonID;
    }
    public String getTitleName() {
        return  this.TitleName;
    }

    public String getButtonName() {
        return  this.ButtonName;
    }

    public String getLottiePath() {
        return  this.LottiePath;
    }

    public void setTitleName(String titleName) {
        this.TitleName = titleName;
    }

    public void setButtonName(String buttonName) {
        this.ButtonName = buttonName;
    }

    public void setLottiePath(String lottiePath) {
        this.LottiePath = lottiePath;
    }

    private String TitleName;
    private String ButtonName;
    private String LottiePath;

    private final ButtonFunctionEventInterface eventInterface;

    int ButtonID;



}
