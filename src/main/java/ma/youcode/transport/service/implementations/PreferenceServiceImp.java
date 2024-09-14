package ma.youcode.transport.service.implementations;

import ma.youcode.transport.entity.Preference;
import ma.youcode.transport.repository.PreferenceRepository;
import ma.youcode.transport.repository.implementations.PreferenceRepositoryImp;
import ma.youcode.transport.service.PreferenceService;
import ma.youcode.transport.utils.Session;

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
