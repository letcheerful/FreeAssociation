package letcheerful.freeassociation.persistent;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import letcheerful.freeassociation.persistent.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserDataBase extends RoomDatabase {
    public abstract User.Access access();
}
