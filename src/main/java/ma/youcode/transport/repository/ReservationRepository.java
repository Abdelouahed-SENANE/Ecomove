package ma.youcode.transport.repository;

import ma.youcode.transport.entity.Reservation;

import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);
    boolean saveReservationOfTicket(Reservation reservation);
    List<Reservation> findMyReservations(String email);
    boolean updateCancelledAt(Reservation reservation);
}
