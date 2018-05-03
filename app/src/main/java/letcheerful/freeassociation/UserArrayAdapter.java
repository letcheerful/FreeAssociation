package letcheerful.freeassociation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import letcheerful.freeassociation.persistent.model.User;

public class UserArrayAdapter extends RecyclerView.Adapter<UserArrayAdapter.ViewHolder> {

    private List<User> userList = new ArrayList<>();
    private HashMap<Integer, String> indexHeaderMap = new HashMap<>();

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setList(List<User> list) {
        this.userList = list;
        char indexHeader = ' ';
        for (int index=0; index<list.size(); index++) {
            User user = list.get(index);
            String name = user.name.toUpperCase();
            if (indexHeader != name.charAt(0)) {
                indexHeader = name.charAt(0);
                indexHeaderMap.put(index, String.valueOf(indexHeader));
            }
        }
    }

    public void clearList() {
        this.userList.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);

        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.avatarView.setImageURI(user.avatarUrl);
        holder.nameView.setText(user.name);
        holder.favoriteView.setChecked(user.favorite);

        String header = indexHeaderMap.get(position);
        if (header != null) {
            holder.headerView.setVisibility(View.VISIBLE);
            holder.headerView.setText(header);
        } else {
            holder.headerView.setVisibility(View.GONE);
        }
    }

    @Override
    public long getItemId(int position) {
        User user = userList.get(position);
        return (user == null) ? -1 : user.id;
    }

    @Override
    public int getItemCount() {
        return (userList == null) ? 0 : userList.size();
    }

    public User getItemById(long id) {
        for (User user : this.userList) {
            if (user.id == id) return user;
        }
        return null;
    }

    public User getItemAtPosition(int position) {
        return getItemById(getItemId(position));
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView parent, View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView headerView;
        SimpleDraweeView avatarView;
        TextView nameView;
        CheckBox favoriteView;

        ViewHolder(View view, OnItemClickListener listener) {
            super(view);
            headerView = view.findViewById(R.id.header);
            avatarView = view.findViewById(R.id.avatar);
            nameView = view.findViewById(R.id.name);
            favoriteView = view.findViewById(R.id.favorite);

            view.setOnClickListener(
                    v -> listener.onItemClick(
                            (RecyclerView) itemView.getParent(),
                            itemView,
                            getAdapterPosition())
            );
        }
    }
}