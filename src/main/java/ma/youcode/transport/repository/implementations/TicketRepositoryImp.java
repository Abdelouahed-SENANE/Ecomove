package ma.youcode.transport.repository.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ma.youcode.transport.config.Database;
import ma.youcode.transport.entity.Contract;
import ma.youcode.transport.entity.Ticket;
import ma.youcode.transport.enums.TicketStatus;
import ma.youcode.transport.enums.TransportationType;

public class TicketRepositoryImp implements ma.youcode.transport.repository.TicketRepository {

    private final  Database db;

    public TicketRepositoryImp()  {
        this.db = Database.getInstance();
    }

    @Override
    public Ticket save(Ticket ticket) {
        String sql = "INSERT INTO tickets (ticketid, transportationtype, boughtfor, sellingprice, soldat, ticketstatus, contractid) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);

            stmt.setString(1, ticket.getTicketId());
            stmt.setObject(2, ticket.getTransportationType().name(), java.sql.Types.OTHER);
            stmt.setDouble(3, ticket.getBoughtFor());
            stmt.setDouble(4, ticket.getSellingPrice());
            stmt.setTimestamp(5, ticket.getSoldAt());
            stmt.setObject(6, ticket.getTicketStatus().name(), java.sql.Types.OTHER);
            stmt.setString(7, ticket.getContract().getContractId());

            int affectedRow = stmt.executeUpdate();
            if (affectedRow > 0) {
                return ticket;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Ticket update(Ticket ticket) {
        String sql = "UPDATE tickets SET transportationtype = ?, boughtfor = ?, sellingprice = ?, soldat = ?, ticketstatus = ? WHERE ticketid = ?";
        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);

            stmt.setObject(1, ticket.getTransportationType().name(), java.sql.Types.OTHER);
            stmt.setDouble(2, ticket.getBoughtFor());
            stmt.setDouble(3, ticket.getSellingPrice());
            stmt.setTimestamp(4, ticket.getSoldAt());
            stmt.setObject(5, ticket.getTicketStatus().name(), java.sql.Types.OTHER);
            stmt.setString(6, ticket.getTicketId());

            int affectedRow = stmt.executeUpdate();
            if (affectedRow > 0) {
                return ticket;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Ticket delete(Ticket ticket) {
        String sql = "DELETE FROM tickets WHERE ticketid = ?";
        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);
            stmt.setString(1, ticket.getTicketId());

            int affectedRow = stmt.executeUpdate();
            if (affectedRow > 0) {
                return ticket;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Ticket findTicketById(String ticketId) {
        Ticket ticket = null;
        String sql = "SELECT * FROM tickets WHERE ticketid = ?";
        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);
            stmt.setString(1, ticketId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ticket = new Ticket();
                ticket.setTicketId(rs.getString("ticketid"));
                ticket.setTransportationType(TransportationType.valueOf(rs.getString("transportationtype")));
                ticket.setBoughtFor(rs.getDouble("boughtfor"));
                ticket.setSellingPrice(rs.getDouble("sellingprice"));
                ticket.setSoldAt(rs.getTimestamp("soldat"));
                ticket.setTicketStatus(TicketStatus.valueOf(rs.getString("ticketstatus")));

                // Assuming a method exists to retrieve Contract by ID
                ContractRepositoryImp contractRepo = new ContractRepositoryImp();
                Contract contract = contractRepo.findContractById(rs.getString("contractid"));
                ticket.setContract(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    @Override
    public List<Ticket> findAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets";

        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(rs.getString("ticketid"));
                ticket.setTransportationType(TransportationType.valueOf(rs.getString("transportationtype")));
                ticket.setBoughtFor(rs.getDouble("boughtfor"));
                ticket.setSellingPrice(rs.getDouble("sellingprice"));
                ticket.setSoldAt(rs.getTimestamp("soldat"));
                ticket.setTicketStatus(TicketStatus.valueOf(rs.getString("ticketstatus")));

                // Assuming a method exists to retrieve Contract by ID
                ContractRepositoryImp contractRepo = new ContractRepositoryImp();
                Contract contract = contractRepo.findContractById(rs.getString("contractid"));
                ticket.setContract(contract);

                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
}
