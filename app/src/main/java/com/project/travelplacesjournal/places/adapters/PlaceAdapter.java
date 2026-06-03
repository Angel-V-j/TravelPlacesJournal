package com.project.travelplacesjournal.places.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.travelplacesjournal.R;
import com.project.travelplacesjournal.data.entities.Place;

import java.util.List;

public class PlaceAdapter
        extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>{
    private final List<Place> places;

    public PlaceAdapter(List<Place> places) {
        this.places = places;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_place,
                        parent,
                        false
                );

        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PlaceViewHolder holder,
            int position) {

        Place place = places.get(position);

        holder.tvName.setText(place.getName());

        holder.tvDescription.setText(
                place.getDescription()
        );

        holder.tvDate.setText(
                "Visited: " + place.getVisitDate()
        );

        holder.ratingBar.setRating(
                place.getRating()
        );
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    static class PlaceViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvDescription;
        TextView tvDate;
        RatingBar ratingBar;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName =
                    itemView.findViewById(R.id.tvName);

            tvDescription =
                    itemView.findViewById(R.id.tvDescription);

            tvDate =
                    itemView.findViewById(R.id.tvDate);

            ratingBar =
                    itemView.findViewById(R.id.ratingBarItem);
        }
    }
}
