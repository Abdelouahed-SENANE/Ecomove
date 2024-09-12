package ma.youcode.transport.repository;

import ma.youcode.transport.entity.Passenger;

public interface AuthRepository {


    Passenger findByEmail(String email);
    Passenger save(Passenger passenger);
    Boolean existsByEmail(String email);
    Passenger update(Passenger passenger);
}
