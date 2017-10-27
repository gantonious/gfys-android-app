package ca.antonious.gfys.features;

/**
 * Created by George on 2017-10-27.
 */

public class Features {
    private static FeatureConfig featureConfig = new FeatureConfig();

    public static void withFeatureConfig(FeatureConfig featureConfig) {
        Features.featureConfig = featureConfig;
    }

    public static boolean isFullScreenEnabled() {
        return featureConfig.isFeatureEnabled("fullScreen");
    }
}
