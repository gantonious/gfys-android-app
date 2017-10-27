package ca.antonious.gfys;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gfycat.core.GfyCore;
import com.gfycat.core.PublicFeedIdentifier;
import com.gfycat.core.downloading.FeedDescription;
import com.gfycat.core.gfycatapi.pojo.Gfycat;

import ca.antonious.gfys.features.Feature;
import ca.antonious.gfys.features.FeatureConfig;
import ca.antonious.gfys.features.Features;
import ca.antonious.gfys.features.IFeaturesApi;
import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity implements GfycatViewCell.OnGyfcatClickedListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private HomogeneousSection<Gfycat, GfycatViewCell> gfycatsSection;

    private Subscription feedSubscription;
    private FeedDescription lastFeedDescriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(buildViewCellAdapter());

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (lastFeedDescriptions != null) {
                GfyCore.getFeedManager()
                        .getNewGfycats(lastFeedDescriptions)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> swipeRefreshLayout.setRefreshing(false));
            } else {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        feedSubscription = GfyCore.getFeedManager()
                .observeGfycats(this, PublicFeedIdentifier.trending())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedData -> {
                    lastFeedDescriptions = feedData.getFeedDescription();
                    gfycatsSection.setAll(feedData.getGfycats());
                    swipeRefreshLayout.setRefreshing(false);
                });

        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
        GfyCore.getFeedManager().getGfycats(PublicFeedIdentifier.trending());
    }

    @Override
    protected void onPause() {
        super.onPause();
        feedSubscription.unsubscribe();
    }

    private ViewCellAdapter buildViewCellAdapter() {
        gfycatsSection = new HomogeneousSection<>(GfycatViewCell::new);

        return ViewCellAdapter.create()
            .section(gfycatsSection)
            .listener(this)
            .build();
    }

    @Override
    public void onGfycatClicked(String gifLink) {
        FullScreenGifActivity.start(this, gifLink);
    }
}
