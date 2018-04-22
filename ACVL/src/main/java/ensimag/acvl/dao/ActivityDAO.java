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
                Activity a = new Activity(rs.getInt("id"), rs.getInt("capacity"), periodDAO.getActivityPeriods(rs.getInt("id")), rs.getFloat("price"),
                        rs.getInt("codeGrades"), rs.getInt("codeDays"), rs.getInt("codeStrategy"), rs.getString("title"), rs.getString("description"), rs.getString("animators"));
                result.add(a);
            }
        } catch (SQLException e) {
            throw new DAOException("Databse error: " + e.getMessage(), e);
        }
        return result;
    }

    public List<Activity> getActivitiesByPeriod(int id) {
        List<Activity> result = new ArrayList<Activity>();
        try (
                Connection conn = getConn();
                Statement st = conn.createStatement();) {
            ResultSet rs = st.executeQuery("SELECT * FROM ACVL_Activities a, ACVL_ActivityPeriods p WHERE a.id = p.activity AND p.period = " + id);
            PeriodDAO periodDAO = new PeriodDAO(dataSource);
            while (rs.next()) {
                Activity a = new Activity(rs.getInt("id"), rs.getInt("capacity"), periodDAO.getActivityPeriods(rs.getInt("id")), rs.getFloat("price"),
                        rs.getInt("codeGrades"), rs.getInt("codeDays"), rs.getInt("codeStrategy"), rs.getString("title"), rs.getString("description"), rs.getString("animators"));
                result.add(a);
            }
        } catch (SQLException e) {
            throw new DAOException("Databse error: " + e.getMessage(), e);
        }
        return result;
    }

    public void createActivity(int capacity, List<Integer> periods, float price, int codeGrades, int codeDays, int codeStrategy, String title, String description, String animators) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("INSERT INTO ACVL_Activities (capacity, codeGrades, codeDays, codeStrategy, title, description, animators, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");) {
            st.setInt(1, capacity);
            st.setInt(2, codeGrades);
            st.setInt(3, codeDays);
            st.setInt(4, codeStrategy);
            st.setString(5, title);
            st.setString(6, description);
            st.setString(7, animators);
            st.setFloat(8, price);
            st.executeUpdate();

            ResultSet rs = conn.createStatement().executeQuery("SELECT ACVL_Activities_id_seq.currval FROM dual");
            rs.next();
            int id = rs.getInt(1);
            for (Integer p : periods) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO ACVL_ActivityPeriods (activity, period) VALUES (?, ?)");
                ps.setInt(1, id);
                ps.setInt(2, p);
                ps.executeUpdate();
            }
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
            Activity a = new Activity(rs.getInt("id"), rs.getInt("capacity"), periodDAO.getActivityPeriods(rs.getInt("id")), rs.getFloat("price"),
                    rs.getInt("codeGrades"), rs.getInt("codeDays"), rs.getInt("codeStrategy"), rs.getString("title"), rs.getString("description"), rs.getString("animators"));
            return a;
        } catch (SQLException e) {
            throw new DAOException("Database error: " + e.getMessage(), e);
        }
    }

    public List<Activity> getActivities(int period, int codeGrade) {
        List<Activity> result = new ArrayList<Activity>();
        try (
                Connection conn = getConn();
                Statement st = conn.createStatement();) {
            ResultSet rs = st.executeQuery("SELECT * FROM Activity a, ACVL_ActivityPeriods p WHERE p.activity = a.id AND p.period = " + period);
            PeriodDAO periodDAO = new PeriodDAO(dataSource);
            while (rs.next()) {
                Activity a = new Activity(rs.getInt("id"), rs.getInt("capacity"), periodDAO.getActivityPeriods(rs.getInt("id")), rs.getFloat("price"),
                        rs.getInt("codeGrades"), rs.getInt("codeDays"), rs.getInt("codeStrategy"), rs.getString("title"), rs.getString("description"), rs.getString("animators"));
                if ((a.getCodeGrades() & codeGrade) != 0) {
                    result.add(a);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Databse error: " + e.getMessage(), e);
        }
        return result;
    }
}
