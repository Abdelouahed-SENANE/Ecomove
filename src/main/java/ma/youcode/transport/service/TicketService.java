package ma.youcode.transport.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import ma.youcode.transport.entity.Ticket;

public interface TicketService {
    Ticket addTicket(Ticket ticket);
    Ticket updateTicket(Ticket ticket);
    List<Ticket> getAllTickets();
    Ticket deleteTicket(String ticketId);
    Ticket getTicket(String ticketId);
    List<List<Ticket>> availbeJourneys(String departure, String destination, LocalDateTime departureDateTime);
    Boolean markAsSold( Ticket ticket);
}
