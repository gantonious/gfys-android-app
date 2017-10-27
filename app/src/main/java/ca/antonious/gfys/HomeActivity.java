package ca.antonious.gfys;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gfycat.core.FeedIdentifier;
import com.gfycat.core.FeedIdentifierFactory;
import com.gfycat.core.GfyCore;
import com.gfycat.core.PublicFeedIdentifier;
import com.gfycat.core.gfycatapi.pojo.Gfycat;

import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;
import rx.android.schedulers.AndroidSchedulers;

public class HomeActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private HomogeneousSection<Gfycat, GfycatViewCell> gfycatsSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> GfyCore.getFeedManager().getGfycats(PublicFeedIdentifier.trending()));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(buildViewCellAdapter());

        GfyCore.getFeedManager()
                .observeGfycats(this, PublicFeedIdentifier.trending())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedData -> {
                    gfycatsSection.setAll(feedData.getGfycats());
                    swipeRefreshLayout.setRefreshing(false);
                });

        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
        GfyCore.getFeedManager().getGfycats(PublicFeedIdentifier.trending());
    }

    private ViewCellAdapter buildViewCellAdapter() {
        gfycatsSection = new HomogeneousSection<>(GfycatViewCell::new);

        return ViewCellAdapter.create()
            .section(gfycatsSection)
            .build();
    }
}
