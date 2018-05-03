package letcheerful.freeassociation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import com.facebook.drawee.backends.pipeline.Fresco;

import letcheerful.freeassociation.etc.EmptyTabFactory;


public class UserListActivity extends AppCompatActivity {

    private UserSearchFragment apiSearchFragment;
    private UserSearchFragment localSearchFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list_activity);

        Fresco.initialize(this);

        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        tabHost.addTab(
                tabHost.newTabSpec(getString(R.string.api))
                .setContent(new EmptyTabFactory(this))
                .setIndicator(getString(R.string.api))
        );
        tabHost.addTab(
                tabHost.newTabSpec(getString(R.string.local))
                        .setContent(new EmptyTabFactory(this))
                        .setIndicator(getString(R.string.local))
        );
        tabHost.setCurrentTabByTag(getString(R.string.local));

        tabHost.setOnTabChangedListener(tabId -> {
            if (tabId.equals(getString(R.string.api))) {
                if (apiSearchFragment == null) {
                    apiSearchFragment = new UserSearchFragment();
                    apiSearchFragment.setAssociator(
                            new GitHubUserAssociator(this, GitHubUserAssociator.MODE_API)
                    );
                }
                loadFragment(apiSearchFragment);
            }

            if (tabId.equals(getString(R.string.local))) {
                if(localSearchFragment == null) {
                    localSearchFragment = new UserSearchFragment();
                    localSearchFragment.setAssociator(
                            new GitHubUserAssociator(this, GitHubUserAssociator.MODE_LOCAL)
                    );

                }
                loadFragment(localSearchFragment);
            }

        });

        tabHost.setCurrentTabByTag(getString(R.string.api));
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }
}