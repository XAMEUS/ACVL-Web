package ensimag.acvl.dao;

import ensimag.acvl.models.Cancel;
import ensimag.acvl.models.Child;
import ensimag.acvl.models.Period;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class CancelDAO extends AbstractDataBaseDAO {

    public CancelDAO(DataSource ds) {
        super(ds);
    }
    
    public static class CancelExtended {
        private final Child child;
        private final Period period;
        private final Date date;
        private final int code;
        private final int codeType;
        private final Object descr;

        public CancelExtended(Child child, Period period, Date date, int code, int codeType, Object descr) {
            this.child = child;
            this.period = period;
            this.date = date;
            this.code = code;
            this.codeType = codeType;
            this.descr = descr;
        }

        public Child getChild() {
            return child;
        }
        
        public Period getPeriod() {
            return period;
        }
        
        public Date getDate() {
            return date;
        }

        public int getCode() {
            return code;
        }

        public int getCodeType() {
            return codeType;
        }

        public Object getDescr() {
            return descr;
        }

        @Override
        public String toString() {
            return "CancelExtended{" + "child=" + child + ", period=" + period + ", date=" + date + ", code=" + code + ", codeType=" + codeType + ", descr=" + descr + '}';
        }
        
    }
    
    public List<CancelExtended> getAllCancels() {
        List<CancelExtended> result = new ArrayList<>();
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement("SELECT * FROM ACVL_Cancel");) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int codeType = rs.getInt("codeType");
                int code = rs.getInt("code");
                Date date = rs.getDate("day");
                int child = rs.getInt("child");
                int period = rs.getInt("period");
                Object descr = null;
                if (codeType == 1) {
                    if (code == 0)
                        descr = "garderie du matin";
                    if (code == 1)
                        descr = "garderie1";
                    if (code == 2)
                        descr = "garderie2";
                    if (code == 3)
                        descr = "garderie3";
                } else if (codeType == 2) {
                    descr = "cantine";
                } else { // codeType == 3
                    descr = new ActivityDAO(dataSource).getActivity(code);
                }
                result.add(new CancelExtended(new ChildDAO(dataSource).getChild(child),
                        new PeriodDAO(dataSource).getPeriod(period), date, code, codeType, descr));
            }
        } catch (SQLException e) {
            throw new DAOException("Database error: " + e.getMessage(), e);
        }
        return result;
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
