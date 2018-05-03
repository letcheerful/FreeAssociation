package letcheerful.freeassociation.service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import letcheerful.freeassociation.persistent.model.User;

public class UserSearchResponse {

    @SerializedName("total_count")
    public int totoalCount;

    @SerializedName("incomplete_results")
    public boolean timeout;

    @SerializedName("items")
    public List<User> userList;
}
