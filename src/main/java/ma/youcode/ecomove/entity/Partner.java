package ma.youcode.ecomove.entity;

import java.sql.Timestamp;
import java.util.List;

import ma.youcode.ecomove.enums.PartnershipStatus;
import ma.youcode.ecomove.enums.TransportationType;

public class Partner {
    private String partnerId;
    private String companyName;
    private String commercialContact;
    private TransportationType transportationType;
    private String geographicZone;
    private String specialConditions;
    private PartnershipStatus partnershipStatus;
    private Timestamp creationDate;
    private List<Contract> contracts;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
        
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCommercialContact() {
        return commercialContact;
    }

    public void setCommercialContact(String commercialContact) {
        this.commercialContact = commercialContact;
    }

    public TransportationType getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(TransportationType transportationType) {
        this.transportationType = transportationType;
    }

    public String getGeographicZone() {
        return geographicZone;
    }

    public void setGeographicZone(String geographicZone) {
        this.geographicZone = geographicZone;
    }

    public String getSpecialConditions() {
        return specialConditions;
    }

    public void setSpecialConditions(String specialConditions) {
        this.specialConditions = specialConditions;
    }

    public PartnershipStatus getPartnerStatus() {
        return partnershipStatus;
    }

    public void setPartnerStatus(PartnershipStatus partnershipStatus) {
        this.partnershipStatus = partnershipStatus;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public List<Contract> getContracts() {
        return contracts;
    }
    public void setContract(List<Contract> contracts) {
        this.contracts = contracts;
    }
}
