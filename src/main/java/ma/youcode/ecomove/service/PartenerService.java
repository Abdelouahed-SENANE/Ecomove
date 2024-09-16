package ma.youcode.ecomove.service;

import java.util.List;

import ma.youcode.ecomove.entity.Partner;

public interface PartenerService {
    public Partner addPartner(Partner partner);
    public Partner updatePartner(Partner partner);
    public List<Partner> getAllPartners();
    public Partner deletePartner(String PartnerId);
    public Partner getPartner(String partnerId);
}
