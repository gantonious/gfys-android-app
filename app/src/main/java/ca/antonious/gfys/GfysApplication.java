package ca.antonious.gfys;

import android.app.Application;

import com.gfycat.core.GfyCoreInitializationBuilder;
import com.gfycat.core.GfyCoreInitializer;
import com.gfycat.core.GfycatApplicationInfo;

/**
 * Created by George on 2017-10-26.
 */


public class GfysApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        GfyCoreInitializer.initialize(
                new GfyCoreInitializationBuilder(
                        this,
                        new GfycatApplicationInfo(Secrets.CLIENT_ID, Secrets.CLIENT_SECRET)

        ));
    }
}
