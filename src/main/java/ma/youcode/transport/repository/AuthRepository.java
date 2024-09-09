package ma.youcode.transport.repository;

import ma.youcode.transport.entity.Costumer;

public interface AuthRepository {


    Costumer findByEmail(String email);
    Costumer save(Costumer costumer);
    Boolean existsByEmail(String email);

}
