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

public class RespondentAdapter extends RecyclerView.Adapter<RespondentAdapter.ViewHolder> {

    private List<RespondentModel> list;

    public RespondentAdapter(List<RespondentModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_respondent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RespondentModel model = list.get(position);
        holder.tvName.setText(model.name);
        holder.tvDate.setText(model.date);
        holder.tvLeadScore.setText(model.leadScore);
        holder.tvDetails.setText(model.details);
        holder.tvListingType.setText(model.listingType);
        holder.tvViewDetail.setText(model.viewDetail);

        // ✅ Open LeadDetailsFragment on click
        holder.tvViewDetail.setOnClickListener(v -> {
            Fragment fragment = new com.example.propertyguru.LeadDetailsFragment();

            FragmentTransaction transaction = ((AppCompatActivity) v.getContext())
                    .getSupportFragmentManager()
                    .beginTransaction();

            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

            // Show fragment container, hide respondent recycler
            View container = ((AppCompatActivity) v.getContext()).findViewById(R.id.fragmentContainer);
            if (container != null) container.setVisibility(View.VISIBLE);

            RecyclerView recycler = ((AppCompatActivity) v.getContext()).findViewById(R.id.recyclerRespondents);
            if (recycler != null) recycler.setVisibility(View.GONE);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate, tvLeadScore, tvDetails, tvListingType, tvViewDetail;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvLeadScore = itemView.findViewById(R.id.tvLeadScore);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvListingType = itemView.findViewById(R.id.tvListingType);
            tvViewDetail = itemView.findViewById(R.id.tvViewDetail); // This must exist in item_respondent.xml
        }
    }
}
