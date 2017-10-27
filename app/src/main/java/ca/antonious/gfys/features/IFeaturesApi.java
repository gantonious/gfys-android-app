package ca.antonious.gfys.features;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by George on 2017-10-27.
 */

public interface IFeaturesApi {
    @GET("/api/v1/features")
    Observable<List<Feature>> getFeatures();
}
