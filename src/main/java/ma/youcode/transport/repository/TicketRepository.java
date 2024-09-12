package ma.youcode.transport.repository;

import java.sql.SQLException;
import java.util.List;

import ma.youcode.transport.entity.Ticket;

public interface TicketRepository {

    Ticket save(Ticket ticket);

    Ticket update(Ticket ticket);

    Ticket delete(Ticket ticket);

    Ticket findTicketById(String ticketId);

    List<Ticket> findAllTickets();


}
