package by.bulba.android.environments;

import by.bulba.android.environments.config.ConfigReader;
import by.bulba.android.environments.config.ConfigReaderFactory;
import by.bulba.android.environments.config.ConfigType;
import by.bulba.android.environments.config.ConfigValue;
import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.internal.dsl.BuildType;
import org.gradle.api.Action;
import org.gradle.api.DomainObjectSet;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.Collections;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


public class AndroidEnvironmentsPluginTest {

    private static final String ROOT_PATH = "some/root/dir";
    private static final String CONFIG_PATH = "config/dir";
    private static final String CONFIG_FILE = "config.proper";

    private static final String KEY = "key";
    private static final String VALUE = "val";
    private static final ConfigType CONFIG_TYPE = ConfigType.STRING;

    private ConfigValue configValue = new ConfigValue.Builder()
            .key(KEY)
            .type(CONFIG_TYPE)
            .value(VALUE)
            .build();

    @Mock
    private File rootFile;
    @Mock
    private ConfigReader configReader;
    @Mock
    private ConfigReaderFactory readerFactory;
    @Mock
    private BuildType buildType;
    @Mock
    private NamedDomainObjectContainer<BuildType> buildTypes;
    private Consumer<BuildType> buildTypeConsumer;

    @Mock
    private ApplicationVariant applicationVariant;
    @Mock
    private DomainObjectSet<ApplicationVariant> applicationVariants;
    private Consumer<ApplicationVariant> appVariantConsumer;
    @Mock
    private AppExtension appExtension;

    @Mock
    private ExtensionContainer extensionContainer;
    @Mock
    private Project project;

    private AndroidEnvironmentsPlugin plugin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        plugin = new AndroidEnvironmentsPlugin();

        plugin.ext = new AndroidEnvironmentsExtension();
        plugin.ext.configPath = CONFIG_PATH;
        plugin.ext.configFile = CONFIG_FILE;
        when(rootFile.getPath()).thenReturn(ROOT_PATH);
        when(project.getRootDir()).thenReturn(rootFile);
        when(extensionContainer.getByName(eq("android"))).thenReturn(appExtension);


        when(configReader.getConfigValues()).thenReturn(Collections.singleton(configValue));
        when(readerFactory.create(anyString())).thenReturn(configReader);

        when(project.getExtensions()).thenReturn(extensionContainer);

        when(buildType.getName()).thenReturn("some_name");
        doAnswer(invocation ->
                buildTypeConsumer = invocation.getArgument(0)
        ).when(buildTypes).forEach(any());
        when(appExtension.getBuildTypes()).thenReturn(buildTypes);

        when(applicationVariant.getFlavorName()).thenReturn("some_flavor_name");
        doAnswer(invocation ->
                appVariantConsumer = invocation.getArgument(0)
        ).when(applicationVariants).forEach(any());
        when(appExtension.getApplicationVariants()).thenReturn(applicationVariants);
    }

    @Test
    public void existsPluginExtension() {
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply("by.bulba.android.environments");

        project.getExtensions().findByName(AndroidEnvironmentsExtension.EXTENSIONS_NAME);
    }

    @Test
    public void buildConfigFieldOnBuildTypeWhenConfigValuesIsNotEmpty() {
        plugin.processBuildTypes(readerFactory, appExtension);
        buildTypeConsumer.accept(buildType);

        verify(buildType).buildConfigField(
                eq(CONFIG_TYPE.getConfigString()),
                eq(KEY),
                eq(VALUE)
        );
    }

    @Test
    public void buildConfigFiledOnApplicationVariantWhenConfigValuesIsNotEmpty() {
        plugin.processApplicationVariants(readerFactory, appExtension);
        appVariantConsumer.accept(applicationVariant);

        verify(applicationVariant).buildConfigField(
                eq(CONFIG_TYPE.getConfigString()),
                eq(KEY),
                eq(VALUE)
        );
    }

    @Test
    public void createExtensionWhenApplyPluginOnProject() {
        plugin.apply(project);

        verify(extensionContainer).create(
                eq(AndroidEnvironmentsExtension.EXTENSIONS_NAME),
                eq(AndroidEnvironmentsExtension.class));
    }

    @Test
    public void callAfterEvaluateSetupWhenApplyPlugin() {
        plugin.apply(project);
        verify(project).afterEvaluate(any(Action.class));
    }

    @Test
    public void setupBuildTypesWhenExtensionHasUseBuildTypes() {
        plugin.ext.useBuildTypes = true;
        plugin.executeTask(project);
        verify(appExtension).getBuildTypes();
    }

    @Test
    public void doNotSetupBuildTypesWhenExtensionHasNotUseBuildTypes() {
        plugin.ext.useBuildTypes = false;
        plugin.executeTask(project);
        verify(appExtension, never()).getBuildTypes();
    }

    @Test
    public void setupAppVariantsWhenExtensionHasUseProductFlavors() {
        plugin.ext.useProductFlavors = true;
        plugin.executeTask(project);
        verify(appExtension).getApplicationVariants();
    }

    @Test
    public void doNotSetupAppVariantsWhenExtensionHasNotUseProductFlavors() {
        plugin.ext.useProductFlavors = false;
        plugin.executeTask(project);
        verify(appExtension, never()).getApplicationVariants();
    }
}
