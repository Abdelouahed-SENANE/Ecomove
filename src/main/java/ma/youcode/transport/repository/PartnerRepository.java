package ma.youcode.transport.repository;

import java.sql.SQLException;
import java.util.List;

import ma.youcode.transport.entity.Partner;

public interface PartnerRepository {

    Partner save(Partner partner) throws SQLException;

    Partner update(Partner partner);

    Partner delete(Partner partner);

    Partner findPartnerById(String partnerId);

    List<Partner> findAllPartners() throws SQLException;

}
