package ma.youcode.ecomove.repository;

import java.util.List;

import ma.youcode.ecomove.entity.SpecialOffer;

public interface SpecialOfferRepository {

    SpecialOffer save(SpecialOffer specialOffer);

    SpecialOffer update(SpecialOffer specialOffer);

    SpecialOffer delete(SpecialOffer specialOffer);

    SpecialOffer findSpecialOfferById(String offerId);

    List<SpecialOffer> findAllSpecialOffers();
}
