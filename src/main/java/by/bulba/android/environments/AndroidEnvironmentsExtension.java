package by.bulba.android.environments;

public class AndroidEnvironmentsExtension {
    String configPath = "config";
    String configFile = "config.properties";
    boolean useBuildTypes = false;
    boolean useProductFlavors = false;

    @Override
    public String toString() {
        return "Path: " + configPath +
                " File: " + configFile +
                " UseBuildTypes: " + useBuildTypes +
                " UseProductFlavors: " + useProductFlavors;
    }
}
