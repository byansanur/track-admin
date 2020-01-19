package com.byandev.trackadmin.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoleModel {
  @SerializedName("api_status")
  @Expose
  private Integer apiStatus;
  @SerializedName("api_message")
  @Expose
  private String apiMessage;
  @SerializedName("data")
  @Expose
  private List<Role> data = null;

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

  public List<Role> getData() {
    return data;
  }

  public void setData(List<Role> data) {
    this.data = data;
  }

  public class Role {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("role")
    @Expose
    private String role;

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public String getRole() {
      return role;
    }

    public void setRole(String role) {
      this.role = role;
    }

  }
}
