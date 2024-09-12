package ma.youcode.transport.service.implementations;

import ma.youcode.transport.entity.Passenger;
import ma.youcode.transport.enums.AuthStatus;
import ma.youcode.transport.repository.AuthRepository;
import ma.youcode.transport.repository.implementations.AuthRepositoryImp;
import ma.youcode.transport.service.AuthService;
import ma.youcode.transport.utils.Session;

public class AuthServiceImp implements AuthService {
    private final AuthRepository authRepository;

    public AuthServiceImp() {
        this.authRepository = new AuthRepositoryImp();
    }
    @Override
    public AuthStatus login(String email) {
        Passenger passenger = authRepository.findByEmail(email);
        if (passenger != null) {
            Session.setLoggedEmail(passenger.getEmail());
            return AuthStatus.SUCCESS_LOGIN;
        }
        return AuthStatus.FAILED_LOGIN;
    }

    @Override
    public AuthStatus register(Passenger passenger) {

        if (authRepository.existsByEmail(passenger.getEmail())) {
            Session.setLoggedEmail(passenger.getEmail());
            return AuthStatus.EMAIL_ALREADY_REGISTERED;
        }
        Passenger savedPassenger = authRepository.save(passenger);
        if (savedPassenger != null) {
            Session.setLoggedEmail(savedPassenger.getEmail());
            return AuthStatus.SUCCESS_REGISTRATION;
        }
        return AuthStatus.FAILED_REGISTRATION;
    }

    @Override
    public AuthStatus updateCustomer(Passenger passenger) {

        Passenger authenticatedPassenger = authRepository.findByEmail(Session.getLoggedEmail());
        if (authenticatedPassenger != null) {
            passenger.setEmail(authenticatedPassenger.getEmail());
            authRepository.update(passenger);
            return AuthStatus.SUCCESS_UPDATE;
        }
        return AuthStatus.ERROR;
    }

}
