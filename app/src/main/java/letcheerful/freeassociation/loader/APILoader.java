package letcheerful.freeassociation.loader;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import letcheerful.freeassociation.persistent.UserDataBase;
import letcheerful.freeassociation.persistent.model.User;
import letcheerful.freeassociation.service.UserSearchResponse;
import letcheerful.freeassociation.service.UserSearchService;
import retrofit2.Call;
import retrofit2.Response;

public class APILoader implements DataLoader {

    private final static Comparator UserComparator = new Comparator() {
        private final Collator collator = Collator.getInstance();

        @Override
        public int compare(Object object1, Object object2) {
            return collator.compare(((User) object1).name, ((User) object2).name);
        }
    };
    private final String SERVICE_URL = "https://api.github.com";
    private final int PAGE_SIZE = 100;
    private UserSearchService service = new UserSearchService(SERVICE_URL);
    private UserDataBase dataBase;

    public APILoader(UserDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Flowable<List<User>> load(String keyword) {
        return Flowable.create(e -> {

            int currentPage = 1;
            List<User> userList = new ArrayList<>();

            while (true) {
                Call<UserSearchResponse> call = service
                        .getApi()
                        .queryUsers((queryWithKeyword(keyword)), currentPage, PAGE_SIZE);

                Response<UserSearchResponse> response = call.execute();

                List<User> userListInPage = (response.body() != null)? response.body().userList: null;
                if (userListInPage != null && response.body().userList.size() > 0) {

                    for (User user : response.body().userList) {
                        List<User> foundUserList = this.dataBase.access().getUserListById(user.id);
                        user.favorite = (foundUserList != null) && (foundUserList.size() == 1);
                    }

                    userList.addAll(response.body().userList);
                    Collections.sort(userList, UserComparator);

                    e.onNext(userList);

                    currentPage++;
                }

                if (userListInPage == null || userListInPage.size() < PAGE_SIZE) {
                    e.onComplete();
                    break;
                }
            }
        }, BackpressureStrategy.ERROR);
    }

    private String queryWithKeyword(String keyword) {
        return keyword + "+" + "in:login";
    }
}
