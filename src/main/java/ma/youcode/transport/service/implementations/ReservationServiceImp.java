package ma.youcode.transport.service.implementations;

import ma.youcode.transport.config.Database;
import ma.youcode.transport.entity.Reservation;
import ma.youcode.transport.entity.Ticket;
import ma.youcode.transport.repository.ReservationRepository;
import ma.youcode.transport.repository.implementations.ReservationRepositoryImp;
import ma.youcode.transport.service.ReservationService;
import ma.youcode.transport.service.TicketService;
import ma.youcode.transport.utils.Session;

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
