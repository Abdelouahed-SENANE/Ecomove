package ma.youcode.transport.service;

import ma.youcode.transport.entity.Passenger;
import ma.youcode.transport.enums.AuthStatus;

public interface AuthService {


     AuthStatus login(String email);
     AuthStatus register(Passenger passenger);
     AuthStatus updateCustomer(Passenger passenger);
}
