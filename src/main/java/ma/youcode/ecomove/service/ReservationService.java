package ma.youcode.ecomove.service;

import ma.youcode.ecomove.entity.Reservation;

import java.util.List;

public interface ReservationService {

    Reservation addReservation(Reservation reservation);
    List<Reservation> getAllReservations();
    Boolean markedAsCancelled(Reservation reservation);
}
