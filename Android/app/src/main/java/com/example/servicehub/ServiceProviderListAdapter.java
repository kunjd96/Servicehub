package com.example.servicehub;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

import java.util.Random;

public class ServiceProviderListAdapter extends RecyclerView.Adapter<ServiceProviderListRecyclerViewHolder> {
//    private Random random;
//
//    public ServiceProviderListAdapter(int seed) {
//        this.random = new Random(seed);
//    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.service_provider_list;
    }

    @NonNull
    @Override
    public ServiceProviderListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ServiceProviderListRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceProviderListRecyclerViewHolder holder, int position) {
//        holder.getView().setText(String.valueOf(random.nextInt()));
        switch (position){
            case 0: holder.getView().setImageResource(R.drawable.plumber);
                    holder.getLabel().setText("Plumber");
                break;
            case 1: holder.getView().setImageResource(R.drawable.cosmetics);
                    holder.getLabel().setText("Cosmetics");
                break;
            case 2: holder.getView().setImageResource(R.drawable.lamp);
                    holder.getLabel().setText("Electrician");
                break;
            case 3: holder.getView().setImageResource(R.drawable.analytics);
                    holder.getLabel().setText("Information tech ");
                break;
            default: holder.getView().setImageResource(R.drawable.plumber);
                    holder.getLabel().setText("Plumber");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}