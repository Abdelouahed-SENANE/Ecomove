package ma.youcode.transport.service;

import java.sql.SQLException;
import java.util.List;

import ma.youcode.transport.entity.Ticket;

public interface TicketService {
    Ticket addTicket(Ticket ticket);
    Ticket updateTicket(Ticket ticket);
    List<Ticket> getAllTickets();
    Ticket deleteTicket(String ticketId);
    Ticket getTicket(String ticketId);
}
