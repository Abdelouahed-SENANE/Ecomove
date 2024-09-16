package ma.youcode.ecomove.repository;

import java.util.List;

import ma.youcode.ecomove.entity.Partner;

public interface PartnerRepository {

    Partner save(Partner partner);

    Partner update(Partner partner);

    Partner delete(Partner partner);

    Partner findPartnerById(String partnerId);

    List<Partner> findAllPartners();

}
