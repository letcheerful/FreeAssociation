package letcheerful.freeassociation.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserSearchAPI {

    @GET("/search/users?")
    Call<UserSearchResponse> queryUsers(
            @Query(value = "q", encoded = true) String query,
            @Query("page") int page,
            @Query("per_page") int itemsPerPage
    );

}

