package ma.youcode.ecomove.repository.implementations;

import ma.youcode.ecomove.config.Database;
import ma.youcode.ecomove.entity.*;
import ma.youcode.ecomove.repository.ReservationRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationRepositoryImp implements ReservationRepository {

    private final Database database;

    public ReservationRepositoryImp() {
        this.database = Database.getInstance();
    }

    @Override
    public Reservation save(Reservation reservation) {

        try{
            Connection connection = database.getConnection();

            String reservationSql = "INSERT INTO reservations (reservationid , cancelledat, passengeremail) VALUES (?, ?, ?)";
            PreparedStatement reservationPstmt = connection.prepareStatement(reservationSql);

            reservationPstmt.setString(1, reservation.getReservationId());
            reservationPstmt.setTimestamp(2 , null);
            reservationPstmt.setString(3, reservation.getPassenger().getEmail());
            reservationPstmt.executeUpdate();

            return reservation;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveReservationOfTicket(Reservation reservation ) {
        int affectedRows = 0;
        String insertPivotTable = "INSERT INTO reservationsoftickets (ticketid , reservationid) VALUES (? , ?)";
        try{
            Connection connection = database.getConnection();
            PreparedStatement insertPivotPstmt = connection.prepareStatement(insertPivotTable);

            for (Ticket reservedTicket : reservation.getTickets()) {
                insertPivotPstmt.setString(1, reservedTicket.getTicketId());
                insertPivotPstmt.setString(2, reservation.getReservationId());
                affectedRows = insertPivotPstmt.executeUpdate();
            }
            if (affectedRows > 0 ) {
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Reservation> findMyReservations(String email) {
        Map<String, Reservation> mapReservations = new HashMap<>();
        String query =  "SELECT r.reservationid, t.*, p.* , routes.*, contracts.* , partners.*  FROM reservations r "
                        + " INNER JOIN  passengers p ON p.email = r.passengeremail "
                        + " INNER JOIN  reservationsoftickets rot  ON rot.reservationid = r.reservationid "
                        + " INNER JOIN  tickets t  ON t.ticketid = rot.ticketid "
                        + " INNER JOIN  routes   ON t.routeid = routes.routeid "
                        + " INNER JOIN  contracts  ON contracts.contractid = t.contractid "
                        + " INNER JOIN  partners  ON partners.partnerid = contracts.partnerid "
                         + " WHERE p.email = ? AND cancelledat IS NULL ";

        try{
            Connection cn = database.getConnection();
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            Reservation reservation;
            while (rs.next()) {
                String reservationId = rs.getString("reservationid");
                if (mapReservations.containsKey(reservationId)) {
                    reservation = mapReservations.get(reservationId);
                }else {
                    reservation = new Reservation();
                    reservation.setReservationId(reservationId);
                    Passenger passenger = new Passenger();
                    passenger.setEmail(rs.getString("email"));
                    passenger.setFirstName(rs.getString("firstname"));
                    passenger.setFirstName(rs.getString("familyname"));
                    reservation.setPassenger(passenger);
                    reservation.setTickets(new ArrayList<>());
                    mapReservations.put(reservationId, reservation);
                }

                Ticket ticket = new Ticket();
                ticket.setTicketId(rs.getString("ticketid"));
                ticket.setDepartureTime(rs.getTimestamp("departuretime").toLocalDateTime());
                ticket.setDuration(rs.getInt("duration"));
                Route route = new Route();
                route.setRouteId(rs.getString("routeid"));
                route.setDestination(rs.getString("destination"));
                route.setDeparture(rs.getString("departure"));
                ticket.setRoute(route);

                Contract contract = new Contract();
                contract.setContractId(rs.getString("contractid"));

                Partner partner = new Partner();
                partner.setPartnerId(rs.getString("partnerid"));
                partner.setCompanyName(rs.getString("companyname"));
                contract.setPartner(partner);

                ticket.setContract(contract);

                reservation.getTickets().add(ticket);

            }
            return new ArrayList<>(mapReservations.values());
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean updateCancelledAt(Reservation reservation) {
            String SQL = "UPDATE reservations SET cancelledat = ? WHERE reservationid = ? ";

            try {
                Connection connection = database.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(SQL);
                pstmt.setTimestamp(1 , Timestamp.valueOf(LocalDateTime.now()));
                pstmt.setString(2, reservation.getReservationId());

                int affectedRow = pstmt.executeUpdate();
                if (affectedRow > 0) {
                    return true;
                }

            }catch (SQLException e) {
                e.printStackTrace();
            }
        return false;
    }
}
