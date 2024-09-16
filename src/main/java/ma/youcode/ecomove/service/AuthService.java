package ma.youcode.ecomove.service;

import ma.youcode.ecomove.entity.Passenger;
import ma.youcode.ecomove.enums.AuthStatus;

public interface AuthService {


     AuthStatus login(String email);
     AuthStatus register(Passenger passenger);
     AuthStatus updateCustomer(Passenger passenger);
}
