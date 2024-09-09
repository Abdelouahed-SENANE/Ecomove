package ma.youcode.transport.repository;

import java.sql.SQLException;
import java.util.List;

import ma.youcode.transport.entity.SpecialOffer;

public interface SpecialOfferRepository {

    SpecialOffer save(SpecialOffer specialOffer) throws SQLException;

    SpecialOffer update(SpecialOffer specialOffer);

    SpecialOffer delete(SpecialOffer specialOffer);

    SpecialOffer findSpecialOfferById(String offerId);

    List<SpecialOffer> findAllSpecialOffers() throws SQLException;
}
