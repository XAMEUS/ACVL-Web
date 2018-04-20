package ensimag.acvl.dao;

import ensimag.acvl.models.Activity;
import ensimag.acvl.models.Child;
import ensimag.acvl.models.Period;
import ensimag.acvl.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class ChildDAO extends AbstractDataBaseDAO {

    public ChildDAO(DataSource ds) {
        super(ds);
    }

    public List<Child> getChildrenList() {
        List<Child> result = new ArrayList<Child>();
        try (
                Connection conn = getConn();
                Statement st = conn.createStatement();) {
            ResultSet rs = st.executeQuery("SELECT * FROM ACVL_Children");
            while (rs.next()) {
                Child child = new Child(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("gender").charAt(0), rs.getString("grade"), rs.getDate("birthdate"));
                child.setRegisteredPeriods(getPeriods(child.getId(), child.getCodeGrade(), true));
                child.setUnregisteredPeriods(getPeriods(child.getId(), child.getCodeGrade(), false));
                System.out.println(child);
                result.add(child);
            }
        } catch (SQLException e) {
            throw new DAOException("Databse error: " + e.getMessage(), e);
        }
        return result;
    }

    public List<Child> getChildrenList(String username) {
        List<Child> result = new ArrayList<Child>();
        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM ACVL_Users u, ACVL_Children c, ACVL_family f where u.username = f.username and f.idChild = c.id and u.username = ?");) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Child child = new Child(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("gender").charAt(0), rs.getString("grade"), rs.getDate("birthdate"));
                child.setUnregisteredPeriods(getPeriods(child.getId(), child.getCodeGrade(), false));
                child.setRegisteredPeriods(getPeriods(child.getId(), child.getCodeGrade(), true));
                result.add(child);
                PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM ACVL_Diet d, ACVL_ChildDiet cd WHERE d.diet = cd.diet AND cd.idChild = ?");
                ps2.setInt(1, child.getId());
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    child.getDiet().add(rs2.getString("diet"));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Databse error: " + e.getMessage(), e);
        }
        return result;
    }

    public int createChild(String firstname, String lastname, String gender, String grade, Date birthdate) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("INSERT INTO ACVL_Children (firstname, lastname, gender, grade, birthdate) VALUES (?, ?, ?, ?, ?)");) {
            st.setString(1, firstname);
            st.setString(2, lastname);
            st.setString(3, gender);
            st.setString(4, grade);
            st.setDate(5, birthdate);
            st.executeUpdate();
            ResultSet rs = conn.createStatement().executeQuery("SELECT ACVL_Children_id_seq.currval FROM dual");
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
    }

    public void setParent(String username, int idChild) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("INSERT INTO ACVL_Family (username, idChild) VALUES (?, ?)");) {
            st.setString(1, username);
            st.setInt(2, idChild);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
    }

    public void setDiet(int idChild, String diet) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("INSERT INTO ACVL_ChildDiet (diet, idChild) VALUES (?, ?)");) {
            System.out.println(idChild + " " + diet);
            st.setString(1, diet);
            st.setInt(2, idChild);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
    }

    public Child getChild(int id) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("SELECT * FROM ACVL_Children WHERE id=?");) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            rs.next();
            Child child = new Child(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("gender").charAt(0), rs.getString("grade"), rs.getDate("birthdate"));
            child.setUnregisteredPeriods(getPeriods(child.getId(), child.getCodeGrade(), false));
            PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM ACVL_Diet d, ACVL_ChildDiet cd WHERE d.diet = cd.diet AND cd.idChild = ?");
            ps2.setInt(1, child.getId());
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                child.getDiet().add(rs2.getString("diet"));
            }
            return child;
        } catch (SQLException e) {
            throw new DAOException("Database error: " + e.getMessage(), e);
        }
    }

    public void editChild(int id, String firstname, String lastname, String gender, String grade, Date birthdate) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("UPDATE ACVL_Children SET firstname=?, lastname=?, gender=?, grade=?, birthdate=? WHERE id=?");) {
            st.setString(1, firstname);
            st.setString(2, lastname);
            st.setString(3, gender);
            st.setString(4, grade);
            st.setDate(5, birthdate);
            st.setInt(6, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database error: " + e.getMessage(), e);
        }
    }

    public void removeChild(int id) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("DELETE FROM ACVL_Children WHERE username=?");) {
            throw new Error("TODO");
        } catch (SQLException e) {
            throw new DAOException("Database error: " + e.getMessage(), e);
        }
    }

    public List<String> getDiets() {
        List<String> result = new ArrayList<String>();
        try (
                Connection conn = getConn();
                ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM ACVL_Diet");) {
            while (rs.next()) {
                result.add(rs.getString("diet"));
            }
        } catch (SQLException e) {
            throw new DAOException("Databse error: " + e.getMessage(), e);
        }
        return result;
    }

    public void addDiet(String diet) {
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("INSERT INTO ACVL_DIET (diet) VALUES (?)");) {
            st.setString(1, diet);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database error " + e.getMessage(), e);
        }
    }

    public List<Period> getPeriods(int idChild, int codeGrade, boolean registered) {
        List<Period> result = new ArrayList<Period>();
        try (Connection conn = getConn();) {
            ResultSet rs = null;
            if (registered)
                rs = conn.createStatement().executeQuery("SELECT * FROM ACVL_Periods where idPeriod IN (select period from ACVL_Registrations r WHERE r.child = " + idChild + ")");
            else
                rs = conn.createStatement().executeQuery("SELECT * FROM ACVL_Periods where idPeriod NOT IN (select period from ACVL_Registrations r WHERE r.child = " + idChild + ")");
            while (rs.next()) {
                Period p = new Period(rs.getInt("idPeriod"), rs.getDate("limitDate"), rs.getDate("startDate"), rs.getDate("endDate"));
                ResultSet rs2 = conn.createStatement().executeQuery("SELECT * FROM ACVL_Activities a, ACVL_ActivityPeriods p WHERE p.activity = a.id AND p.period = " + p.getId());
                while (rs2.next()) {
                    Activity a = new Activity(rs2.getInt("id"), rs2.getInt("capacity"), null,
                            rs2.getInt("codeGrades"), rs2.getInt("codeDays"), rs2.getInt("codeStrategy"), rs2.getString("title"), rs2.getString("description"), rs2.getString("animators"));
                    if ((a.getCodeGrades() / codeGrade) % 2 == 1) {
                        for (int day = 1; day <= 5; day++) {
                            if (((a.getCodeDays() / (int) (Math.pow(2, day - 1))) % 2) == 1) {
                                p.addActivitiy(day, a);
                            }
                        }
                    }
                }
                result.add(p);
            }
        } catch (SQLException e) {
            throw new DAOException("Databse error: " + e.getMessage(), e);
        }
        return result;
    }
    
    public List<Period> getPeriods(boolean moulinetted) {
        List<Period> result = new ArrayList<Period>();
        try (Connection conn = getConn();) {
            ResultSet rs = null;
            if (moulinetted)
                rs = conn.createStatement().executeQuery("SELECT * FROM ACVL_Periods where idPeriod IN (select period from ACVL_moulinette)");
            else
                rs = conn.createStatement().executeQuery("SELECT * FROM ACVL_Periods where idPeriod NOT IN (select period from ACVL_moulinette)");
            while (rs.next()) {
                Period p = new Period(rs.getInt("idPeriod"), rs.getDate("limitDate"), rs.getDate("startDate"), rs.getDate("endDate"));
                result.add(p);
            }
        } catch (SQLException e) {
            throw new DAOException("Databse error: " + e.getMessage(), e);
        }
        return result;
    }

}
