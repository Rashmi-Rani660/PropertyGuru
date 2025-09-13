package com.example.propertyguru;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AllActivityAdapter extends RecyclerView.Adapter<AllActivityAdapter.ViewHolder> {

    private Context context;
    private List<AllActivityModel> listingList;

    public AllActivityAdapter(Context context, List<AllActivityModel> listingList) {
        this.context = context;
        this.listingList = listingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllActivityModel model = listingList.get(position);

        holder.propertyTitle.setText(model.getTitle());
        holder.propertyLocation.setText(model.getLocation());
        holder.propertyType.setText(model.getType());
        holder.propertyPrice.setText(model.getPrice());
        holder.developerName.setText(model.getDeveloper());
        holder.propertyImage.setImageResource(model.getImageResId());
    }

    @Override
    public int getItemCount() {
        return listingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView propertyImage;
        TextView propertyTitle, propertyLocation, propertyType, propertyPrice, developerName;
        Button viewNumberButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            propertyImage = itemView.findViewById(R.id.propertyImage);
            propertyTitle = itemView.findViewById(R.id.propertyTitle);
            propertyLocation = itemView.findViewById(R.id.propertyLocation);
            propertyType = itemView.findViewById(R.id.propertyType);
            propertyPrice = itemView.findViewById(R.id.propertyPrice);
            developerName = itemView.findViewById(R.id.developerName);
            viewNumberButton = itemView.findViewById(R.id.viewNumberButton);
        }
    }
}
