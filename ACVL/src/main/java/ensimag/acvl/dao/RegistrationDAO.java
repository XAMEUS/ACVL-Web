package ensimag.acvl.dao;

import ensimag.acvl.models.Activity;
import ensimag.acvl.models.Period;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class RegistrationDAO extends AbstractDataBaseDAO {

    public RegistrationDAO(DataSource ds) {
        super(ds);
    }
    
    public void registerChild(int child, int period, int codeCantine, int codeGarderie, String infos) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("INSERT INTO ACVL_Registrations (child, period, codeCantine, codeGarderie, infos) VALUES (?, ?, ?, ?, ?)");) {
            st.setInt(1, child);
            st.setInt(2, period);
            st.setInt(3, codeCantine);
            st.setInt(4, codeGarderie);
            st.setString(5, infos);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
    }
    
    public void registerWish(int child, int period, int activity, int day, int rank) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("INSERT INTO ACVL_Wishes (child, period, activity, rank, day) VALUES (?, ?, ?, ?, ?)");) {
            st.setInt(1, child);
            st.setInt(2, period);
            st.setInt(3, activity);
            st.setInt(4, rank);
            st.setInt(5, day);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
    }
    
}
