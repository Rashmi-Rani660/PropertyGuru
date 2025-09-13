package com.example.propertyguru;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.ResponseViewHolder> {

    List<ResponseModel> list;

    public ResponseAdapter(List<ResponseModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ResponseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.response_item, parent, false);
        return new ResponseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResponseViewHolder holder, int position) {
        ResponseModel model = list.get(position);
        holder.tvName.setText(model.name);
        holder.tvDate.setText(model.date);
        holder.tvLeadScore.setText("Lead Score: " + model.score);
        holder.tvUserType.setText(model.type);
        holder.tvDetails.setText(model.details);
        holder.tvListingType.setText(model.listing);

        // Handle "View Lead Detail" click
        holder.tvViewDetail.setOnClickListener(v -> {
            Fragment fragment = new com.example.propertyguru.LeadDetailsFragment();

            FragmentTransaction transaction = ((AppCompatActivity) v.getContext())
                    .getSupportFragmentManager()
                    .beginTransaction();

            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

            // Show fragment container, hide RecyclerView
            View container = ((AppCompatActivity) v.getContext()).findViewById(R.id.fragmentContainer);
            if (container != null) {
                container.setVisibility(View.VISIBLE);
            }

            RecyclerView recycler = ((AppCompatActivity) v.getContext()).findViewById(R.id.recyclerResponses);
            if (recycler != null) {
                recycler.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ResponseViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate, tvLeadScore, tvUserType, tvDetails, tvListingType, tvViewDetail;

        public ResponseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvLeadScore = itemView.findViewById(R.id.tvLeadScore);
            tvUserType = itemView.findViewById(R.id.tvUserType);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvListingType = itemView.findViewById(R.id.tvListingType);
            tvViewDetail = itemView.findViewById(R.id.tvViewDetail); // ✅ This must exist in response_item.xml
        }
    }
}
