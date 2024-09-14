package ma.youcode.transport.entity;

import java.util.List;

public class Reservation {
    private String reservationId;
    private String cancelledAt;
    private Passenger passenger;
    private List<Ticket> tickets;

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public String getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(String cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
    public void setTickets(List<Ticket> reservations) {
        this.tickets = reservations;
    }
}
