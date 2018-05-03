package letcheerful.freeassociation;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import letcheerful.freeassociation.persistent.model.User;

public class UserSearchFragment extends Fragment {

    private SearchView searchView;

    private UserArrayAdapter adapter;
    private Disposable loadSubscription;
    private Disposable searchSubscription;
    private PublishProcessor<String> searchProcessor;
    private GitHubUserAssociator associator;
    private ProgressBar progressBar;

    public void setAssociator(GitHubUserAssociator associator) {
        this.associator = associator;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_list, container, false);


        adapter = new UserArrayAdapter();
        adapter.setOnItemClickListener((parent, itemView, position) -> {
            User user = adapter.getItemAtPosition(position);
            if (user.favorite) {
                user.favorite = false;
                associator.removeFavorite(user);
            } else {
                user.favorite = true;
                associator.addFavorite(user);
            }
            adapter.notifyItemChanged(position);
        });

        RecyclerView userListView = view.findViewById(R.id.userList);
        userListView.setAdapter(adapter);
        searchView = view.findViewById(R.id.search);

        searchProcessor = PublishProcessor.create();
        searchSubscription = searchProcessor
                .debounce(100, java.util.concurrent.TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .map(keyword -> {
                    adapter.clearList();
                    return keyword;
                })
                .subscribe(keyword -> search(keyword));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchProcessor.onComplete();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchProcessor.onNext(newText);
                return false;
            }
        });

        progressBar = new ProgressBar(getActivity());

        return view;
    }

    @Override
    public void onDetach() {
        searchSubscription.dispose();
        loadSubscription.dispose();
        super.onDetach();
    }

    public void search(String keyword) {
        if (keyword == null || keyword.length() == 0) {
            adapter.clearList();
            adapter.notifyDataSetChanged();
            return;
        }

        if (loadSubscription != null && loadSubscription.isDisposed()) {
            loadSubscription.dispose();
        }

        showProgress();

        loadSubscription = associator.getUserList(keyword)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userList -> {
                            adapter.setList(userList);
                            adapter.notifyDataSetChanged();
                        },
                        error -> dismissProgress(),
                        this::dismissProgress

                );
    }

    public void showProgress() {

    }

    public void dismissProgress() {
    }
}
