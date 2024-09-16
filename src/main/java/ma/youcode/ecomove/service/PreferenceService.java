package ma.youcode.ecomove.service;

import ma.youcode.ecomove.entity.Preference;

import java.util.List;

public interface PreferenceService {

    Boolean addPreference(Preference preference);
    List<Preference> getMyPreferences();
}
