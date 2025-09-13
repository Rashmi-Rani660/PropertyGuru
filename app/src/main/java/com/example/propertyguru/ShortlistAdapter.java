package com.example.propertyguru;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShortlistAdapter extends RecyclerView.Adapter<ShortlistAdapter.ViewHolder> {

    private List<String> propertyList;

    public ShortlistAdapter(List<String> propertyList) {
        this.propertyList = propertyList;
    }

    @NonNull
    @Override
    public ShortlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shortlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShortlistAdapter.ViewHolder holder, int position) {
        String property = propertyList.get(position);
        holder.propertyName.setText(property);
    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView propertyName;

        public ViewHolder(View itemView) {
            super(itemView);
            propertyName = itemView.findViewById(R.id.propertyName);
        }
    }
}
