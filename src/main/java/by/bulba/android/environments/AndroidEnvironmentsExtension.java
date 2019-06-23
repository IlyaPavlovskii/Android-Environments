package by.bulba.android.environments;

/**
 * Android environment extension file. Contains plugin settings.
 * */
public class AndroidEnvironmentsExtension {

    public static final String EXTENSIONS_NAME = "environments";

    /**
     * Relative path of configuration folder.
     * */
    public String configPath = "config";
    /**
     * Configuration property file name.
     * */
    public String configFile = "config.properties";
    /**
     * Read configuration properties for build types.
     * */
    public boolean useBuildTypes = false;
    /**
     * Read configuration properties for each product flavor.
     * */
    public boolean useProductFlavors = false;

    @Override
    public String toString() {
        return "Path: " + configPath +
                " File: " + configFile +
                " UseBuildTypes: " + useBuildTypes +
                " UseProductFlavors: " + useProductFlavors;
    }
}
