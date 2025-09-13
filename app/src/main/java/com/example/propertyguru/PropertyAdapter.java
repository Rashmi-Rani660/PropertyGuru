package com.example.propertyguru;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {

    private Context context;
    private List<PropertyModel> propertyList;

    public PropertyAdapter(Context context, List<PropertyModel> propertyList) {
        this.context = context;
        this.propertyList = propertyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_property, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PropertyModel property = propertyList.get(position);
        holder.title.setText(property.getTitle());
        holder.price.setText("₹" + property.getPrice());
        holder.location.setText(property.getLocation());

    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, location, price;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.propertyTitle);
            location = itemView.findViewById(R.id.propertyLocation);
            price = itemView.findViewById(R.id.propertyPrice);
            image = itemView.findViewById(R.id.propertyImage);
        }
    }
}
