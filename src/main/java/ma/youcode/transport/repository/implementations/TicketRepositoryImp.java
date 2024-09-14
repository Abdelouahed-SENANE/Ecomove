package ma.youcode.transport.repository.implementations;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ma.youcode.transport.config.Database;
import ma.youcode.transport.entity.Contract;
import ma.youcode.transport.entity.Route;
import ma.youcode.transport.entity.Ticket;
import ma.youcode.transport.enums.TicketStatus;
import ma.youcode.transport.enums.TransportationType;

import javax.xml.crypto.Data;

public class TicketRepositoryImp implements ma.youcode.transport.repository.TicketRepository {

    private final  Database db;

    public TicketRepositoryImp()  {
        this.db = Database.getInstance();
    }

    @Override
    public Ticket save(Ticket ticket) {
        String sql = "INSERT INTO tickets (ticketid, transportationtype, boughtfor, sellingprice, soldat, ticketstatus, contractid , routeid , departuretime , duration) VALUES (?, ?, ?, ?, ?, ?, ?,? , ? ,?)";

        try {
            Connection cn = db.getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);

            stmt.setString(1, ticket.getTicketId());
            stmt.setObject(2, ticket.getTransportationType().name(),Types.OTHER);
            stmt.setDouble(3, ticket.getBoughtFor());
            stmt.setDouble(4, ticket.getSellingPrice());
            stmt.setDate(5,null);
            stmt.setObject(6, TicketStatus.PENDING , Types.OTHER);
            stmt.setString(7, ticket.getContract().getContractId());
            stmt.setString(8, ticket.getRoute().getRouteId());
            stmt.setTimestamp(9 , Timestamp.valueOf(ticket.getDepartureTime()));
            stmt.setDouble(10, ticket.getDuration());

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

            stmt.setObject(1, ticket.getTransportationType().name(), Types.OTHER);
            stmt.setDouble(2, ticket.getBoughtFor());
            stmt.setDouble(3, ticket.getSellingPrice());
            stmt.setDate(4, Date.valueOf(ticket.getSoldAt()));
            stmt.setObject(5, ticket.getTicketStatus().name(), Types.OTHER);
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
                ticket.setSoldAt(rs.getDate("soldat").toLocalDate());
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
        String sql = "SELECT * FROM tickets t "+
                " INNER JOIN routes r ON r.routeid = t.routeid WHERE soldat IS NULL ";

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
                ticket.setDuration(rs.getInt("duration"));
                ticket.setDepartureTime(rs.getTimestamp("departuretime").toLocalDateTime());
                ticket.setTicketStatus(TicketStatus.valueOf(rs.getString("ticketstatus")));
                Route route = new Route();
                route.setRouteId(rs.getString("routeid"));
                route.setDeparture(rs.getString("departure"));
                route.setDestination(rs.getString("destination"));
                route.setDistance(rs.getDouble("distance"));
                ticket.setRoute(route);
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

    @Override
    public Boolean updateStatus(Ticket ticket) {

        String updateStatusSQL = "UPDATE tickets SET ticketstatus = ?, soldat = ? WHERE ticketid = ?";
        try{
            Connection connection = db.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(updateStatusSQL);

                pstmt.setObject(1, TicketStatus.SOLD , java.sql.Types.OTHER);
                pstmt.setTimestamp(2 , Timestamp.valueOf(LocalDateTime.now()));
                pstmt.setString(3, ticket.getTicketId());
                int  affectedRow = pstmt.executeUpdate();

                if (affectedRow > 0) {
                    return true;
                }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
