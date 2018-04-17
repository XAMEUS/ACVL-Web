package ensimag.acvl.dao;

import ensimag.acvl.models.Period;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class PeriodDAO extends AbstractDataBaseDAO {

    public PeriodDAO(DataSource ds) {
        super(ds);
    }

    public List<Period> getPeriods() {
        List<Period> result = new ArrayList<Period>();
        try (
                Connection conn = getConn();
                Statement st = conn.createStatement();) {
            ResultSet rs = st.executeQuery("SELECT * FROM ACVL_Periods");
            while (rs.next()) {
                Period p = new Period(rs.getInt("idPeriod"), rs.getDate("startDate"), rs.getDate("endDate"));
                result.add(p);
            }
        } catch (SQLException e) {
            throw new DAOException("Databse error: " + e.getMessage(), e);
        }
        return result;
    }

    public void createPeriod(Date start, Date end) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("INSERT INTO ACVL_Periods (startDate, endDate) VALUES (?, ?)");) {
            st.setDate(1, start);
            st.setDate(2, end);
            System.out.println("ensimag.acvl.dao.PeriodDAO.createPeriod()");
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
    }

    public Period getChild(int id) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("SELECT * FROM ACVL_Periods WHERE idPeriod=?");) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            rs.next();
            Period p = new Period(rs.getInt("idPeriod"), rs.getDate("startDate"), rs.getDate("endDate"));
            return p;
        } catch (SQLException e) {
            throw new DAOException("Database error: " + e.getMessage(), e);
        }
    }
}
