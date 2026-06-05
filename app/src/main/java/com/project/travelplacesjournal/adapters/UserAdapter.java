package com.project.travelplacesjournal.adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.travelplacesjournal.R;
import com.project.travelplacesjournal.data.entities.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private final List<User> userList;
    private final OnUserBlockClickListener listener;

    public interface OnUserBlockClickListener {
        void onBlockClick(User user);
    }

    public UserAdapter(List<User> userList, OnUserBlockClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    public void updateList(List<User> newList) {
        this.userList.clear();
        this.userList.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user= userList.get(position);

        String fullName = user.getFirstName() + " " + user.getLastName();
        holder.tvFullName.setText(fullName);
        holder.tvEmail.setText(user.getEmail());

        if (user.isBlocked()) {
            holder.btnBlock.setText("Отблокирай");
            holder.btnBlock.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#28A745"))); // Зелено
        } else {
            holder.btnBlock.setText("Блокирай");
            holder.btnBlock.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DC3545"))); // Червено
        }

        if (user.isAdmin()) {
            holder.btnBlock.setVisibility(View.GONE);
        } else {
            holder.btnBlock.setVisibility(View.VISIBLE);
            holder.btnBlock.setOnClickListener(v -> listener.onBlockClick(user));
        }
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvEmail;
        Button btnBlock;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.tvUserFullName);
            tvEmail = itemView.findViewById(R.id.tvUserEmail);
            btnBlock = itemView.findViewById(R.id.btnBlockUnblock);
        }
    }
}
