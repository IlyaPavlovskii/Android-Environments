package by.bulba.android.environments.config;

/**
 * {@link ConfigReader} factory.
 * Plugin have to more than one implementation of configuration reader and have to use
 * different types of reader of each extension.
 */
public interface ConfigReaderFactory {

    /**
     * Creates new {@link ConfigReader} implementation of this sub config.
     *
     * @param subConfig sub configuration path.
     * @return {@link ConfigReader} implementation for current config.
     */
    ConfigReader create(String subConfig);

}
