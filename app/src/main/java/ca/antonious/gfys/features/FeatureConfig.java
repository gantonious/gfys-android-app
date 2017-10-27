package ca.antonious.gfys.features;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by George on 2017-10-27.
 */

public class FeatureConfig {
    private Map<String, Boolean> featuresMap = new HashMap<>();

    public static FeatureConfig fromFeatures(List<Feature> features) {
        FeatureConfig featureConfig = new FeatureConfig();

        for (Feature feature : features) {
            featureConfig.featuresMap.put(feature.getName(), feature.isEnabled());
        }

        return featureConfig;
    }

    public boolean isFeatureEnabled(String featureName) {
        return featuresMap.containsKey(featureName) &&
                featuresMap.get(featureName);
    }
}
