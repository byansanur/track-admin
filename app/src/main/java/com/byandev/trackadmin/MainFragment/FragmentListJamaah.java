package com.byandev.trackadmin.MainFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.byandev.trackadmin.Adapter.AdapterListUsers;
import com.byandev.trackadmin.Api.ApiEndPoint;
import com.byandev.trackadmin.Api.SharedPrefManager;
import com.byandev.trackadmin.Api.UtilsApi;
import com.byandev.trackadmin.Models.UserListModel;
import com.byandev.trackadmin.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentListJamaah extends Fragment {

  private Context context;
  private ApiEndPoint mApiServices;
  SharedPrefManager sharedPrefManager;

  private RecyclerView recyclerView;
  private ProgressBar progress;

  private ArrayList<UserListModel.Users> users;
  private AdapterListUsers adapter;

  private Integer offset = 15, limit;
  private boolean itShouldLoadMore = true;

  public FragmentListJamaah() {}

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle s) {
    final View view = inflater.inflate(R.layout.fragment_user_list, container, false);
    context = getContext();
    sharedPrefManager = new SharedPrefManager(getContext());
    mApiServices = UtilsApi.getAPIService();
    limit = 15;

    recyclerView = view.findViewById(R.id.recyclerView);
    recyclerView.setHasFixedSize(true);
    progress = view.findViewById(R.id.progress);

    users = new ArrayList<>();
    adapter = new AdapterListUsers(context, users);
    LinearLayoutManager llm = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(llm);
    recyclerView.setAdapter(adapter);
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) {
          if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
            if (itShouldLoadMore) {
              progress.setVisibility(View.VISIBLE);
              LoadMore();
            }
          }
        }
      }
    });
    return view;
  }
  @Override
  public void onResume(){
    super.onResume();
    users.clear();
    adapter.notifyDataSetChanged();
    progress.setVisibility(View.VISIBLE);
    firstLoad();
  }

  @Override
  public void onStart(){
    super.onStart();
  }

  private void firstLoad() {
    itShouldLoadMore = false;
    mApiServices.usersAll(3, limit, 0).enqueue(new Callback<UserListModel>() {
      @Override
      public void onResponse(Call<UserListModel> call, Response<UserListModel> response) {
        if (response.isSuccessful()) {
          itShouldLoadMore = true;
          if (response.body().getData() != null) {
            List<UserListModel.Users> mod = response.body().getData();
            users.addAll(mod);
            adapter.notifyDataSetChanged();
            if (users.size() >= 1) {
              progress.setVisibility(View.INVISIBLE);
            } else {
              progress.setVisibility(View.INVISIBLE);
            }
            progress.setVisibility(View.GONE);
          }
        }
      }

      @Override
      public void onFailure(Call<UserListModel> call, Throwable t) {
        itShouldLoadMore = true;
        progress.setVisibility(View.GONE);
        Toast.makeText(context, "message " + t.getMessage(), Toast.LENGTH_LONG).show();
      }
    });
  }

  private void LoadMore() {
    itShouldLoadMore=false;
    mApiServices.usersAll(3,limit,offset).enqueue(new Callback<UserListModel>() {
      @Override
      public void onResponse(Call<UserListModel> call, Response<UserListModel> response) {
        if (response.isSuccessful()) {
          itShouldLoadMore = true;
          if (response.body().getData() != null) {
            List<UserListModel.Users> mod = response.body().getData();
            users.addAll(mod);
            adapter.notifyDataSetChanged();
            int index = users.size();
            offset = index;
            progress.setVisibility(View.GONE);
          }
        }
      }

      @Override
      public void onFailure(Call<UserListModel> call, Throwable t) {
        itShouldLoadMore = true;
        progress.setVisibility(View.GONE);
        Toast.makeText(context, "message " + t.getMessage(), Toast.LENGTH_LONG).show();
      }
    });
  }
}
