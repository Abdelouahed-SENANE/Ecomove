package ma.youcode.ecomove.repository.implementations;

import ma.youcode.ecomove.config.Database;
import ma.youcode.ecomove.entity.Route;
import ma.youcode.ecomove.repository.RouteRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RouteRepositoryImp implements RouteRepository {
    private final Database database;

    public RouteRepositoryImp() {
        this.database = Database.getInstance();
    }
    @Override
    public Boolean save(Route route) {
        String sql = "INSERT INTO routes (routeId , departure , destination , distance) VALUES (? , ? ,? ,?)";
        try{
            Connection connection = database.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, route.getRouteId());
            pstmt.setString(2, route.getDeparture());
            pstmt.setString(3, route.getDestination());
            pstmt.setDouble(4, route.getDistance());

            int result = pstmt.executeUpdate();

            if (result > 0) {
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Route findRoute(Route route) {
        String query = "SELECT * FROM routes WHERE departure = ? AND destination = ?";
        Route foundRoute = null;  // Initialize the Route object to null

        try {
            Connection connection = database.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, route.getDeparture());
            pstmt.setString(2, route.getDestination());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    foundRoute = new Route();
                    foundRoute.setDeparture(rs.getString("departure"));
                    foundRoute.setDestination(rs.getString("destination"));
                    foundRoute.setRouteId(rs.getString("routeId"));
                    foundRoute.setDistance(rs.getDouble("distance"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foundRoute;
    }
}
