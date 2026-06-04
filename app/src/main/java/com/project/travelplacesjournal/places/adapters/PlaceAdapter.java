package com.project.travelplacesjournal.places.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.travelplacesjournal.R;
import com.project.travelplacesjournal.data.database.AppDatabase;
import com.project.travelplacesjournal.data.database.DatabaseProvider;
import com.project.travelplacesjournal.data.entities.Place;
import com.project.travelplacesjournal.data.entities.PlaceImage;
import com.project.travelplacesjournal.places.EditPlaceActivity;
import com.project.travelplacesjournal.utils.ImageUtils;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>{
    private final Context context;
    private final List<Place> places;
    public PlaceAdapter(Context context, List<Place> places) {
        this.context = context;
        this.places = places;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_place,
                        parent, false);

        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PlaceViewHolder holder,
            int position) {

        Place place = places.get(position);
        holder.layoutImages.removeAllViews();
        holder.tvName.setText(place.getName());
        holder.tvDescription.setText(place.getDescription());
        holder.tvDate.setText("Visited: " + place.getVisitDate());
        holder.ratingBar.setRating(place.getRating());
        AppDatabase db = DatabaseProvider.getDatabase(context);

        List<PlaceImage> images = db.placeImageDao()
                                    .getByPlaceId(place.getId());

        for(PlaceImage image : images){
            Uri uri = Uri.parse(image.getImageUri());
            ImageView preview = ImageUtils.createImagePreview(
                            holder.itemView.getContext(),
                            uri);

            holder.layoutImages.addView(preview);
        }

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditPlaceActivity.class);
            intent.putExtra("PLACE_ID", place.getId());
            context.startActivity(intent);
        });
        holder.btnDelete.setOnClickListener(v -> {
            db.placeDao().delete(place);
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                places.remove(currentPosition);
                notifyItemRemoved(currentPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    static class PlaceViewHolder
            extends RecyclerView.ViewHolder {
        LinearLayout layoutImages;
        TextView tvName;
        TextView tvDescription;
        TextView tvDate;
        RatingBar ratingBar;
        Button btnEdit;
        Button btnDelete;

        public PlaceViewHolder(
                @NonNull View itemView) {
            super(itemView);

            layoutImages = itemView.findViewById(R.id.layoutImages);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
            ratingBar = itemView.findViewById(R.id.ratingBarItem);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
