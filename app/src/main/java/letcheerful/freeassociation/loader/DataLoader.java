package letcheerful.freeassociation.loader;

import java.util.List;

import io.reactivex.Flowable;
import letcheerful.freeassociation.persistent.model.User;

public interface DataLoader {
    Flowable<List<User>> load(String keyword);
}
