package letcheerful.freeassociation.persistent.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.Flowable;

@Entity
public class User {

    @PrimaryKey
    @SerializedName("id")
    public long id;

    @SerializedName("login")
    public String name;

    @SerializedName("avatar_url")
    public String avatarUrl;

    @SerializedName("favorite")
    public Boolean favorite;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof User)) return false;

        return (id == ((User) object).id)
                && name.equals(((User) object).name)
                && avatarUrl.equals(((User) object).avatarUrl);
    }

    @Dao
    public interface Access {
        @Query("SELECT * FROM User WHERE id = :id")
        List<User> getUserListById(long id);

        @Query("SELECT * FROM User WHERE id = :id")
        Flowable<List<User>> loadUserListById(long id);

        @Query("SELECT * FROM User ORDER BY name DESC")
        Flowable<List<User>> loadAll();

        @Query("SELECT * FROM User ORDER BY name DESC LIMIT :limit OFFSET :offset")
        Flowable<List<User>> loadUsers(int limit, int offset);

        @Query("SELECT * FROM User WHERE name LIKE '%' || :keyword || '%' ORDER BY name DESC")
        Flowable<List<User>> getUserListByKeyword(String keyword);

        @Query("SELECT * FROM User")
        List<User> getAll();

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void addUserList(List<User> UserList);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void addUser(User user);

        @Update
        void updateUser(User user);

        @Update
        void updateUserList(List<User> UserList);

        @Delete
        void removeUser(User user);

        @Query("DELETE FROM User")
        void removeAll();
    }
}
