package ma.youcode.transport.service;

import java.sql.SQLException;
import java.util.List;

import ma.youcode.transport.entity.Partner;

public interface PartenerService {
    public Partner addPartner(Partner partner) throws SQLException;
    public Partner updatePartner(Partner partner) throws SQLException;
    public List<Partner> getAllPartners() throws SQLException;
    public Partner deletePartner(String PartnerId) throws SQLException;
    public Partner getPartner(String partnerId) throws SQLException;
}
