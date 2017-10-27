package ca.antonious.gfys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ca.antonious.gfys.features.FeatureConfig;
import ca.antonious.gfys.features.Features;
import ca.antonious.gfys.features.IFeaturesApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(IFeaturesApi.class)
                .getFeatures()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(FeatureConfig::fromFeatures)
                .subscribe(featureConfig -> {
                    Features.withFeatureConfig(featureConfig);
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                }, throwable -> {
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                });
    }
}
