package ma.youcode.ecomove.repository.implementations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ma.youcode.ecomove.config.Database;
import ma.youcode.ecomove.entity.Contract;
import ma.youcode.ecomove.entity.SpecialOffer;
import ma.youcode.ecomove.enums.DiscountType;
import ma.youcode.ecomove.enums.OfferStatus;

public class SpecialOfferRepositoryImp implements ma.youcode.ecomove.repository.SpecialOfferRepository {

    private final  Database db;

    public SpecialOfferRepositoryImp()  {
        this.db = Database.getInstance();
    }

    @Override
    public SpecialOffer save(SpecialOffer specialOffer)  {
        String sql = "INSERT INTO specialoffers (offerid, offername, offerdescription, startingdate, enddate, discounttype, discountvalue, conditions, offerstatus, contractid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);

            stmt.setString(1, specialOffer.getOfferId());
            stmt.setString(2, specialOffer.getOfferName());
            stmt.setString(3, specialOffer.getOfferDescription());
            stmt.setDate(4, Date.valueOf(specialOffer.getStartingDate()));
            stmt.setDate(5, Date.valueOf(specialOffer.getEndDate()));
            stmt.setObject(6, specialOffer.getDiscountType().name(), java.sql.Types.OTHER);
            stmt.setDouble(7, specialOffer.getDiscountValue());
            stmt.setString(8, specialOffer.getConditions());
            stmt.setObject(9, specialOffer.getOfferStatus().name(), java.sql.Types.OTHER);
            stmt.setString(10, specialOffer.getContract().getContractId());

            int affectedRow = stmt.executeUpdate();
            if (affectedRow > 0) {
                return specialOffer;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SpecialOffer update(SpecialOffer specialOffer) {
        String sql = "UPDATE specialoffers SET offername = ?, offerdescription = ?, startingdate = ?, enddate = ?, discounttype = ?, discountvalue = ?, conditions = ?, offerstatus = ? WHERE offerid = ?";
        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);

            stmt.setString(1, specialOffer.getOfferName());
            stmt.setString(2, specialOffer.getOfferDescription());
            stmt.setDate(3, Date.valueOf(specialOffer.getStartingDate()));
            stmt.setDate(4, Date.valueOf(specialOffer.getEndDate()));
            stmt.setObject(5, specialOffer.getDiscountType().name(), java.sql.Types.OTHER);
            stmt.setDouble(6, specialOffer.getDiscountValue());
            stmt.setString(7, specialOffer.getConditions());
            stmt.setObject(8, specialOffer.getOfferStatus().name(), java.sql.Types.OTHER);
            stmt.setString(9, specialOffer.getOfferId());

            int affectedRow = stmt.executeUpdate();
            if (affectedRow > 0) {
                return specialOffer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SpecialOffer delete(SpecialOffer specialOffer) {
        String sql = "DELETE FROM specialoffers WHERE offerid = ?";
        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);
            stmt.setString(1, specialOffer.getOfferId());

            int affectedRow = stmt.executeUpdate();
            if (affectedRow > 0) {
                return specialOffer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SpecialOffer findSpecialOfferById(String offerId) {
        SpecialOffer specialOffer = null;
        String sql = "SELECT * FROM specialoffers WHERE offerid = ?";
        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);
            stmt.setString(1, offerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                specialOffer = new SpecialOffer();
                specialOffer.setOfferId(rs.getString("offerid"));
                specialOffer.setOfferName(rs.getString("offername"));
                specialOffer.setOfferDescription(rs.getString("offerdescription"));
                specialOffer.setStartingDate(rs.getDate("startingdate").toLocalDate());
                specialOffer.setEndDate(rs.getDate("enddate").toLocalDate());
                specialOffer.setDiscountValue(rs.getDouble("discountvalue"));
                specialOffer.setConditions(rs.getString("conditions"));

                String discountTypeString = rs.getString("discounttype");
                DiscountType discountType = DiscountType.valueOf(discountTypeString);
                specialOffer.setDiscountType(discountType);

                String offerStatusString = rs.getString("offerstatus");
                OfferStatus offerStatus = OfferStatus.valueOf(offerStatusString);
                specialOffer.setOfferStatus(offerStatus);

                // Assuming a method exists to retrieve Contract by ID
                ContractRepositoryImp contractRepo = new ContractRepositoryImp();
                Contract contract = contractRepo.findContractById(rs.getString("contractid"));
                specialOffer.setContract(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialOffer;
    }

    @Override
    public List<SpecialOffer> findAllSpecialOffers()  {
        List<SpecialOffer> specialOffers = new ArrayList<>();
        String sql = "SELECT * FROM specialoffers";

        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SpecialOffer specialOffer = new SpecialOffer();
                specialOffer.setOfferId(rs.getString("offerid"));
                specialOffer.setOfferName(rs.getString("offername"));
                specialOffer.setOfferDescription(rs.getString("offerdescription"));
                specialOffer.setStartingDate(rs.getDate("startingdate").toLocalDate());
                specialOffer.setEndDate(rs.getDate("enddate").toLocalDate());
                specialOffer.setDiscountValue(rs.getDouble("discountvalue"));
                specialOffer.setConditions(rs.getString("conditions"));

                String discountTypeString = rs.getString("discounttype");
                DiscountType discountType = DiscountType.valueOf(discountTypeString);
                specialOffer.setDiscountType(discountType);

                String offerStatusString = rs.getString("offerstatus");
                OfferStatus offerStatus = OfferStatus.valueOf(offerStatusString);
                specialOffer.setOfferStatus(offerStatus);

                // Assuming a method exists to retrieve Contract by ID
                ContractRepositoryImp contractRepo = new ContractRepositoryImp();
                Contract contract = contractRepo.findContractById(rs.getString("contractid"));
                specialOffer.setContract(contract);

                specialOffers.add(specialOffer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialOffers;
    }
}
