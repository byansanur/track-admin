package com.byandev.trackadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.byandev.trackadmin.Main.DetailUsersActivity;
import com.byandev.trackadmin.Models.UserListModel;
import com.byandev.trackadmin.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterListUsers extends RecyclerView.Adapter<AdapterListUsers.Holder> {

  private ArrayList<UserListModel.Users> users;
  private Context context;

  public AdapterListUsers(Context context, List<UserListModel.Users> users1) {
    this.context = context;
    this.users = (ArrayList<UserListModel.Users>) users1;
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, null);
    return new Holder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    final UserListModel.Users model = users.get(position);
    holder.nama.setText(model.getNama());
    holder.nohp.setText("+62 "+model.getNoHp());
    holder.username.setText(model.getUsername());
    holder.cardView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // intent detail
        Intent a = new Intent(context, DetailUsersActivity.class);
        a.putExtra("id", model.getId());
        context.startActivity(a);
      }
    });
  }

  @Override
  public int getItemCount() {
    return users.size();
  }

  public class Holder extends RecyclerView.ViewHolder {
    TextView nama, username, nohp;
    CardView cardView;
    public Holder(@NonNull View itemView) {
      super(itemView);
      nama = itemView.findViewById(R.id.tvNama);
      username = itemView.findViewById(R.id.tvUserName);
      nohp = itemView.findViewById(R.id.tvNoHp);
      cardView = itemView.findViewById(R.id.card);
    }
  }
}
