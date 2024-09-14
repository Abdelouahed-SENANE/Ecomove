package ma.youcode.transport.repository;

import ma.youcode.transport.entity.Preference;

import java.util.List;

public interface PreferenceRepository {

    Boolean save(Preference preference);
    List<Preference> findMyPreferences(String email);

}
