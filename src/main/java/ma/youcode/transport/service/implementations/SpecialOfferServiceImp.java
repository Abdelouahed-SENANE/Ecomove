package ma.youcode.transport.service.implementations;

import java.sql.SQLException;
import java.util.List;

import ma.youcode.transport.entity.SpecialOffer;
import ma.youcode.transport.repository.SpecialOfferRepository;
import ma.youcode.transport.repository.implementations.SpecialOfferRepositoryImp;

public class SpecialOfferServiceImp implements ma.youcode.transport.service.SpecialOfferService {
    private SpecialOfferRepository repository;

    public SpecialOfferServiceImp() throws SQLException {
        repository = new SpecialOfferRepositoryImp();
    }

    @Override
    public SpecialOffer addSpecialOffer(SpecialOffer specialOffer) throws SQLException {
        return repository.save(specialOffer);
    }

    @Override
    public SpecialOffer updateSpecialOffer(SpecialOffer specialOffer) throws SQLException {
        return repository.update(specialOffer);
    }

    @Override
    public List<SpecialOffer> getAllSpecialOffers() throws SQLException {
        return repository.findAllSpecialOffers();
    }

    @Override
    public SpecialOffer deleteSpecialOffer(String offerId) throws SQLException {
        SpecialOffer specialOffer = repository.findSpecialOfferById(offerId);
        if (specialOffer != null) {
            repository.delete(specialOffer);
        }
        return specialOffer;
    }

    @Override
    public SpecialOffer getSpecialOffer(String offerId) throws SQLException {
        return repository.findSpecialOfferById(offerId);
    }
}
