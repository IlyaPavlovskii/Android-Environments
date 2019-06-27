package by.bulba.android.environments.config;

import org.gradle.internal.impldep.com.google.common.annotations.VisibleForTesting;

/**
 * Base {@link ConfigReader} implementation with basis implementation of common methods.
 * */
public abstract class BaseConfigReader implements ConfigReader {

    @VisibleForTesting
    protected String toConfigKey(String key) {
        return key.replaceAll("(\\.)|(-)", "_")
                .toUpperCase();
    }

}
