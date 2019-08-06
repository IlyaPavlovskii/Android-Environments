package by.bulba.android.environments.config;

import by.bulba.android.environments.AndroidEnvironmentsExtension;
import by.bulba.android.environments.file.ConfigFileProvider;
import org.gradle.api.Project;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class ApplicationVariantConfigReaderFactoryTest {

    private static final String ROOT_DIR_PATH = "/home/user/project/config";

    @Mock
    File rootDir;
    @Mock
    Project project;
    @Mock
    ConfigFileProvider configFileProvider;
    @Mock
    ConfigReaderFactory configReaderFactory;
    AndroidEnvironmentsExtension extension = new AndroidEnvironmentsExtension();
    private ApplicationVariantConfigReaderFactory factory =
            new ApplicationVariantConfigReaderFactory();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(rootDir.getPath()).thenReturn(ROOT_DIR_PATH);
        when(project.getRootDir()).thenReturn(rootDir);
    }

    @Test
    public void createAppVarInstanceFromProjectAndExtension() {
        assertThat(
                factory.create(project, extension),
                instanceOf(ApplicationVariantConfigValueReaderImpl.class)
        );
    }

    @Test
    public void createAppVarInstance() {
        assertThat(
                factory.create(configFileProvider, configReaderFactory),
                instanceOf(ApplicationVariantConfigValueReaderImpl.class)
        );
    }

}