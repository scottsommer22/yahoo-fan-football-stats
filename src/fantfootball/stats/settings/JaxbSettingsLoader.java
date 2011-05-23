package fantfootball.stats.settings;

import util.JaxbUtil;


/**
 * This type uses Jaxb to save the settings to XML.
 *
 */
public class JaxbSettingsLoader implements SettingsLoader {

    private static final String SETTINGS_FILE = "config/settings.xml";
    
    @Override
    public Settings getSettings() {

       Settings settings = JaxbUtil.loadFromFile(SETTINGS_FILE, Settings.class);
       return (settings == null) ? new Settings() : settings;
    }

    @Override
    public void setSettings(Settings settings) {
        JaxbUtil.writeToFile(SETTINGS_FILE, settings);
    }

}
