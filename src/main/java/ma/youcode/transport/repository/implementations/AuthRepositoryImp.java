package ma.youcode.transport.repository.implementations;

import ma.youcode.transport.config.Database;
import ma.youcode.transport.entity.Contract;
import ma.youcode.transport.entity.Costumer;
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
    public Costumer save(Costumer costumer) {

        String sql = "INSERT INTO costumer (email , firstname , familyname , phone) VALUES (?, ?, ? , ?)";
        try{
            Connection connection = database.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            int affetedRow = pstmt.executeUpdate();
            if (affetedRow > 0) {
                return costumer;
            }
            return null;

        }catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

    @Override
    public Costumer findByEmail(String email) {
        Costumer costumer = null;
        String sql = "SELECT * FROM costumer WHERE email = ?";
        try {
            Connection connection = database.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                costumer = new Costumer();
                costumer.setEmail(rs.getString("email"));
                costumer.setFamilyName(rs.getString("familyname"));
                costumer.setFirstName(rs.getString("firstname"));
                costumer.setPhone(rs.getString("phone"));
                return costumer;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return costumer;
    }

    @Override
    public Boolean existsByEmail(String email) {
        return Optional.ofNullable(this.findByEmail(email)).isPresent();
    }
}
