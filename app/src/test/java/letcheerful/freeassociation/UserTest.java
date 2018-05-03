package letcheerful.freeassociation;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import letcheerful.freeassociation.persistent.model.User;
import letcheerful.freeassociation.service.UserSearchService;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserTest {


    private UserSearchService service;

    @Before
    public void setup() {

    }


    @Test
    public void testEquals() {
        User userX = new User();

        userX.id = 8295371;
        userX.name = "letcheerful";
        userX.avatarUrl = "https://avatars1.githubusercontent.com/u/8295371?v=4";

        User userY = new User();

        userY.id = 8295371;
        userY.name = "letcheerful";
        userY.avatarUrl = "https://avatars1.githubusercontent.com/u/8295371?v=4";

        User userZ = new User();

        userZ.id = 8295371;
        userZ.name = "letcheerful";
        userZ.avatarUrl = "https://avatars1.githubusercontent.com/u/8295371?v=4";


        Assert.assertTrue(userX.equals(userX));

        if (userX.equals(userY)) {
            Assert.assertTrue(userY.equals(userX));
        }

        if (userX.equals(userY) && userY.equals(userZ)) {
            Assert.assertTrue(userX.equals(userZ));
        }
    }
}