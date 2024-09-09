package ma.youcode.transport.entity;

import java.sql.Timestamp;

import ma.youcode.transport.enums.TicketStatus;
import ma.youcode.transport.enums.TransportationType;

public class Ticket {
    private String ticketId;
    private TransportationType transportationType;
    private double boughtFor;
    private double sellingPrice;
    private Timestamp soldAt;
    private TicketStatus ticketStatus;
    private Contract contract;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public TransportationType getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(TransportationType transportationType) {
        this.transportationType = transportationType;
    }

    public double getBoughtFor() {
        return boughtFor;
    }

    public void setBoughtFor(double boughtFor) {
        this.boughtFor = boughtFor;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Timestamp getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(Timestamp soldAt) {
        this.soldAt = soldAt;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Contract getContract() {
        return contract;
    }
    public void setContract(Contract partner) {
        this.contract = partner; 
    }
}
