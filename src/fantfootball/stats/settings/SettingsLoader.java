package fantfootball.stats.settings;

/**
 * Simple type to load settings. This allows a client to remember the cookie
 * and league key which would be a pain to keep entering.
 * 
 */
public interface SettingsLoader {

    /**
     * Get the settings.
     * 
     * @return a settings object.
     */
    Settings getSettings();

    /**
     * Save the users settings.
     * 
     * @param settings
     *            the settings.
     */
    void setSettings(Settings settings);
}
