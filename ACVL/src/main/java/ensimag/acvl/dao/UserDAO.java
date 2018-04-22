package ensimag.acvl.dao;

import ensimag.acvl.models.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.sql.DataSource;

public class UserDAO extends AbstractDataBaseDAO {

    public UserDAO(DataSource ds) {
        super(ds);
    }

    /**
     * @return List of users
     */
    public List<User> getUsersList() {
        List<User> result = new ArrayList<User>();
        try (
                Connection conn = getConn();
                Statement st = conn.createStatement();) {
            ResultSet rs = st.executeQuery("SELECT * FROM ACVL_Users");
            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getBytes("passwd"), rs.getString("address"));
                result.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException("Database error: " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * @param name username
     * @param password user password
     */
    public void createUser(String name, String password, String address) {
        try (
            Connection conn = getConn();
            PreparedStatement st = conn.prepareStatement("INSERT INTO ACVL_Users (username, passwd, address) VALUES (?, ?, ?)");) {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), name.getBytes(), 4, 256);
            SecretKey key = skf.generateSecret( spec );
            byte[] pass = key.getEncoded();
            st.setString(1, name);
            st.setBytes(2, pass);;
            st.setString(3, address);
            st.executeUpdate();
        } catch (InvalidKeySpecException e) {
            throw new DAOException("SecretKeyFactory key error " + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            throw new DAOException("SecretKeyFactory algorithm error " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
    }
    
    public boolean signAs(String name, String password) {
        User user = getUser(name);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), name.getBytes(), 4, 256);
            SecretKey key = skf.generateSecret( spec );
            byte[] pass = key.getEncoded();
            return user.passwordMatch(pass);
        } catch (InvalidKeySpecException e) {
            throw new DAOException("SecretKeyFactory key error " + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            throw new DAOException("SecretKeyFactory algorithm error " + e.getMessage(), e);
        }
    }

    public User getUser(String username) {
        try (
            Connection conn = getConn();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM ACVL_Users WHERE username LIKE ?");) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            rs.next();
            return new User(rs.getString("username"), rs.getBytes("passwd"), rs.getString("address"));
        } catch (SQLException e) {
            throw new DAOException("Database error: " + e.getMessage(), e);
        }
    }

    public void editUser(int id, String name, byte[] password) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("UPDATE ACVL_Users SET username=?, titre=? WHERE username=?");) {
            // TODO
            throw new Error("TODO");
        } catch (SQLException e) {
            throw new DAOException("Database error: " + e.getMessage(), e);
        }
    }

    public void removeUser(int id) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("DELETE FROM ACVL_Users WHERE username=?");) {
            throw new Error("TODO");
        } catch (SQLException e) {
            throw new DAOException("Database error: " + e.getMessage(), e);
        }
    }
}
