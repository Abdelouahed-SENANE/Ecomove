package ma.youcode.ecomove.service;

import java.util.List;

import ma.youcode.ecomove.entity.SpecialOffer;

public interface SpecialOfferService {
    SpecialOffer addSpecialOffer(SpecialOffer specialOffer) ;
    SpecialOffer updateSpecialOffer(SpecialOffer specialOffer);
    List<SpecialOffer> getAllSpecialOffers();
    SpecialOffer deleteSpecialOffer(String offerId);
    SpecialOffer getSpecialOffer(String offerId);
}
