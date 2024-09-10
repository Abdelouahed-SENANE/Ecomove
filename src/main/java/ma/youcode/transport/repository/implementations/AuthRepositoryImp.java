package ma.youcode.transport.repository.implementations;

import ma.youcode.transport.config.Database;
import ma.youcode.transport.entity.Passenger;
import ma.youcode.transport.repository.AuthRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AuthRepositoryImp implements AuthRepository {
    private final Database database;
    public AuthRepositoryImp() {
        this.database = Database.getInstance();
    }

    @Override
    public Passenger save(Passenger passenger) {
        String sql = "INSERT INTO passengers (email , firstname , familyname , phonenumber) VALUES (?, ?, ? , ?)";
        try{
            Connection connection = database.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, passenger.getEmail());
            pstmt.setString(2, passenger.getFirstName());
            pstmt.setString(3, passenger.getFamilyName());
            pstmt.setString(4, passenger.getPhone());

            int affetedRow = pstmt.executeUpdate();
            if (affetedRow > 0) {
                return passenger;
            }
        }catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public Passenger findByEmail(String email) {
        Passenger passenger = null;
        String sql = "SELECT * FROM passengers WHERE email = ?";
        try {
            Connection connection = database.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                passenger = new Passenger();
                passenger.setEmail(rs.getString("email"));
                passenger.setFamilyName(rs.getString("familyname"));
                passenger.setFirstName(rs.getString("firstname"));
                passenger.setPhone(rs.getString("phonenumber"));
                return passenger;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return passenger;
    }

    @Override
    public Boolean existsByEmail(String email) {
        return Optional.ofNullable(this.findByEmail(email)).isPresent();
    }

    @Override
    public Passenger update(Passenger passenger) {
        String sql = "UPDATE passengers SET firstname = ?, familyname = ?  , phonenumber = ? WHERE email = ?";
        try {
            Connection connection = database.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, passenger.getFirstName());
            pstmt.setString(2, passenger.getFamilyName());
            pstmt.setString(3, passenger.getPhone());
            pstmt.setString(4, passenger.getEmail());
            int affetedRow = pstmt.executeUpdate();
            if (affetedRow > 0) {
                return passenger;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
