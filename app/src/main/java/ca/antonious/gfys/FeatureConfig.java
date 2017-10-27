package ca.antonious.gfys;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by George on 2017-10-27.
 */

public class FeatureConfig {
    private Map<String, Boolean> features = new HashMap<>();

    public boolean isFeatureEnabled(String featureName) {
        return features.containsKey(featureName) &&
                features.get(featureName);
    }
}
