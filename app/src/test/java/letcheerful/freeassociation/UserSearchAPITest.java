package letcheerful.freeassociation;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import letcheerful.freeassociation.service.UserSearchService;
import letcheerful.freeassociation.persistent.model.User;

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
    public void testURL() throws InterruptedException {
        final CountDownLatch initLock = new CountDownLatch(1);
        service.getApi().queryUsers("letcheerful", 0, 1)
                .subscribe(
                        response -> {
                            List<User> userList = response.userList;
                            for (User user : userList) {
                                System.out.println(user);
                            }

                            Assert.assertTrue(userList.size() > 0);

                            initLock.countDown();
                        },
                        error -> {
                            Assert.assertTrue(false);

                            initLock.countDown();
                        }
                        );
        initLock.await();
    }
}