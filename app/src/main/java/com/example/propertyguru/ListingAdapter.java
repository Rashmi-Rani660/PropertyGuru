package com.example.propertyguru;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ListingModel> listings;
    private BannerType bannerType;

    private static final int TYPE_BANNER_VERIFIED = 0;
    private static final int TYPE_BANNER_NEW_LAUNCHES = 1;
    private static final int TYPE_BANNER_YOUR_CHOICE = 2;
    private static final int TYPE_ITEM = 3;

    public enum BannerType {
        NONE, VERIFIED, NEW_LAUNCHES, YOUR_CHOICE, PROJECTS, READY_TO_MOVE, OWNER
    }

    public ListingAdapter(Context context, List<ListingModel> listings, BannerType bannerType) {
        this.context = context;
        this.listings = listings;
        this.bannerType = bannerType;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            switch (bannerType) {
                case VERIFIED:
                    return TYPE_BANNER_VERIFIED;
                case NEW_LAUNCHES:
                    return TYPE_BANNER_NEW_LAUNCHES;
                case YOUR_CHOICE:
                    return TYPE_BANNER_YOUR_CHOICE;
                default:
                    return TYPE_ITEM;
            }
        }
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;

        switch (viewType) {
            case TYPE_BANNER_VERIFIED:
                view = inflater.inflate(R.layout.item_banner, parent, false);
                return new BannerViewHolder(view);
            case TYPE_BANNER_NEW_LAUNCHES:
                view = inflater.inflate(R.layout.item_new_launches_banner, parent, false);
                return new BannerViewHolder(view);
            case TYPE_BANNER_YOUR_CHOICE:
                view = inflater.inflate(R.layout.item_banner_choice, parent, false);
                return new BannerViewHolder(view);
            default:
                view = inflater.inflate(R.layout.item_listing, parent, false);
                return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        boolean hasBanner = (bannerType == BannerType.VERIFIED || bannerType == BannerType.NEW_LAUNCHES || bannerType == BannerType.YOUR_CHOICE);
        int dataPosition = hasBanner ? position - 1 : position;

        if (holder instanceof ItemViewHolder && dataPosition >= 0 && dataPosition < listings.size()) {
            ListingModel model = listings.get(dataPosition);
            ItemViewHolder itemHolder = (ItemViewHolder) holder;

            itemHolder.title.setText(model.getTitle());
            itemHolder.location.setText(model.getLocation());
            itemHolder.builder.setText(model.getBuilderName());
            itemHolder.possessionDate.setText("Possession by " + model.getPossessionDetails());
            itemHolder.bhk2Price.setText("2 BHK Apartment\n₹ " + model.getBhk2Price());
            itemHolder.bhk3Price.setText("3 BHK Apartment\n₹ " + model.getBhk3Price());

            // Load horizontal images
            HorizontalImageAdapter imageAdapter = new HorizontalImageAdapter(context, model.getImages()); // model.getImages() returns List<String>
             itemHolder.recyclerImages.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            itemHolder.recyclerImages.setAdapter(imageAdapter);

            // Toggle view number
            itemHolder.viewNumberBtn.setOnClickListener(v -> {
                String current = itemHolder.viewNumberBtn.getText().toString();
                itemHolder.viewNumberBtn.setText(current.equals("View Number") ? "8595986867" : "View Number");
            });

            // Call functionality
            itemHolder.callButton.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:8595986867"));

                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (context instanceof Activity) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                    }
                    return;
                }

                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        boolean hasBanner = (bannerType == BannerType.VERIFIED || bannerType == BannerType.NEW_LAUNCHES || bannerType == BannerType.YOUR_CHOICE);
        return listings.size() + (hasBanner ? 1 : 0);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView title, location, builder, possessionDate, bhk2Price, bhk3Price, viewNumberBtn;
        RecyclerView recyclerImages;
        ImageView callButton;

        public ItemViewHolder(View itemView) {
            super(itemView);
            recyclerImages = itemView.findViewById(R.id.recyclerImages);
            title = itemView.findViewById(R.id.propertyTitle);
            location = itemView.findViewById(R.id.propertyLocation);
            builder = itemView.findViewById(R.id.propertyBuilder);
            possessionDate = itemView.findViewById(R.id.possessionDate);
            bhk2Price = itemView.findViewById(R.id.bhk2Price);
            bhk3Price = itemView.findViewById(R.id.bhk3Price);
            viewNumberBtn = itemView.findViewById(R.id.viewNumberBtn);
            callButton = itemView.findViewById(R.id.callButton);
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        public BannerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
