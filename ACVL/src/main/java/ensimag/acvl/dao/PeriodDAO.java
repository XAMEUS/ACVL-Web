package ensimag.acvl.dao;

import ensimag.acvl.models.Activity;
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
                Period p = new Period(rs.getInt("idPeriod"), rs.getDate("limitDate"), rs.getDate("startDate"), rs.getDate("endDate"));
                result.add(p);
            }
        } catch (SQLException e) {
            throw new DAOException("Databse error: " + e.getMessage(), e);
        }
        return result;
    }
    
    public List<Period> getActivityPeriods(int id) {
        List<Period> result = new ArrayList<Period>();
        try (
                Connection conn = getConn();
                Statement st = conn.createStatement();) {
            ResultSet rs = st.executeQuery("SELECT * FROM ACVL_Periods p, ACVL_ActivityPeriods a WHERE p.idPeriod = a.period AND a.activity = " + id);
            while (rs.next()) {
                Period p = new Period(rs.getInt("idPeriod"), rs.getDate("limitDate"), rs.getDate("startDate"), rs.getDate("endDate"));
                result.add(p);
            }
        } catch (SQLException e) {
            throw new DAOException("Databse error: " + e.getMessage(), e);
        }
        return result;
    }

    public void createPeriod(Date limit, Date start, Date end) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("INSERT INTO ACVL_Periods (limitDate, startDate, endDate) VALUES (?, ?, ?)");) { 
            st.setDate(1, limit);
            st.setDate(2, start);
            st.setDate(3, end);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
    }

    public Period getPeriod(int id) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("SELECT * FROM ACVL_Periods WHERE idPeriod=?");) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            rs.next();
            Period p = new Period(rs.getInt("idPeriod"), rs.getDate("limitDate"), rs.getDate("startDate"), rs.getDate("endDate"));
            return p;
        } catch (SQLException e) {
            throw new DAOException("Database error: " + e.getMessage(), e);
        }
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
    
}
