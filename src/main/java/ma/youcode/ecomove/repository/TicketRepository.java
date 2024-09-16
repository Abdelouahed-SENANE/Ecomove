package ma.youcode.ecomove.repository;

import java.util.List;

import ma.youcode.ecomove.entity.Ticket;

public interface TicketRepository {

    Ticket save(Ticket ticket);

    Ticket update(Ticket ticket);

    Ticket delete(Ticket ticket);

    Ticket findTicketById(String ticketId);

    List<Ticket> findAllTickets();
    Boolean updateStatus(Ticket ticket);

}
