package ma.youcode.transport.service;

import java.sql.SQLException;
import java.util.List;

import ma.youcode.transport.entity.SpecialOffer;

public interface SpecialOfferService {
    SpecialOffer addSpecialOffer(SpecialOffer specialOffer) ;
    SpecialOffer updateSpecialOffer(SpecialOffer specialOffer);
    List<SpecialOffer> getAllSpecialOffers();
    SpecialOffer deleteSpecialOffer(String offerId);
    SpecialOffer getSpecialOffer(String offerId);
}
