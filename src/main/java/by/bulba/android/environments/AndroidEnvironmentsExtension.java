package by.bulba.android.environments;

public class AndroidEnvironmentsExtension {

    public static final String EXTENSIONS_NAME = "environments";

    public String configPath = "config";
    public String configFile = "config.properties";
    public boolean useBuildTypes = false;
    public boolean useProductFlavors = false;

    @Override
    public String toString() {
        return "Path: " + configPath +
                " File: " + configFile +
                " UseBuildTypes: " + useBuildTypes +
                " UseProductFlavors: " + useProductFlavors;
    }
}
