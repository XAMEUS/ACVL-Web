package ensimag.acvl.dao;

import ensimag.acvl.models.Activity;
import ensimag.acvl.models.Period;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class ActivityDAO extends AbstractDataBaseDAO {

    public ActivityDAO(DataSource ds) {
        super(ds);
    }

    public List<Activity> getActivities() {
        List<Activity> result = new ArrayList<Activity>();
        try (
                Connection conn = getConn();
                Statement st = conn.createStatement();) {
            ResultSet rs = st.executeQuery("SELECT * FROM ACVL_Activities");
            PeriodDAO periodDAO = new PeriodDAO(dataSource);
            while (rs.next()) {
                Period p = periodDAO.getPeriod(rs.getInt("period"));
                Activity a = new Activity(rs.getInt("id"), rs.getInt("capacity"), p, rs.getInt("codeGrades"), rs.getInt("codeDays"), rs.getString("title"), rs.getString("description"), rs.getString("animators"));
                result.add(a);
            }
        } catch (SQLException e) {
            throw new DAOException("Databse error: " + e.getMessage(), e);
        }
        return result;
    }

    public void createActivity(int capacity, int period, int codeGrades, int codeDays, String title, String description, String animators) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("INSERT INTO ACVL_Activities (capacity, period, codeGrades, codeDays, title, description, animators) VALUES (?, ?, ?, ?, ?, ?, ?)");) {
            st.setInt(1, capacity);
            st.setInt(2, period);
            st.setInt(3, codeGrades);
            st.setInt(4, codeDays);
            st.setString(5, title);
            st.setString(6, description);
            st.setString(7, animators);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
    }

    public Activity getActivity(int id) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("SELECT * FROM ACVL_Activities WHERE id=?");) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            rs.next();
            PeriodDAO periodDAO = new PeriodDAO(dataSource);
            Period p = periodDAO.getPeriod(rs.getInt("idPeriod"));
            Activity a = new Activity(rs.getInt("id"), rs.getInt("capacity"), p, rs.getInt("codeGrades"), rs.getInt("codeDays"), rs.getString("title"), rs.getString("description"), rs.getString("animators"));
            return a;
        } catch (SQLException e) {
            throw new DAOException("Database error: " + e.getMessage(), e);
        }
    }
}
