package letcheerful.freeassociation.loader;

import java.util.List;

import io.reactivex.Flowable;
import letcheerful.freeassociation.persistent.UserDataBase;
import letcheerful.freeassociation.persistent.model.User;

public class LocalLoader implements DataLoader {

    private UserDataBase dataBase;

    public LocalLoader(UserDataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Flowable<List<User>> load(String keyword) {
        return this.dataBase.access().getUserListByKeyword(keyword);
    }
}
