package letcheerful.freeassociation;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import letcheerful.freeassociation.loader.APILoader;
import letcheerful.freeassociation.loader.LocalLoader;
import letcheerful.freeassociation.persistent.UserDataBase;
import letcheerful.freeassociation.persistent.model.User;

public class GitHubUserAssociator {

    public static final int MODE_API = 1;
    public static final int MODE_LOCAL = 2;

    private APILoader apiLoader;
    private LocalLoader localLoader;
    private UserDataBase dataBase;
    private int mode;

    public GitHubUserAssociator(Context context, int mode) {
        this.mode = mode;
        this.dataBase = Room.databaseBuilder(context,
                UserDataBase.class, "user-db").build();

        this.apiLoader = new APILoader(dataBase);
        this.localLoader = new LocalLoader(dataBase);
    }

    public Flowable<List<User>> getUserList(String keyword) {
        if (mode == MODE_LOCAL) return localLoader.load(keyword);
        if (mode == MODE_API) return apiLoader.load(keyword);
        return Flowable.empty();
    }

    public void addFavorite(User user) {
        Flowable.just(user)
                .subscribeOn(Schedulers.io())
                .subscribe(aUser -> this.dataBase.access().addUser(aUser));
    }

    public void removeFavorite(User user) {
        Flowable.just(user)
                .subscribeOn(Schedulers.io())
                .subscribe(aUser -> this.dataBase.access().removeUser(aUser));
    }
}
