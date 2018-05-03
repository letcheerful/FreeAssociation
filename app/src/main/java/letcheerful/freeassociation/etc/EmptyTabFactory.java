package letcheerful.freeassociation.etc;

import android.content.Context;
import android.view.View;
import android.widget.TabHost;

public class EmptyTabFactory implements TabHost.TabContentFactory {

    private Context context;

    public EmptyTabFactory(Context context) {
        this.context = context;
    }

    @Override
    public View createTabContent(String tag) {
        return new View(context);
    }
}
