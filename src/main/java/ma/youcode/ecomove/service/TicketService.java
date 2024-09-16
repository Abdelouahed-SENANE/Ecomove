package ma.youcode.ecomove.service;

import java.time.LocalDateTime;
import java.util.List;

import ma.youcode.ecomove.entity.Ticket;
import ma.youcode.ecomove.enums.TransportationType;

public interface TicketService {
    Ticket addTicket(Ticket ticket);
    Ticket updateTicket(Ticket ticket);
    List<Ticket> getAllTickets();
    Ticket deleteTicket(String ticketId);
    Ticket getTicket(String ticketId);
    List<List<Ticket>> availbeJourneys(String departure, String destination, LocalDateTime departureDateTime , TransportationType type);
    Boolean markAsSold( Ticket ticket);
}
