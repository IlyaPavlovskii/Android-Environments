package by.bulba.android.environments.file;

import by.bulba.android.environments.ConfigFormat;
import by.bulba.android.environments.exceptions.DirectoryExpectedException;
import by.bulba.android.environments.exceptions.DirectoryNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfigFileProviderImplTest {

    private static final String CONFIG_TEST_DIR = "configTestDir";
    private static final String NON_EXISTS_DIR = "non_exists_folder";
    private static final String NON_DIR_FILE = "notDirectory";
    private static final String EMPTY = "empty";
    private static final String DEBUG = "debug";
    private static final int EXPECTED_PROPERTIES_FILE_COUNT_IN_DEBUG = 2;

    private URL configRootUrl = getClass()
            .getClassLoader()
            .getResource(CONFIG_TEST_DIR);
    private File configRootDir = new File(configRootUrl.getFile());
    private ConfigFormat configFormat = ConfigFormat.PROPERTIES;
    private ConfigFileProviderImpl provider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        provider = new ConfigFileProviderImpl(configRootDir, configFormat);
    }

    @Test(expected = DirectoryNotFoundException.class)
    public void throwDirNotFoundExceptionWhenSubfolderDirNotFound() {
        provider.getConfigFiles(NON_EXISTS_DIR);
    }

    @Test(expected = DirectoryExpectedException.class)
    public void throwDirExpectedExceptionWhenSubdirectoryIsNotDirectory() {
        provider.getConfigFiles(NON_DIR_FILE);
    }

    @Test
    public void returnEmptyCollectionWhenConfigFilesEmpty() {
        assertTrue(provider.getConfigFiles(EMPTY).isEmpty());
    }

    @Test
    public void returnOnlyFilteredConfigFiles() {
        assertEquals(
                EXPECTED_PROPERTIES_FILE_COUNT_IN_DEBUG,
                provider.getConfigFiles(DEBUG).size()
        );
    }

}