package ma.youcode.transport.service;

import java.sql.SQLException;
import java.util.List;

import ma.youcode.transport.entity.Partner;

public interface PartenerService {
    public Partner addPartner(Partner partner);
    public Partner updatePartner(Partner partner);
    public List<Partner> getAllPartners();
    public Partner deletePartner(String PartnerId);
    public Partner getPartner(String partnerId);
}
