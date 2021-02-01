package com.example.servicehub;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ServiceProviderListRecyclerViewHolder extends RecyclerView.ViewHolder {


    private ImageView view;
    private TextView label;
    public ServiceProviderListRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView.findViewById(R.id.serviceProviderImageView);
        label = itemView.findViewById(R.id.serviceProviderTextView);
    }

    public ImageView getView(){
        return view;
    }

    public TextView getLabel() {return label; }

}
