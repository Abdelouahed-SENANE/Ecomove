package ma.youcode.ecomove.service.implementations;

import ma.youcode.ecomove.entity.Preference;
import ma.youcode.ecomove.repository.PreferenceRepository;
import ma.youcode.ecomove.repository.implementations.PreferenceRepositoryImp;
import ma.youcode.ecomove.service.PreferenceService;
import ma.youcode.ecomove.utils.Session;

import java.util.List;

public class PreferenceServiceImp implements PreferenceService {
    private final PreferenceRepository preferenceRepository;
    public PreferenceServiceImp() {
        this.preferenceRepository = new PreferenceRepositoryImp();
    }
    @Override
    public Boolean addPreference(Preference preference) {
        return preferenceRepository.save(preference);
    }

    @Override
    public List<Preference> getMyPreferences() {
        String loggedEmail = Session.getLoggedEmail();
        return preferenceRepository.findMyPreferences(loggedEmail);
    }
}
