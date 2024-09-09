package ma.youcode.transport.service;

import java.sql.SQLException;
import java.util.List;

import ma.youcode.transport.entity.Ticket;

public interface TicketService {
    Ticket addTicket(Ticket ticket) throws SQLException;
    Ticket updateTicket(Ticket ticket) throws SQLException;
    List<Ticket> getAllTickets() throws SQLException;
    Ticket deleteTicket(String ticketId) throws SQLException;
    Ticket getTicket(String ticketId) throws SQLException;
}
