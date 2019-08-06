package by.bulba.android.environments.config;

import by.bulba.android.environments.exceptions.UniqueKeyException;
import by.bulba.android.environments.file.ConfigFileProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ApplicationVariantConfigValueReaderImplTest {

    private static final String APP_VARIANT = "app_variant";
    private static final int EXPECTED_CONFIG_VALUES = 2;

    @Mock
    private ConfigFileProvider configFileProvider;
    @Mock
    private ConfigReader configReader;
    @Mock
    private ConfigReaderFactory configReaderFactory;
    @Mock
    private Set<String> keySet;
    @Mock
    private ConfigValue configValue1;
    @Mock
    private ConfigValue configValue2;
    private ApplicationVariantConfigValueReaderImpl reader;
    private File file = new File("tmp");
    private List<ConfigValue> configValues = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        configValues.add(configValue1);
        configValues.add(configValue2);
        when(configValue1.getKey()).thenReturn("key1");
        when(configValue2.getKey()).thenReturn("key2");
        when(configReader.getConfigValues()).thenReturn(configValues);
        when(keySet.contains(anyString())).thenReturn(false);
        when(configReaderFactory.create(any())).thenReturn(configReader);
        when(configFileProvider.getConfigFiles(anyString())).thenReturn(Collections.emptyList());
        reader = new ApplicationVariantConfigValueReaderImpl(
                configFileProvider,
                configReaderFactory
        );
    }

    @Test
    public void totalConfigValuesEmptyWhenConfigFilesEmpty() {
        assertTrue(reader.getConfigValues(APP_VARIANT).isEmpty());
    }

    @Test
    public void collectUniqueValues() {
        assertEquals(
                EXPECTED_CONFIG_VALUES,
                reader.collectConfigValues(file, keySet).size()
        );
    }

    @Test(expected = UniqueKeyException.class)
    public void throwUniqueKeyExceptionWhenKeySetContainsKey() {
        when(keySet.contains(anyString())).thenReturn(true);
        reader.collectConfigValues(file, keySet);
    }

}