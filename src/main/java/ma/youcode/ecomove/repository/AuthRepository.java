package ma.youcode.ecomove.repository;

import ma.youcode.ecomove.entity.Passenger;

public interface AuthRepository {


    Passenger findByEmail(String email);
    Passenger save(Passenger passenger);
    Boolean existsByEmail(String email);
    Passenger update(Passenger passenger);
}
