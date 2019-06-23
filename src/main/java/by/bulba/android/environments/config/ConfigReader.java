package by.bulba.android.environments.config;

import java.util.Collection;

/**
 * Configuration file reader interface.
 * Each config file must support conversion to {@link ConfigValue} type.
 * to
 * */
public interface ConfigReader {

    /**
     * Configuration collection from configuration file.
     * */
    Collection<ConfigValue> getConfigValues();

}
