package ma.youcode.transport.service.implementations;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import ma.youcode.transport.entity.Contract;
import ma.youcode.transport.entity.SpecialOffer;
import ma.youcode.transport.entity.Ticket;
import ma.youcode.transport.enums.ContractStatus;
import ma.youcode.transport.enums.DiscountType;
import ma.youcode.transport.repository.ContractRepository;
import ma.youcode.transport.repository.TicketRepository;
import ma.youcode.transport.repository.implementations.ContractRepositoryImp;
import ma.youcode.transport.repository.implementations.TicketRepositoryImp;

public class TicketServiceImp implements ma.youcode.transport.service.TicketService {
    private final TicketRepository ticketRepository;
    private final ContractRepository contractRepository;

    public TicketServiceImp() {
        this.ticketRepository = new TicketRepositoryImp();
        this.contractRepository = new ContractRepositoryImp();
    }

    @Override
    public Ticket addTicket(Ticket ticket)  {

        ticket.setBoughtFor(calculateBuyingPrice(ticket));
        Ticket savedTicket = this.ticketRepository.save(ticket);
        return savedTicket;
    }

    @Override
    public Ticket updateTicket(Ticket ticket)  {
        return ticketRepository.update(ticket);
    }

    @Override
    public Ticket getTicket(String ticketId)  {
        return ticketRepository.findTicketById(ticketId);
    }

    @Override
    public List<Ticket> getAllTickets()  {
        return ticketRepository.findAllTickets();
    }

    @Override
    public Ticket deleteTicket(String ticketId) throws SQLException {
        Ticket ticket = ticketRepository.findTicketById(ticketId);
        if (ticket != null) {
            ticketRepository.delete(ticket);
        }
        return ticket;
    }

    private  Double calculateBuyingPrice(Ticket ticket) {
        Double buyingPrice = 0.0;
        Contract contract = ticket.getContract();
        if (contract != null) {
                buyingPrice = ticket.getBoughtFor() - (ticket.getBoughtFor() * contract.getSpecialRate());
                if (contract.getSpecialOffers().size() > 0) {
                    Double fixedPrice = 0.0;
                    Double percentage = 0.0;
                    for (SpecialOffer specialOffer : ticket.getContract().getSpecialOffers()) {
                        if (specialOffer.getDiscountType().equals(DiscountType.FIX_AMOUNT)){
                            fixedPrice += specialOffer.getDiscountValue();
                        }else {
                            percentage += specialOffer.getDiscountValue();
                        }
                    }
                    buyingPrice = buyingPrice -  (fixedPrice  + ( buyingPrice * percentage));
                }
        } else {
            System.out.println("Contract not found.");
        }
        return buyingPrice;
    }

}
