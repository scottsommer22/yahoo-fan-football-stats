package fantfootball;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Test;

import fantfootball.stats.settings.Settings;

import util.JaxbUtil;

public class SettingsTest {

    @Test
    public void marshall() {

        Settings settings = new Settings("l", "c", 1, 2, "stats.csv");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        
        JaxbUtil.marshall(output, settings);

        InputStream input = new ByteArrayInputStream(output.toByteArray());
        
        Settings loaded = JaxbUtil.unmarshall(input, Settings.class);
        
        Assert.assertEquals(settings.getCookie(), loaded.getCookie());
        
    }
}
