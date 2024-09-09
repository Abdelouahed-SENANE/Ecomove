package ma.youcode.transport.repository.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ma.youcode.transport.config.Database;
import ma.youcode.transport.entity.Contract;
import ma.youcode.transport.entity.Partner;
import ma.youcode.transport.entity.SpecialOffer;
import ma.youcode.transport.enums.ContractStatus;
import ma.youcode.transport.enums.DiscountType;
import ma.youcode.transport.enums.OfferStatus;
import ma.youcode.transport.repository.ContractRepository;
import ma.youcode.transport.repository.PartnerRepository;
import ma.youcode.transport.repository.SpecialOfferRepository;

public class ContractRepositoryImp implements ContractRepository {

    private final Database db;
    public ContractRepositoryImp()  {
        this.db = Database.getInstance();
    }

    @Override
    public Contract save(Contract contract)  {
        String sql = "INSERT INTO contracts (contractid, startingdate, enddate, specialrate, agreementconditions, renewable, contractstatus, partnerid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);

            stmt.setString(1, contract.getContractId());
            stmt.setTimestamp(2, contract.getStartingDate());
            stmt.setTimestamp(3, contract.getEndDate());
            stmt.setDouble(4, contract.getSpecialRate());
            stmt.setString(5, contract.getAgreementConditions());
            stmt.setBoolean(6, contract.getRenewable());
            stmt.setObject(7, contract.getContractStatus().name(), java.sql.Types.OTHER);
            stmt.setString(8, contract.getPartner().getPartnerId());

            int affectedRow = stmt.executeUpdate();
            if (affectedRow > 0) {
                return contract;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Contract update(Contract contract) {
        String sql = "UPDATE contracts SET startingdate = ?, enddate = ?, specialrate = ?, agreementconditions = ?, renewable = ?, contractstatus = ? WHERE contractid = ?";
        try  {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);

            stmt.setTimestamp(1, contract.getStartingDate());
            stmt.setTimestamp(2, contract.getEndDate());
            stmt.setDouble(3, contract.getSpecialRate());
            stmt.setString(4, contract.getAgreementConditions());
            stmt.setBoolean(5, contract.getRenewable());
            stmt.setObject(6, contract.getContractStatus().name(), java.sql.Types.OTHER);
            stmt.setString(7, contract.getContractId());

            int affectedRow = stmt.executeUpdate();

            if (affectedRow > 0) {
                return contract;
            } else {
                System.out.println("No rows were updated.");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        return null;
    }


    @Override
    public Contract delete(Contract contract) {
        String sql = "DELETE FROM contracts WHERE contractid = ?";
        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);
            stmt.setString(1, contract.getContractId());

            int affectedRow = stmt.executeUpdate();
            if (affectedRow > 0) {
                return contract;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Contract findContractById(String contractId) {
        Contract contract = null;
        String sql = "SELECT * FROM contracts"
                    + " INNER JOIN partners ON partners.partnerid = contracts.partnerid"
                    + " WHERE contracts.contractid = ? ";
        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);
            stmt.setString(1, contractId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                contract = new Contract();
                contract.setContractId(rs.getString("contractid"));
                contract.setStartingDate(rs.getTimestamp("startingdate"));
                contract.setEndDate(rs.getTimestamp("enddate"));
                contract.setSpecialRate(rs.getDouble("specialrate"));
                contract.setAgreementConditions(rs.getString("agreementconditions"));
                contract.setRenewable(rs.getBoolean("renewable"));

                String contractStatusString = rs.getString("contractstatus");
                ContractStatus contractStatus = ContractStatus.valueOf(contractStatusString);
                contract.setContractStatus(contractStatus);
                Partner partner = new Partner();
                partner.setPartnerId(rs.getString("partnerid"));
                partner.setCompanyName(rs.getString("companyname"));
                contract.setPartner(partner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contract;
    }

    @Override
    public List<Contract> findAllContracts()  {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT * FROM contracts"
                    +" INNER JOIN  partners ON partners.partnerid = contracts.partnerid";

        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Contract contract = new Contract();
                contract.setContractId(rs.getString("contractid"));
                contract.setStartingDate(rs.getTimestamp("startingdate"));
                contract.setEndDate(rs.getTimestamp("enddate"));
                contract.setSpecialRate(rs.getDouble("specialrate"));
                contract.setAgreementConditions(rs.getString("agreementconditions"));
                contract.setRenewable(rs.getBoolean("renewable"));
                String contractStatusString = rs.getString("contractstatus");
                ContractStatus contractStatus = ContractStatus.valueOf(contractStatusString);
                contract.setContractStatus(contractStatus);

                Partner partner = new Partner();
                partner.setPartnerId(rs.getString("partnerid"));
                partner.setCompanyName(rs.getString("companyname"));
                contract.setPartner(partner);

                contracts.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contracts;
    }

    @Override
    public List<Contract> findValidatedContract() {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT * FROM contracts c "+
                "INNER JOIN partners p ON p.partnerid = c.partnerid "+
                "INNER JOIN specialoffers os ON os.contractid = c.contractid "+
                "WHERE c.contractstatus = 'ONGOING' AND os.offerstatus = 'ACTIVE' ";
        try{
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Contract contract = new Contract();
                contract.setContractId(rs.getString("contractid"));
                contract.setStartingDate(rs.getTimestamp("startingdate"));
                contract.setEndDate(rs.getTimestamp("enddate"));
                contract.setSpecialRate(rs.getDouble("specialrate"));
                contract.setAgreementConditions(rs.getString("agreementconditions"));
                contract.setRenewable(rs.getBoolean("renewable"));
                contract.setContractStatus(ContractStatus.valueOf(rs.getString("contractstatus")));
                Partner partner = new Partner();
                partner.setPartnerId(rs.getString("partnerid"));
                partner.setCompanyName(rs.getString("companyname"));
                contract.setPartner(partner);
                List<SpecialOffer> speciaOffers = new ArrayList<>();
                SpecialOffer specialOffer = new SpecialOffer();
                specialOffer.setOfferId(rs.getString("offerid"));
                specialOffer.setOfferName(rs.getString("offername"));
                specialOffer.setOfferStatus(OfferStatus.valueOf(rs.getString("offerstatus")));
                speciaOffers.add(specialOffer);
                contract.setSpecialOffers(speciaOffers);
                contracts.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Contract contract : contracts) {
            System.out.println("Contract Id : " + contract.getContractId());
        }
        return contracts;
    }

    @Override
    public Contract findContractWithSpecialOffers(String contractId) {
        Contract contract = null;
        String sql = "SELECT * FROM contracts"
                + " INNER JOIN specialoffers ON specialoffers.contractid = contracts.contractid"
                + " WHERE contracts.contractid = ? AND specialoffers.offerstatus = 'ACTIVE' ";
        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);
            stmt.setString(1, contractId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                contract = new Contract();
                contract.setContractId(rs.getString("contractid"));
                contract.setStartingDate(rs.getTimestamp("startingdate"));
                contract.setEndDate(rs.getTimestamp("enddate"));
                contract.setSpecialRate(rs.getDouble("specialrate"));
                contract.setAgreementConditions(rs.getString("agreementconditions"));
                contract.setRenewable(rs.getBoolean("renewable"));

                String contractStatusString = rs.getString("contractstatus");
                ContractStatus contractStatus = ContractStatus.valueOf(contractStatusString);
                contract.setContractStatus(contractStatus);
                List<SpecialOffer> specialOffers = new ArrayList<>();
                SpecialOffer specialOffer = new SpecialOffer();
                specialOffer.setOfferId(rs.getString("offerid"));
                specialOffer.setOfferName(rs.getString("offername"));
                specialOffer.setOfferStatus(OfferStatus.valueOf(rs.getString("offerstatus")));
                specialOffer.setDiscountValue(rs.getDouble("discountvalue"));
                specialOffer.setDiscountType(DiscountType.valueOf(rs.getString("discounttype")));
                specialOffers.add(specialOffer);
                contract.setSpecialOffers(specialOffers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contract;
    }
}
