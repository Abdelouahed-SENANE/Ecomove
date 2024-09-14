package ma.youcode.transport.service;

import ma.youcode.transport.entity.Preference;

import java.util.List;

public interface PreferenceService {

    Boolean addPreference(Preference preference);
    List<Preference> getMyPreferences();
}
