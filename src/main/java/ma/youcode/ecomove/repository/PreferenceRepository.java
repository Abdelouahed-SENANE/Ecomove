package ma.youcode.ecomove.repository;

import ma.youcode.ecomove.entity.Preference;

import java.util.List;

public interface PreferenceRepository {

    Boolean save(Preference preference);
    List<Preference> findMyPreferences(String email);

}
