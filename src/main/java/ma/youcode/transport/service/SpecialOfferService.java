package ma.youcode.transport.service;

import java.sql.SQLException;
import java.util.List;

import ma.youcode.transport.entity.SpecialOffer;

public interface SpecialOfferService {
    SpecialOffer addSpecialOffer(SpecialOffer specialOffer) throws SQLException;
    SpecialOffer updateSpecialOffer(SpecialOffer specialOffer) throws SQLException;
    List<SpecialOffer> getAllSpecialOffers() throws SQLException;
    SpecialOffer deleteSpecialOffer(String offerId) throws SQLException;
    SpecialOffer getSpecialOffer(String offerId) throws SQLException;
}
