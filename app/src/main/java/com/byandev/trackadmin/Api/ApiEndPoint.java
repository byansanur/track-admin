package com.byandev.trackadmin.Api;

import com.byandev.trackadmin.Models.CreateAkunModel;
import com.byandev.trackadmin.Models.CreateRekomModel;
import com.byandev.trackadmin.Models.DetailRekomModel;
import com.byandev.trackadmin.Models.DetailSosModel;
import com.byandev.trackadmin.Models.JamaahDetailModel;
import com.byandev.trackadmin.Models.LocationUsersModel;
import com.byandev.trackadmin.Models.Login;
import com.byandev.trackadmin.Models.ProfileModel;
import com.byandev.trackadmin.Models.RekomModel;
import com.byandev.trackadmin.Models.RoleModel;
import com.byandev.trackadmin.Models.SosListModel;
import com.byandev.trackadmin.Models.TypeModel;
import com.byandev.trackadmin.Models.UpdateLocationModel;
import com.byandev.trackadmin.Models.UserListModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiEndPoint {
  @FormUrlEncoded
  @POST("login_admin")
  Call<Login> requestLogin(@Field("username") String username,
                           @Field("password") String password);

  @FormUrlEncoded
  @PUT("update_location")
  Call<UpdateLocationModel> updateLocation(@Field("id") Integer id,
                                           @Field("lat") String lat,
                                           @Field("lng") String lng);

  @FormUrlEncoded
  @POST("create_akun")
  Call<CreateAkunModel> acc(@Field("nama") String nama,
                            @Field("username") String username,
                            @Field("password") String password,
                            @Field("tgl_lahir") String tglLahir,
                            @Field("no_ktp") String noKtp,
                            @Field("no_hp") String noHp,
                            @Field("no_visa") String noVisa,
                            @Field("no_passpor") String noPasspor,
                            @Field("id_privileges") Integer idPrivileges);

  @FormUrlEncoded
  @POST("rekom_create")
  Call<CreateRekomModel> recomAcc(@Field("nama") String nama,
                                  @Field("alamat") String alamat,
                                  @Field("lat") String lat,
                                  @Field("lng") String lng,
                                  @Field("rating") String rating,
                                  @Field("id_type") Integer idType);

  @GET("location/users")
  Call<LocationUsersModel> loc(@Query("id_privileges") Integer idPrivileges);

  @GET("fetch_users")
  Call<ProfileModel> profile(@Query("id") Integer id,
                             @Query("id_privileges") Integer idPrivileges);

  @GET("rekom_detail")
  Call<DetailRekomModel> detail(@Query("id") Integer id);

  @GET("rekom_list")
  Call<RekomModel> rekomkategori(@Query("id_type") Integer idType);

  @GET("rekom_list")
  Call<RekomModel> rekomList(@Query("id_type") Integer idType,
                             @Query("limit") Integer limit,
                             @Query("offset") Integer offset);

  @GET("sos/list")
  Call<SosListModel> listSos(@Query("limit") Integer limit,
                             @Query("offset") Integer offset);

  @GET("sos/detail")
  Call<DetailSosModel> detailSos(@Query("id") Integer id);

  @GET("fetch_users/list")
  Call<UserListModel> usersAll(@Query("id_privileges") Integer idPrivileges,
                             @Query("limit") Integer limit,
                             @Query("offset") Integer offset);

  @GET("fetch_users")
  Call<JamaahDetailModel> jamaahDetail(@Query("id") Integer id);

  @GET("privileges_role")
  Call<RoleModel> roles();

  @GET("privileges_type")
  Call<TypeModel> type();


//  @FormUrlEncoded
//  @POST("sos")
//  Call<SosCreateModel> sosCreate(@Field("message") String message,
//                                 @Field("id_users_sender") Integer idUsers,
//                                 @Field("lat") String lat,
//                                 @Field("lng") String lng);
//
//  @FormUrlEncoded
//  @PUT("update_location")
//  Call<UpdateLocationModel> updateLocation(@Field("id") Integer id,
//                                           @Field("lat") String lat,
//                                           @Field("lng") String lng);
//
//  @GET("rekom_list")
//  Call<RekomModel> rekomList(@Query("id_type") Integer idType,
//                             @Query("limit") Integer limit,
//                             @Query("offset") Integer offset);
//
//  @GET("sos/detail")
//  Call<DetailSosModel> detailSos(@Query("id") Integer id);
//
//  @GET("sos/list")
//  Call<SosListModel> listSos(@Query("id_users_sender") Integer idUsers,
//                             @Query("limit") Integer limit,
//                             @Query("offset") Integer offset);
//
//  @GET("rekom_list")
//  Call<RekomModel> rekomkategori(@Query("id_type") Integer idType);
//
//  @GET("rekom_list")
//  Call<RekomModel> rekomListMap();
//
//  @GET("rekom_list")
//  Call<RekomModel> search(@Query("id_type") Integer idType,
//                          @Query("nama") String nama);
//
//  @GET("rekom_detail")
//  Call<DetailRekomModel> detail(@Query("id") Integer id);
//
//  @GET("fetch_users")
//  Call<ProfileModel> profile(@Query("id") Integer id,
//                             @Query("id_privileges") Integer idPrivileges);


}
