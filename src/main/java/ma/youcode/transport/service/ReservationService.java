package ma.youcode.transport.service;

import ma.youcode.transport.entity.Reservation;

import java.util.List;

public interface ReservationService {

    Reservation addReservation(Reservation reservation);
    List<Reservation> getAllReservations();
    Boolean markedAsCancelled(Reservation reservation);
}
