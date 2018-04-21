package ensimag.acvl.dao;

import ensimag.acvl.models.Cancel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class CancelDAO extends AbstractDataBaseDAO {

    public CancelDAO(DataSource ds) {
        super(ds);
    }
    
    public Cancel getCancels(int child, int period) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("SELECT * FROM ACVL_Cancel WHERE child=? AND period=?");) {
            st.setInt(1, child);
            st.setInt(2, period);
            ResultSet rs = st.executeQuery();
            Cancel c = new Cancel();
            while (rs.next()) {
                int codeType = rs.getInt("codeType");
                int code = rs.getInt("code");
                Date date = rs.getDate("day");
                c.addCancel(date, codeType, code);
            }
            return c;
        } catch (SQLException e) {
            throw new DAOException("Database error: " + e.getMessage(), e);
        }
    }
    
    public void addCancel(int child, int period, int codeType, int code, Date day) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("INSERT INTO ACVL_Cancel (child, period, codeType, code, day) VALUES (?, ?, ?, ?, ?)");) {
            st.setInt(1, child);
            st.setInt(2, period);
            st.setInt(3, codeType);
            st.setInt(4, code);
            st.setDate(5, day);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
    }
}
