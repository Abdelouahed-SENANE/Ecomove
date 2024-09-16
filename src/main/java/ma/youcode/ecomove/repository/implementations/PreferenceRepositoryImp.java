package ma.youcode.ecomove.repository.implementations;

import ma.youcode.ecomove.config.Database;
import ma.youcode.ecomove.entity.Preference;
import ma.youcode.ecomove.enums.TransportationType;
import ma.youcode.ecomove.repository.PreferenceRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreferenceRepositoryImp implements PreferenceRepository {
    private final Database database;

    public PreferenceRepositoryImp() {
        this.database = Database.getInstance();
    }
    @Override
    public Boolean save(Preference preference) {
        String SQL = "INSERT INTO preferences (id, passengeremail ,preferreddeparture , preferreddestination, preferredtransportationtype , preferredtime ) VALUES (?, ?, ? , ? , ? , ?)";
        try {
            Connection connection = database.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, preference.getPreferenceId());
            pstmt.setString(2 , preference.getPassenger().getEmail());
            pstmt.setString(3 , preference.getPreferredDeparture());
            pstmt.setString(4 , preference.getPreferredDestination());
            pstmt.setObject(5 , preference.getPreferredTransportationType() , Types.OTHER);
            pstmt.setTimestamp(6 , Timestamp.valueOf(preference.getPreferredDepartureTime()));

            int affectedRow = pstmt.executeUpdate();
            if (affectedRow > 0) {
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Preference> findMyPreferences(String email) {
        List<Preference> preferences = new ArrayList<>();
        String query = "SELECT * FROM preferences WHERE passengeremail = ?";

        try {
            Connection connection = database.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Preference preference = new Preference();
                preference.setPreferenceId(rs.getString("id"));
                preference.setPreferredDeparture(rs.getString("preferreddeparture"));
                preference.setPreferredDestination(rs.getString("preferreddestination"));
                preference.setPreferredTransportationType(TransportationType.valueOf(rs.getString("preferredtransportationtype")));
                preference.setPreferredDepartureTime(rs.getTimestamp("preferredtime").toLocalDateTime());
                preferences.add(preference);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return preferences;
    }
}
