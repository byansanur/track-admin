package com.byandev.trackadmin.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TypeModel {
  @SerializedName("api_status")
  @Expose
  private Integer apiStatus;
  @SerializedName("api_message")
  @Expose
  private String apiMessage;
  @SerializedName("data")
  @Expose
  private List<Typed> data = null;

  public Integer getApiStatus() {
    return apiStatus;
  }

  public void setApiStatus(Integer apiStatus) {
    this.apiStatus = apiStatus;
  }

  public String getApiMessage() {
    return apiMessage;
  }

  public void setApiMessage(String apiMessage) {
    this.apiMessage = apiMessage;
  }

  public List<Typed> getData() {
    return data;
  }

  public void setData(List<Typed> data) {
    this.data = data;
  }

  public class Typed {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("type_rekom")
    @Expose
    private String typeRekom;

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public String getTypeRekom() {
      return typeRekom;
    }

    public void setTypeRekom(String typeRekom) {
      this.typeRekom = typeRekom;
    }

  }

}
