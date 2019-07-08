package by.bulba.android.environments.config;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ConfigValueTest {

    private final ConfigValue actual = new ConfigValue(
            "key",
            ConfigType.BOOLEAN,
            "true"
    );

    @Test
    public void checkEquals() {
        ConfigValue expected = new ConfigValue("key", ConfigType.BOOLEAN, "true");
        assertEquals(expected, actual);
    }

    @Test
    public void checkNotEquals() {
        ConfigValue expected = new ConfigValue("key", ConfigType.STRING, "true");
        assertNotEquals(expected, actual);
    }

    @Test
    public void checkHashCode() {
        int expected = actual.getKey().hashCode() + actual.getValue().hashCode();
        assertEquals(expected, actual.hashCode());
    }

    @Test
    public void checkToString() {
        String text = actual.toString();
        assertTrue(
                text.contains("true") &&
                        text.contains("key") &&
                        text.contains("Boolean")
        );
    }

}