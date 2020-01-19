package com.byandev.trackadmin.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.byandev.trackadmin.Api.ApiEndPoint;
import com.byandev.trackadmin.Api.SharedPrefManager;
import com.byandev.trackadmin.Api.UtilsApi;
import com.byandev.trackadmin.Models.CreateAkunModel;
import com.byandev.trackadmin.Models.RoleModel;
import com.byandev.trackadmin.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAkunActivity extends AppCompatActivity {

  private Context context;
  private ApiEndPoint mApiServices;
  SharedPrefManager sharedPrefManager;

  private EditText nama, username, password, tglahir, noktp, nohp, novisa, nopaspor;
//  String sNama, sUsername, sPassword, sTglLahir, sNoktp, sNohp, sNovisa, sNopasspor;
  String sRole;
  Integer iRole;
  private Button cekAdd;
  private ProgressBar loading;
  private Spinner spRole;
  private Calendar calendar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_akun);

    context = this;
    mApiServices = UtilsApi.getAPIService();
    sharedPrefManager = new SharedPrefManager(context);

    calendar = Calendar.getInstance();
    cekAdd = findViewById(R.id.btnAddUser);
    loading = findViewById(R.id.loading);

    spRole = findViewById(R.id.spRole);
    initSpRole();

    nama = findViewById(R.id.namauser);
    username = findViewById(R.id.userName);
    password = findViewById(R.id.password);
    tglahir = findViewById(R.id.tglLahir);
    noktp = findViewById(R.id.noKtp);
    nohp = findViewById(R.id.noHp);
    novisa = findViewById(R.id.visa);
    nopaspor = findViewById(R.id.passpor);
    
    validasiButton();
    listener();
  }

  private void listener() {
    tglahir.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        new DatePickerDialog(context, listener, 1980,
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)).show();
      }
      DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
          calendar.set(Calendar.YEAR, year);
          calendar.set(Calendar.MONTH, month);
          calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

          SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
          tglahir.setText(dateFormat.format(calendar.getTime()));
        }
      };
    });
  }

  @Override
  public void onStart(){
    super.onStart();
  }


  private void initSpRole() {
    mApiServices.roles().enqueue(new Callback<RoleModel>() {
      @Override
      public void onResponse(Call<RoleModel> call, Response<RoleModel> response) {
        if (response.isSuccessful()) {
          if (response.body().getApiStatus() == 1) {
            if (response.body().getData() != null) {
              final List<RoleModel.Role> roleList = response.body().getData();
              final List<String> strings = new ArrayList<>();
              for (int i = 0; i < roleList.size(); i++) {
                strings.add(roleList.get(i).getRole());
              }
              ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item_2, strings);
              spRole.setAdapter(adapter);
              int intSource = adapter.getPosition(sRole);
              spRole.setSelection(intSource);
              spRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  iRole = roleList.get(position).getId();
                  Log.d("role id: ", String.valueOf(iRole));
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
      public void onFailure(Call<RoleModel> call, Throwable t) {
        loading.setVisibility(View.VISIBLE);
      }
    });
  }

  @Override
  public void onResume(){
    super.onResume();
  }

  private void validasiButton() {
    cekAdd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (TextUtils.isEmpty(nama.getText())) {
          nama.setError("Wajib diisi !");
        } else if (TextUtils.isEmpty(username.getText())){
          username.setError("Wajib diisi !");
        } else if (TextUtils.isEmpty(password.getText())) {
          password.setError("Wajib diisi !");
        } else if (TextUtils.isEmpty(tglahir.getText())) {
          password.setError("Wajib diisi");
        } else if (TextUtils.isEmpty(noktp.getText())) {
          noktp.setError("wajib diisi !");
        } else if (TextUtils.isEmpty(nohp.getText())) {
          nohp.setError("Wajib diisi !");
        } else if (TextUtils.isEmpty(novisa.getText())) {
          novisa.setError("isi N/A apabila belum memiliki data tersebut");
        } else if (TextUtils.isEmpty(nopaspor.getText())) {
          nopaspor.setError("isi N/A apabila belum memiliki data tersebut");
        } else {
          loading.setVisibility(View.VISIBLE);
          cekAdd.setVisibility(View.GONE);
//          loading = ProgressBar.show(context, null, "Tunggu...", true, false);
          createOrder();
        }
      }
    });
  }
  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }

  private void createOrder() {
    mApiServices.acc(
        nama.getText().toString(),
        username.getText().toString(),
        password.getText().toString(),
        tglahir.getText().toString(),
        noktp.getText().toString(),
        nohp.getText().toString(),
        novisa.getText().toString(),
        nopaspor.getText().toString(),
        iRole
    ).enqueue(new Callback<CreateAkunModel>() {
      @Override
      public void onResponse(Call<CreateAkunModel> call, Response<CreateAkunModel> response) {
        loading.setVisibility(View.GONE);
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
      public void onFailure(Call<CreateAkunModel> call, Throwable t) {
        loading.setVisibility(View.GONE);
        Toast.makeText(context, "Gagal kesalahan server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }
}
