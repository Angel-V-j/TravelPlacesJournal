package com.project.travelplacesjournal.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.travelplacesjournal.R;
import com.project.travelplacesjournal.data.entities.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener{
        void onEditClick(Category category);
        void onDeleteClick(Category category);
    }
    public CategoryAdapter(List<Category> categoryList, OnCategoryClickListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }
    public void updateList(List<Category> newList) {
        this.categoryList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.tvName.setText(category.getName());
        holder.btnEdit.setOnClickListener(v -> listener.onEditClick(category));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(category));
    }

    @Override
    public int getItemCount() { return categoryList != null ? categoryList.size() : 0; }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageButton btnEdit, btnDelete;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCategoryName);
            btnEdit = itemView.findViewById(R.id.btnEditCategory);
            btnDelete = itemView.findViewById(R.id.btnDeleteCategory);
        }
    }
}
