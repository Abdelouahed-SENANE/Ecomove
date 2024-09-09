package ma.youcode.transport.repository.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ma.youcode.transport.config.Database;
import ma.youcode.transport.entity.Partner;
import ma.youcode.transport.enums.PartnershipStatus;
import ma.youcode.transport.enums.TransportationType;

public class PartnerRepositoryImp implements ma.youcode.transport.repository.PartnerRepository {

    private final  Database db;

    public PartnerRepositoryImp()  {
        this.db = Database.getInstance();
    }

    @Override
    public Partner save(Partner partner)  {

        String sql = "INSERT INTO partners (partnerid , companyname , commercialcontact , transportationtype , geographiczone , specialconditions , partnershipstatus , creationdate) VALUES (? , ? , ? , ? , ? , ? , ? , ?)";

        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);

            stmt.setString(1, partner.getPartnerId());
            stmt.setString(2, partner.getCompanyName());
            stmt.setString(3, partner.getCommercialContact());
            stmt.setObject(4, partner.getTransportationType().name(), java.sql.Types.OTHER);
            stmt.setString(5, partner.getGeographicZone());
            stmt.setString(6, partner.getSpecialConditions());
            stmt.setObject(7, partner.getPartnerStatus().name(), java.sql.Types.OTHER);
            stmt.setTimestamp(8, partner.getCreationDate());

            int affectedRow = stmt.executeUpdate();
            if (affectedRow > 0) {
               return partner;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Partner delete(Partner partner) {
        
        String sql = "DELETE FROM partners WHERE partnerid = ?";
        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);
            stmt.setString(1, partner.getPartnerId());

            int affectedRow = stmt.executeUpdate();
            if (affectedRow > 0) {
                return partner;
            } 
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return null;
    }

    @Override
    public Partner update(Partner partner) {

        String sql = "UPDATE partners SET companyname = ?, commercialcontact = ?, transportationtype = ?, geographiczone = ?, specialconditions = ?, partnershipstatus = ? WHERE partnerid = ?";
        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);

            stmt.setString(1, partner.getCompanyName());
            stmt.setString(2, partner.getCommercialContact());
            stmt.setObject(3, partner.getTransportationType().name(), java.sql.Types.OTHER);
            stmt.setString(4, partner.getGeographicZone());
            stmt.setString(5, partner.getSpecialConditions());
            stmt.setObject(6, partner.getPartnerStatus().name(), java.sql.Types.OTHER);
            stmt.setString(7, partner.getPartnerId());

            int affectedRow = stmt.executeUpdate();
            if (affectedRow > 0) {
                return partner;
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return null;
    }

    @Override
    public Partner findPartnerById(String partnerId) {
        Partner partner = null;
        String sql = "SELECT * FROM partners WHERE partnerid = ? ";
        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);
            stmt.setString(1, partnerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                partner = new Partner();
                partner.setPartnerId(rs.getString("partnerid"));
                partner.setCompanyName(rs.getString("companyname"));
                partner.setCommercialContact(rs.getString("commercialcontact"));

                String transportationTypeString = rs.getString("transportationtype");
                TransportationType transportationType = TransportationType.valueOf(transportationTypeString);
                partner.setTransportationType(transportationType);

                partner.setGeographicZone(rs.getString("geographiczone"));
                partner.setSpecialConditions(rs.getString("specialconditions"));

                String partnershipStatusString = rs.getString("partnershipstatus");
                PartnershipStatus partnershipStatus = PartnershipStatus.valueOf(partnershipStatusString);
                partner.setPartnerStatus(partnershipStatus);

                partner.setCreationDate(rs.getTimestamp("creationdate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partner;
    }

    @Override
    public List<Partner> findAllPartners() {
        List<Partner> partners = new ArrayList<>();

        try {
            String sql = "SELECT * FROM partners";

            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Partner partner = new Partner();
                partner.setPartnerId(rs.getString("partnerid"));
                partner.setCompanyName(rs.getString("companyname"));
                partner.setCommercialContact(rs.getString("commercialcontact"));
                String transportationTypeString = rs.getString("transportationtype");
                TransportationType transportationType = TransportationType.valueOf(transportationTypeString);
                partner.setTransportationType(transportationType);
                partner.setGeographicZone(rs.getString("geographiczone"));
                partner.setSpecialConditions(rs.getString("specialconditions"));
                String partnershipStatusString = rs.getString("partnershipstatus");
                PartnershipStatus partnershipStatus = PartnershipStatus.valueOf(partnershipStatusString);
                partner.setPartnerStatus(partnershipStatus);
                partner.setCreationDate(rs.getTimestamp("creationdate"));

                partners.add(partner);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving partners: " + e.getMessage());
            e.printStackTrace();
        }
        return partners;
    }

}
