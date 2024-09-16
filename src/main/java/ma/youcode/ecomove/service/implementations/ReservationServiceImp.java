package ma.youcode.ecomove.service.implementations;

import ma.youcode.ecomove.entity.Reservation;
import ma.youcode.ecomove.entity.Ticket;
import ma.youcode.ecomove.repository.ReservationRepository;
import ma.youcode.ecomove.repository.implementations.ReservationRepositoryImp;
import ma.youcode.ecomove.service.ReservationService;
import ma.youcode.ecomove.service.TicketService;
import ma.youcode.ecomove.utils.Session;

import java.util.List;

public class ReservationServiceImp implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final TicketService ticketService;
    public ReservationServiceImp() {
        this.reservationRepository = new ReservationRepositoryImp();
        this.ticketService = new TicketServiceImp();
    }
    @Override
    public Reservation addReservation(Reservation reservation) {
        Reservation savedReservation = reservationRepository.save(reservation);
        if (savedReservation != null) {
            this.reservationRepository.saveReservationOfTicket(savedReservation);
                for (Ticket ticket : savedReservation.getTickets()) {
                    this.ticketService.markAsSold(ticket);
                }
            return savedReservation;
        }
        return null;
    }

    @Override
    public List<Reservation> getAllReservations() {
        String loggedEmail = Session.getLoggedEmail();
        return this.reservationRepository.findMyReservations(loggedEmail);
    }

    @Override
    public Boolean markedAsCancelled(Reservation reservation) {
        return this.reservationRepository.updateCancelledAt(reservation);
    }
}
