package com.byandev.trackadmin.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.byandev.trackadmin.Api.ApiEndPoint;
import com.byandev.trackadmin.Api.SharedPrefManager;
import com.byandev.trackadmin.Api.UtilsApi;
import com.byandev.trackadmin.Models.CreateRekomModel;
import com.byandev.trackadmin.Models.TypeModel;
import com.byandev.trackadmin.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRekomActivity extends AppCompatActivity  {

  private Context context;
  private ApiEndPoint mApiServices;
  SharedPrefManager sharedPrefManager;

  private EditText nama, alamat, etLat, etLng, rating;
  String sType;
  Integer iType;
  private Button cekAdd;
  private ProgressBar loading;
  private Spinner spType;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_rekom);

    context = this;
    mApiServices = UtilsApi.getAPIService();
    sharedPrefManager = new SharedPrefManager(context);

    loading = findViewById(R.id.loading);

    nama= findViewById(R.id.etNamaRekom);
    alamat = findViewById(R.id.etAlamat);
    etLat = findViewById(R.id.etKoordinatLat);
    etLng = findViewById(R.id.etKoordinatLng);
    rating = findViewById(R.id.etRating);
    spType = findViewById(R.id.spRole);

    cekAdd = findViewById(R.id.btnAddUser);
    initSp();

//    21.424964, 39.829828
  }

  @Override
  public void onStart(){
    super.onStart();
    listener();
  }

  private void listener() {
    cekAdd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (TextUtils.isEmpty(nama.getText())) {
          nama.setError("Wajib diisi");
        } else if (TextUtils.isEmpty(alamat.getText())) {
          alamat.setError("Wajibdiisi");
        } else if (TextUtils.isEmpty(etLat.getText())) {
          etLat.setError("Wajib diisi");
        } else if (TextUtils.isEmpty(etLng.getText())) {
          etLng.setError("Wajib diisi");
        } else if (TextUtils.isEmpty(rating.getText())) {
          rating.setError("Wajib diisi");
        } else {
          loading.setVisibility(View.VISIBLE);
          cekAdd.setVisibility(View.GONE);
          createRekom();
        }
      }
    });
  }

  private void initSp() {
    mApiServices.type().enqueue(new Callback<TypeModel>() {
      @Override
      public void onResponse(Call<TypeModel> call, Response<TypeModel> response) {
        if (response.isSuccessful()) {
          if (response.body().getApiStatus() == 1) {
            if (response.body().getData() != null) {
              final List<TypeModel.Typed> typeds = response.body().getData();
              final List<String> strings = new ArrayList<>();
              for (int i = 0; i < typeds.size(); i++) {
                strings.add(typeds.get(i).getTypeRekom());
              }
              ArrayAdapter<String> adapter =  new ArrayAdapter<>(context, R.layout.spinner_item_2, strings);
              spType.setAdapter(adapter);
              int source = adapter.getPosition(sType);
              spType.setSelection(source);
              spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  iType = typeds.get(position).getId();
                  Log.d("id type : ", String.valueOf(iType));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
              });
            }
          }
        }
      }

      @Override
      public void onFailure(Call<TypeModel> call, Throwable t) {

      }
    });
  }

  @Override
  public void onResume(){
    super.onResume();
  }

  private void createRekom() {
    mApiServices.recomAcc(
        nama.getText().toString(),
        alamat.getText().toString(),
        etLat.getText().toString(),
        etLng.getText().toString(),
        rating.getText().toString(), iType
    ).enqueue(new Callback<CreateRekomModel>() {
      @Override
      public void onResponse(Call<CreateRekomModel> call, Response<CreateRekomModel> response) {
        if (response.isSuccessful()) {
          if (response.body().getApiStatus() == 1) {
            Intent a = new Intent(context, HomeActivity.class);
            context.startActivity(a);
            finish();
            Toast.makeText(context, "Berhasil menambah account", Toast.LENGTH_LONG).show();
          } else {
            loading.setVisibility(View.GONE);
            Toast.makeText(context, "Gagal membuat account", Toast.LENGTH_SHORT).show();
          }
        } else {
          loading.setVisibility(View.GONE);
          Toast.makeText(context, "Gagal kesalahan jaringan", Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<CreateRekomModel> call, Throwable t) {
        loading.setVisibility(View.GONE);
        Toast.makeText(context, "Gagal kesalahan server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }
  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }

}
