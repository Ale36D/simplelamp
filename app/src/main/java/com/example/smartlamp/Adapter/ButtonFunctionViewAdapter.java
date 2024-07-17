package com.example.smartlamp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.smartlamp.R;
import com.example.smartlamp.RcListInfo.ButtonFunctionViewInfo.ButtonFunctionViewInfo;

import java.util.ArrayList;

public class ButtonFunctionViewAdapter extends RecyclerView.Adapter<ButtonFunctionViewAdapter.viewHolder> {
    ArrayList<ButtonFunctionViewInfo> info;
    Context context;
    public ButtonFunctionViewAdapter(ArrayList<ButtonFunctionViewInfo> items) {
        this.info = items;
    }
    @NonNull
    @Override
    public ButtonFunctionViewAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.buttonframelayout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonFunctionViewAdapter.viewHolder holder, int position) {
        holder.TitleName_tv.setText(info.get(position).getTitleName());
        holder.Function_Btn.setText(info.get(position).getButtonName());
        holder.Function_Btn.setOnClickListener(view -> {
            info.get(position).RunEvent();
        });
        int lottiePath = holder.itemView.getResources().
                getIdentifier(info.get(position).getLottiePath(),
                        "raw",
                        holder.itemView.getContext().getPackageName());
        holder.lottieView.setAnimation(lottiePath);
        holder.lottieView.playAnimation();
    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView TitleName_tv;
        Button Function_Btn;
        LottieAnimationView lottieView;
        int ButtonID;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            TitleName_tv = itemView.findViewById(R.id.function_tv);
            Function_Btn = itemView.findViewById(R.id.function_btn);
            lottieView = itemView.findViewById(R.id.function_lottie);
        }
    }
}
