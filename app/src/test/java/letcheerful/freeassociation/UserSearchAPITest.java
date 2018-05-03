package letcheerful.freeassociation;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import letcheerful.freeassociation.service.UserSearchResponse;
import letcheerful.freeassociation.service.UserSearchService;
import letcheerful.freeassociation.persistent.model.User;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserSearchAPITest {


    private UserSearchService service;

    @Before
    public void setup() {
        service = new UserSearchService("https://api.github.com");
    }


    @Test
    public void testURL() throws IOException {
        Call<UserSearchResponse> call = service.getApi().queryUsers("letcheerful", 0, 1);
        Response<UserSearchResponse> response = call.execute();
        UserSearchResponse searchResponse = response.body();

        Assert.assertTrue(searchResponse.userList.size() > 0);
    }
}