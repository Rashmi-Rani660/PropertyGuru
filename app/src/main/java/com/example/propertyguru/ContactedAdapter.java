package com.example.propertyguru;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactedAdapter extends RecyclerView.Adapter<ContactedAdapter.ViewHolder> {

    private List<String> contactedList;

    public ContactedAdapter(List<String> contactedList) {
        this.contactedList = contactedList;
    }

    @NonNull
    @Override
    public ContactedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contacted, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactedAdapter.ViewHolder holder, int position) {
        String item = contactedList.get(position);
        holder.title.setText(item);
    }

    @Override
    public int getItemCount() {
        return contactedList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.contactedTitle);
        }
    }
}
